package com.example.easyfind.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.easyfind.R;
import com.example.easyfind.adapter.RestDetailImgAdapter;
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

    private Business res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_detail);

        initView();


    }

    private void initView() {

        rName = findViewById(R.id.name);
        rPrice = findViewById(R.id.price);
        rDesc = findViewById(R.id.description);
        callTV = findViewById(R.id.callTxt);
        addressTV = findViewById(R.id.address);


        callBtn = findViewById(R.id.callImgBtn);
        msgBtn = findViewById(R.id.message);
        mapBtn = findViewById(R.id.mapBtn);



        //
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialContactPhone("123123123");

            }
        });

        //
        msgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendSMS("Enquiry!");
            }
        });


        //
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                Intent vAct = new Intent(v.getContext(), MapActivity.class);

                vAct.putExtra("lat", res.getCoordinates().getLatitude());
                vAct.putExtra("long", res.getCoordinates().getLongitude());

                v.getContext().startActivity(vAct);

            }
        });

        fetchData();

    }

    public void sendSMS(String message) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(res.getPhone(), null, message, null, null);
    }

    private void dialContactPhone(final String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }

    private void fetchData() {
        apiInterface = APIClient.getRetrofit().create(GetDataService.class);
        Call<Business> call = apiInterface.getBusinessDetail("NYa-JphaaB41ElGsb3iawA");
        call.enqueue(new Callback<Business>() {
            @Override
            public void onResponse(Call<Business> call, Response<Business> response) {
                response.isSuccessful();
                if (response.isSuccessful()) {

                    res = response.body();

                  //  Log.i(String.valueOf(res.getPhotos()), "");

                    //
                    listView = findViewById(R.id.imgRecyclerView);
                    restaurantAdapter = new RestDetailImgAdapter(res.getPhotos());
                    RecyclerView.LayoutManager layM = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
                   // listView.addItemDecoration(new DividerItemDecoration(listView.getContext(), DividerItemDecoration.HORIZONTAL));
                    listView.setLayoutManager(layM);
                    listView.setAdapter(restaurantAdapter);

                    //
                    rName.setText(res.getName());
                    rPrice.setText(res.getPrice());
                    rDesc.setText(res.getAlias());
                    callTV.setText(res.getPhone());
                    addressTV.setText(res.getLocation().getDisplayAddress().toString());

                } else {

                }
            }
            @Override
            public void onFailure(Call<Business> call, Throwable t) {
                call.cancel();
            }
        });
    }
}
