package imgur.com.imgurclient;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;


/**
 * Created by emilija.pereska on 6/24/2016.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.PostViewHolder> {


    MainActivity context;
    ArrayList<String> imageLinks;


    public ImageAdapter(MainActivity c) {
        this.context = c;
        imageLinks= (ArrayList<String>) context.imageLinks();
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.item, parent, false);
        return new PostViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final PostViewHolder holder, final int position) {
        Picasso.with(context).load(imageLinks.get(position)).into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Im clicked " + holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return imageLinks.size();
    }


    public static class PostViewHolder extends RecyclerView.ViewHolder {

        ImageView image;

        public PostViewHolder(View itemView) {
            super(itemView);
            this.image = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}
