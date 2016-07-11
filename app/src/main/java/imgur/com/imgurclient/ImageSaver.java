package imgur.com.imgurclient;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import imgur.com.imgurclient.models.ImageService.ImageModel;

/**
 * Created by Emilija.Pereska on 7/11/2016.
 */
public interface ImageSaver {

    void saveImages(ArrayList<ImageModel> images,MainActivity context) throws IOException;
}
