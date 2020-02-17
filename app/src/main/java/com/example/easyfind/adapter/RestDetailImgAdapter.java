package com.example.easyfind.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.easyfind.R;
import com.squareup.picasso.Picasso;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RestDetailImgAdapter  extends RecyclerView.Adapter<RestDetailImgAdapter.listViewHolder> {

    private List<String> imgList;

    public RestDetailImgAdapter(List<String> imgList) {
        this.imgList = imgList;
    }

    @NonNull
    @Override
    public listViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_recycle, parent, false);
        return new listViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull listViewHolder holder, int position) {
        final String imgUrl = imgList.get(position);

        Picasso.get().load(imgUrl).into(holder.imgV);

    }

    @Override
    public int getItemCount() {
        return imgList.size();
    }

    public static class listViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgV;

        public listViewHolder(@NonNull View itemView) {
            super(itemView);

            imgV = itemView.findViewById(R.id.imageView);
        }
    }

}
