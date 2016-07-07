package imgur.com.imgurclient;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import imgur.com.imgurclient.models.ImageService.ImageModel;


/**
 * Created by emilija.pereska on 6/24/2016.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.PostViewHolder> {


    MainActivity context;
    List<ImageModel> response;


    public ImageAdapter(MainActivity c, List<ImageModel> response) {
        this.context = c;
        this.response = response;
    }

    public void setImages(List<ImageModel> images)
    {
        this.response=images;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.item, parent, false);
        return new PostViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final PostViewHolder holder, final int position) {

        Picasso.with(context).load(response.get(position).getLink()).into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.showDialog(response.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {

        return response.size();

    }

    public void updateAfterRefresh() {
        response.clear();

    }


    public static class PostViewHolder extends RecyclerView.ViewHolder {

        ImageView image;

        public PostViewHolder(View itemView) {
            super(itemView);
            this.image = (ImageView) itemView.findViewById(R.id.image);
        }
    }

}
