package imgur.com.imgurclient;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import imgur.com.imgurclient.models.ImageService.ImageModel;

/**
 * Created by Emilija.Pereska on 7/18/2016.
 */
public class AlertDialogLoader extends ComponentLoader {

    private ImageView image;
    private TextView title, description, views, setDescription;
    private AlertDialog dialog;
    private View dialogView;
    private ImageModel imageResponse;

    public AlertDialogLoader(Context context, ImageModel imageResponse) {
        super(context);
        this.imageResponse = imageResponse;
    }

    public void initializeVariables() {

        dialogView = getView(R.layout.alert_dialog);
        image = (ImageView) dialogView.findViewById(R.id.imageView);
        title = (TextView) dialogView.findViewById(R.id.title);
        description = (TextView) dialogView.findViewById(R.id.description);
        views = (TextView) dialogView.findViewById(R.id.views);
        setDescription = (TextView) dialogView.findViewById(R.id.fitDescription);
    }

    public void setupDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        dialog = builder.create();

        initializeVariables();
        dialog.setView(dialogView);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }


    @Override
    public View getView(int resource) {
        return super.getView(resource);
    }

    @Override
    public void setupComponent() {

        setupDialog();
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                final int size = 512;


                title.setText(imageResponse.getTitle());
                Picasso.with(getContext())
                        .load(imageResponse.getLink())
                        .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                        .resize(size, size)
                        .onlyScaleDown()
                        .into(image);

                if (imageResponse.getDescription() != null) {
                    setDescription.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                    setDescription.setText("Description: ");
                }
                description.setText(imageResponse.getDescription());
                views.setText("Views: " + imageResponse.getViews());

            }
        });

        dialog.show();

    }
}
