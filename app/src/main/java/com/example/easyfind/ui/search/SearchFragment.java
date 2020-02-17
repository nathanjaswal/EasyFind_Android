package com.example.easyfind.ui.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyfind.R;
import com.example.easyfind.adapter.RestaurantAdapter;
import com.example.easyfind.models.BaseBusiness;
import com.example.easyfind.models.Business;
import com.example.easyfind.store.APIClient;
import com.example.easyfind.store.GetDataService;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    private RecyclerView listView;
    private RestaurantAdapter restaurantAdapter;
    private GetDataService apiInterface;
    private BaseBusiness baseBusiness;
    private List<Business> businesses = new ArrayList<>();
    private KProgressHUD progressHUD;
    private SearchView searchView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initRecyclerView(view);
        fetchData();
    }

    private void initViews(View view) {
        searchView = view.findViewById(R.id.searchView);
        searchView.setQueryHint("Enter location name");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void initRecyclerView(View view) {
        listView = view.findViewById(R.id.recycle_list);
        restaurantAdapter = new RestaurantAdapter(businesses, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        listView.addItemDecoration(new DividerItemDecoration(listView.getContext(), DividerItemDecoration.VERTICAL));
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(restaurantAdapter);
    }

    private void fetchData() {
        progressHUD = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        apiInterface = APIClient.getRetrofit().create(GetDataService.class);
        Call<BaseBusiness> call = apiInterface.getBaseBusiness();
        call.enqueue(new Callback<BaseBusiness>() {
            @Override
            public void onResponse(Call<BaseBusiness> call, Response<BaseBusiness> response) {
                progressHUD.dismiss();
                response.isSuccessful();
                if (response.isSuccessful()) {
                    baseBusiness = response.body();
                    businesses.addAll(baseBusiness.getBusinesses());
                    restaurantAdapter.notifyDataSetChanged();
                } else {

                }
            }
            @Override
            public void onFailure(Call<BaseBusiness> call, Throwable t) {
                progressHUD.dismiss();
                call.cancel();
            }
        });
    }
}