package imgur.com.imgurclient;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import imgur.com.imgurclient.models.ImageService.Image;
import imgur.com.imgurclient.models.ImageService.ImageModel;


/**
 * Created by emilija.pereska on 6/24/2016.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.PostViewHolder> {


    MainActivity context;
    List<ImageModel> response;
    private static final int MAX_WIDTH = 250;
    private static final int MAX_HEIGHT = 250;
    int size = (int) Math.ceil(Math.sqrt(MAX_WIDTH * MAX_HEIGHT));


    public ImageAdapter(MainActivity c) {
        this.context = c;
        response = new ArrayList<>();
    }

    public void addImages(List<ImageModel> images) {
        if (images == null || images.isEmpty()) {
            return;
        }
        int previousSize = response.size();
        response.addAll(images);
        int currentSize = response.size();
        notifyItemRangeInserted(previousSize, currentSize);
    }

    public void addMyImages(List<ImageModel> images)
    {
        if (images == null || images.isEmpty()) {
            return;
        }
      /*  if(response.size()==5)
        {
            response.clear();
        }*/
        response.addAll(images);
        notifyItemRangeInserted(0,images.size());
    }


    public void clearList()
    {
        int end=response.size();
        response.clear();
        notifyItemRangeRemoved(0,end);
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.item, parent, false);
        return new PostViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final PostViewHolder holder, final int position) {
        Picasso.with(context).load(response.get(position).getLink()).transform(new BitMapTransform(MAX_WIDTH,MAX_WIDTH)).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).resize(size,size).centerInside().into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.showDialog(response.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return response.size();
    }

    public void updateAfterRefresh() {
        int size = response.size();
        response.clear();
        notifyItemRangeRemoved(0, size);
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {

        ImageView image;

        public PostViewHolder(View itemView) {
            super(itemView);
            this.image = (ImageView) itemView.findViewById(R.id.image);
        }
    }



}
