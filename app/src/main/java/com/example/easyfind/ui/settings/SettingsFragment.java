package com.example.easyfind.ui.settings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.easyfind.R;

public class SettingsFragment extends Fragment {

    private Button call;
    private Button email;
    private Button about;
    private Button logout;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        return root;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);

    }

    private void initViews(View view){
        call = view.findViewById(R.id.callus);
        email = view.findViewById(R.id.emailus);
        about = view.findViewById(R.id.aboutus);
        logout = view.findViewById(R.id.logout);

        //
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialContactPhone("123123123");

            }
        });

        //
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // dialContactPhone("123123123");

            }
        });

        //
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // dialContactPhone("123123123");

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // dialContactPhone("123123123");

            }
        });
    }

    private void dialContactPhone(final String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }
}
