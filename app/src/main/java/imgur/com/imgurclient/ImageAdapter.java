package imgur.com.imgurclient;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


/**
 * Created by emilija.pereska on 6/24/2016.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.PostViewHolder> {

    List<Post> posts;
    private Integer[] images = {R.mipmap.one, R.mipmap.two, R.mipmap.three, R.mipmap.four, R.mipmap.five, R.mipmap.six, R.mipmap.seven
    };
    Context context;

    public ImageAdapter(Context c)
    {
        this.context=c;
    }
    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View v = inflater.inflate(R.layout.item,parent,false);
        PostViewHolder pvh = new PostViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        holder.image.setImageResource(images[position]);


    }

    @Override
    public int getItemCount() {
        return images.length;
    }



    public static class PostViewHolder extends RecyclerView.ViewHolder {

        ImageView image;

        public PostViewHolder(View itemView) {
            super(itemView);
            this.image= (ImageView) itemView.findViewById(R.id.image);
        }
    }
}
