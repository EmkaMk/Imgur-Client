package imgur.com.imgurclient;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import imgur.com.imgurclient.activities.MainActivity;
import imgur.com.imgurclient.models.ImageService.ImageModel;

/**
 * Created by Emilija.Pereska on 7/11/2016.
 */

// not used, implementation used for saving images localy
public class InternalStorageSaver implements ImageSaver {


    @Override
    public void saveImages(ArrayList<ImageModel> images, final MainActivity context) throws IOException {

        String root = Environment.getExternalStorageDirectory().toString();
        final File dir = new File(root + "/ImgurClient");
        if (!dir.exists()) {
            dir.mkdir();
        }

        for (final ImageModel image : images) {

            Picasso.with(context).load(image.getLink()).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    Log.i(InternalStorageSaver.class.getName(), "The image was obtained correctly");


                    try {
                        saveImageToExternal(image.getTitle(), bitmap, context);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                    Log.i(InternalStorageSaver.class.getName(), "The image was not obtained correctly");

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                    Log.i(InternalStorageSaver.class.getName(), "The image is preparing to load");

                }
            });

        }

    }


    public void saveImageToExternal(String imgName, Bitmap bm, Context c) throws IOException {
        //Create Path to save Image
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/ImgurClient"); //Creates app specific folder
        path.mkdirs();
        File imageFile = new File(path, imgName + ".png");
        FileOutputStream out = new FileOutputStream(imageFile);
        try {
            bm.compress(Bitmap.CompressFormat.PNG, 100, out); // Compress Image
            out.flush();
            out.close();
            MediaScannerConnection.scanFile(c, new String[]{imageFile.getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String path, Uri uri) {
                    Log.i("ExternalStorage", "Scanned " + path + ":");
                    Log.i("ExternalStorage", "-> uri=" + uri);
                }
            });
        } catch (Exception e) {
            throw new IOException();
        }
    }
}
