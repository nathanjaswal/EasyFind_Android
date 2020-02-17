package com.example.easyfind.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.easyfind.R;
import com.example.easyfind.adapter.RestDetailImgAdapter;
import com.example.easyfind.adapter.RestaurantAdapter;
import com.example.easyfind.models.BaseBusiness;
import com.example.easyfind.models.Business;
import com.example.easyfind.store.APIClient;
import com.example.easyfind.store.GetDataService;

import java.util.ArrayList;
import java.util.List;

public class RestDetailActivity extends AppCompatActivity {

    private RecyclerView listView;
    private RestDetailImgAdapter restaurantAdapter;
    private Business detail;
    private List<String> imgList = new ArrayList<>();

    private TextView rName;
    private TextView rPrice;
    private TextView rDesc;
    private TextView callTV;
    private TextView addressTV;
    private ImageButton callBtn;
    private ImageButton msgBtn;
    private ImageButton mapBtn;

    private GetDataService apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_detail);

        initView();

        fetchData();
    }

    private void initView() {

        rName = findViewById(R.id.name);
        rPrice = findViewById(R.id.price);
        rDesc = findViewById(R.id.description);
        callTV = findViewById(R.id.callTxt);
        addressTV = findViewById(R.id.address);
        listView = findViewById(R.id.recycle_list);

        callBtn = findViewById(R.id.callImgBtn);
        msgBtn = findViewById(R.id.message);
        mapBtn = findViewById(R.id.mapBtn);

        restaurantAdapter = new RestDetailImgAdapter(imgList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
        listView.addItemDecoration(new DividerItemDecoration(listView.getContext(), DividerItemDecoration.HORIZONTAL));
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(restaurantAdapter);

        //
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noString = no.getText().toString();
                String msgMsg = msg.getText().toString();
                sendSms(noString, msgMsg);

            }
        });

        //
        msgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialContactPhone("123123123");
            }
        });


        //
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
//                Intent vAct = new Intent(v.getContext(), RestDetailActivity.class);
//
//     /           v.getContext().startActivity(vAct);

            }
        });

    }

    public void sendSMS(String message) {
        SmsManager smsManager = SmsManager.getDefault();
        //smsManager.sendTextMessage();//TextMessage(readcontacts(context, "john"), null, message, null, null);

    }

    private void dialContactPhone(final String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }

    private void fetchData() {
        apiInterface = APIClient.getRetrofit().create(GetDataService.class);
        Call<Business> call = apiInterface.getDetsinBusiness();
        call.enqueue(new Callback<BaseBusiness>() {
            @Override
            public void onResponse(Call<BaseBusiness> call, Response<BaseBusiness> response) {
                response.isSuccessful();
                if (response.isSuccessful()) {
                    Business = response.body();
                    businesses.addAll(baseBusiness.getBusinesses());
                    restaurantAdapter.notifyDataSetChanged();
                } else {

                }
            }
            @Override
            public void onFailure(Call<BaseBusiness> call, Throwable t) {
                call.cancel();
            }
        });
    }
}
