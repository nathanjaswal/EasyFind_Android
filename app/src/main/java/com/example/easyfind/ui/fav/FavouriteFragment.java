package com.example.easyfind.ui.fav;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyfind.R;
import com.example.easyfind.adapter.RestaurantAdapter;
import com.example.easyfind.database.BusinessService;
import com.example.easyfind.database.BusinessServiceImpl;
import com.example.easyfind.models.Business;

import java.util.ArrayList;
import java.util.List;

public class FavouriteFragment extends Fragment {

    private RecyclerView listView;
    private RestaurantAdapter restaurantAdapter;
    private List<Business> businesses = new ArrayList<>();
    private BusinessServiceImpl businessService;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favourite, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        businessService = new BusinessServiceImpl(getContext());
        initRecyclerView(view);
        fetchDataFromDB();
    }

    private void initRecyclerView(View view) {
        listView = view.findViewById(R.id.recycle_list);
        restaurantAdapter = new RestaurantAdapter(businesses);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        listView.addItemDecoration(new DividerItemDecoration(listView.getContext(), DividerItemDecoration.VERTICAL));
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(restaurantAdapter);
    }

    public void fetchDataFromDB() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                businesses.addAll(businessService.getAll());
                return null;
            }

            @Override
            protected void onPostExecute(Void agentsCount) {
                restaurantAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

}