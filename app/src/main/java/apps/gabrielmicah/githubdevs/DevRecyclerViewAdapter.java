package apps.gabrielmicah.githubdevs;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Daniel on 7/23/2016.
 */
public class DevRecyclerViewAdapter extends RecyclerView.Adapter<DevRecyclerViewAdapter.MenuViewHolder>
{
    List<DevData> contents;
    private Context context;

    static final int TYPE_NORMAL = 0;
    static final int TYPE_PICTURE = 1;

    public DevRecyclerViewAdapter(Context con, List<DevData> contents)
    {
        this.contents = contents;
        this.context = con;
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder
    {
        LinearLayout cv;
        TextView username;
        ImageView thumbnail;


        MenuViewHolder(View itemView)
        {
            super(itemView);
            cv = (LinearLayout)itemView.findViewById(R.id.devcard_layout);
            username = (TextView)itemView.findViewById(R.id.dev_username);
            thumbnail = (ImageView)itemView.findViewById(R.id.dev_thumbnail);
        }

    }

    @Override
    public int getItemViewType(int position)
    {
        switch (position)
        {
            default:
                return TYPE_NORMAL;
        }
    }

    @Override
    public int getItemCount()
    {
        return contents.size();
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = null;

        switch (viewType)
        {
            case TYPE_NORMAL:
            {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dev_card, parent, false);
                MenuViewHolder pvh = new MenuViewHolder(view);
                return pvh;
            }

        }
        return null;
    }


    @Override
    public void onBindViewHolder(final MenuViewHolder holder,final int position)
    {
        switch (getItemViewType(position))
        {
            case TYPE_NORMAL:
            {
                holder.username.setText(contents.get(position).username);
                //holder.thumbnail.setImageResource(R.drawable.class);
                Picasso.with(context)
                        .load(contents.get(position).image)
                        .into(holder.thumbnail);
            }
            break;

        }

        //set the onClicklistener for the card
        holder.cv.setOnClickListener
                (
                        new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {

                                Intent i = new Intent(context,ProfileActivity.class);
                                i.putExtra("username",contents.get(position).username);
                                i.putExtra("image",contents.get(position).image);
                                i.putExtra("profile_url",contents.get(position).profile_url);
                                i.putExtra("html_url",contents.get(position).html_url);
                                context.startActivity(i);


                            }
                        }
                );
    }

}
