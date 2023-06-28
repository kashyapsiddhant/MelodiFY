package com.example.melodify;

import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<MusicFiles> musicFiles;
    MusicAdapter(Context mContext, ArrayList<MusicFiles> mFiles)
    {
        this.musicFiles=mFiles;
        this.context=mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.music_items,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.filename.setText(musicFiles.get(position).getTitle());
        byte[] image;
        image = getAlbumArt(musicFiles.get(position).getPath());
        if (image!=null)
        {
            Glide.with(context).asBitmap()
                    .load(image)
                    .into(holder.album_art);
        }
        else {
             Glide.with(context)
                     .load(R.drawable.album)
                     .into(holder.album_art);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, PlayerActivity.class);
                intent.putExtra("position",position);
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return musicFiles.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView filename;
        ImageView album_art, menuDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            filename=itemView.findViewById(R.id.music_filename);
            album_art=itemView.findViewById(R.id.music_img);

        }
    }
    private byte[] getAlbumArt(String uri) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        byte[] art = null;
        try {
            retriever.setDataSource(uri);
            art = retriever.getEmbeddedPicture();
        } catch (Exception e) {
            Log.e("Error", "Failed to retrieve album art: " + e.getMessage());
        }
        return art;
    }



}
