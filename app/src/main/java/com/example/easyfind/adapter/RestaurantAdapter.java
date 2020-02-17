package com.example.easyfind.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.easyfind.activities.RestDetailActivity;
import com.example.easyfind.database.BusinessServiceImpl;
import com.example.easyfind.models.Business;
import com.example.easyfind.ui.search.SearchFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    private List<Business> businesses;
    private SearchFragment searchFragment;

    public RestaurantAdapter(List<Business> businesses, Fragment fragment) {
        this.businesses = businesses;
        if (fragment instanceof SearchFragment)
            searchFragment = (SearchFragment) fragment;
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
        holder.txtAddress.setText(business.getLocation().getDisplayAddress().toString());
        Picasso.get().load(business.getImageUrl()).into(holder.imgRestaurant);
        holder.txtReview.setText(business.getReviewCount() + " Reviews");
        holder.txtCategory.setText(business.getCategories().get(0).getTitle());
        holder.txtPrice.setText(business.getPrice());
        holder.ratingBar.setRating(business.getRating().floatValue());
        final boolean isFav = checkIsFav(holder.itemView.getContext(), business);
        setFav(isFav, holder);
        holder.imgFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToFavUnFav(business, v.getContext(), isFav);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushToDetail(v.getContext());
            }
        });
    }

    private void pushToDetail(Context context) {
        Intent intent = new Intent(context, RestDetailActivity.class);
        context.startActivity(intent);
    }

    private boolean checkIsFav(Context context, Business business) {
        BusinessServiceImpl businessService = new BusinessServiceImpl(context);
        List<Business> businesses = new ArrayList<>();
        businesses.addAll(businessService.getAll());
        boolean isFav = false;
        for (Business dbBusiness: businesses) {
            if (business.getId().equalsIgnoreCase(dbBusiness.getId())) {
                isFav = true;
                break;
            }
        }
        return isFav;
    }

    private void setFav(boolean isFav, RestaurantViewHolder holder) {
        holder.imgFavourite.setImageResource(isFav ? R.drawable.ic_fav_black_24dp : R.drawable.ic_unfav_black_24dp);
    }

    private void addToFavUnFav(Business business, Context context, boolean isFav) {
        BusinessServiceImpl businessService = new BusinessServiceImpl(context);
        business.setFav(!isFav);
        if (isFav) {
            businessService.delete(business);
            refreshList(businessService);
        } else {
            businessService.insertAll(business);
            notifyDataSetChanged();
        }
    }

    private void refreshList(BusinessServiceImpl businessService) {
        if (searchFragment != null) {
            notifyDataSetChanged();
            return;
        }
        businesses.removeAll(businesses);
        businesses.addAll(businessService.getAll());
        notifyDataSetChanged();
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
