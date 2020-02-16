package com.example.easyfind.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import com.example.easyfind.R;
import com.example.easyfind.database.BusinessServiceImpl;
import com.example.easyfind.models.Business;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    private List<Business> businesses;

    public RestaurantAdapter(List<Business> businesses) {
        this.businesses = businesses;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new RestaurantAdapter.RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RestaurantViewHolder holder, int position) {
        final Business business = businesses.get(position);
        holder.txtName.setText(business.getName());
        holder.txtAddress.setText(business.getLocation().getAddress1());
        Picasso.get().load(business.getImageUrl()).into(holder.imgRestaurant);
        holder.txtReview.setText(business.getReviewCount() + " Reviews");
        holder.txtCategory.setText(business.getCategories().get(0).getTitle());
        holder.txtPrice.setText(business.getPrice());
        holder.ratingBar.setRating(business.getRating().floatValue());
        holder.imgFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToFav(business, v.getContext());
            }
        });
    }

    private void addToFav(Business business, Context context) {
        BusinessServiceImpl businessService = new BusinessServiceImpl(context);
        businessService.insertAll(business);
    }

    private void replaceFragment (Fragment fragment, View v){
        String backStateName = fragment.getClass().getName();
        FragmentManager manager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);
        if (!fragmentPopped) { //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.container, fragment);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }

    @Override
    public int getItemCount() {
        return businesses.size();
    }

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName, txtAddress, txtReview, txtPrice, txtCategory;
        public ImageView imgRestaurant, imgFavourite;
        public RatingBar ratingBar;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtAddress = itemView.findViewById(R.id.txt_address);
            txtReview = itemView.findViewById(R.id.txt_review);
            txtPrice = itemView.findViewById(R.id.txt_price);
            txtCategory = itemView.findViewById(R.id.txt_cat);
            imgRestaurant = itemView.findViewById(R.id.img_rest);
            imgFavourite = itemView.findViewById(R.id.img_fav);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }
}
