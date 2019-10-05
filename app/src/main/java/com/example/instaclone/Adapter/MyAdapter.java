package com.example.instaclone.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instaclone.Data.Post;
import com.example.instaclone.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {
    ArrayList<Post> posts;
    Context mContext;

    public MyAdapter(ArrayList<Post> posts, Context mContext) {
        this.posts = posts;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout,parent,false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.textViewEMAIL.setText(posts.get(position).getEmail());
        Picasso.get().load(posts.get(position).getImageUrl()).into(holder.imageView2);
        holder.textViewCOMMENT.setText(posts.get(position).getComment());

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView textViewEMAIL,textViewCOMMENT;
        ImageView imageView2;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            textViewEMAIL=itemView.findViewById(R.id.textViewEMAIL);
            textViewCOMMENT=itemView.findViewById(R.id.textViewComment);
            imageView2=itemView.findViewById(R.id.imageView2);

        }
    }
}
