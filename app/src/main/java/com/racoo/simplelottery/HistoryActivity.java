package com.racoo.simplelottery;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private AdView mAdView;
    private FloatingActionButton delete_button;
    private List<LottoData> arrayLottoData;
    private TextView noHistory;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Serializable serial = getIntent().getSerializableExtra("arrayLottoData");
        arrayLottoData = (ArrayList<LottoData>)serial;



        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setReverseLayout(true);
        ((LinearLayoutManager) layoutManager).setStackFromEnd(true);

        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new HistoryAdapter(arrayLottoData);
        recyclerView.setAdapter(mAdapter);


        setBind();

        if (arrayLottoData.size() == 0) {
            noHistory.setVisibility(View.VISIBLE);
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (arrayLottoData.size() > 0) {

                    builder.setMessage("Are you sure you want to clear all records?");
                    builder.setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    arrayLottoData.clear();
                                    mAdapter.notifyDataSetChanged();
                                    sendData();


                                    if (arrayLottoData.size() == 0) {
                                    noHistory.setVisibility(View.VISIBLE);
                                    }

                                }
                            });
                    builder.setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    return;
                                }
                            });

                    builder.show();

                }


            }


        });


    }

    public void setBind() {
        delete_button = findViewById(R.id.delete_button);
        noHistory = findViewById(R.id.TextView_noHistory);
    }

    public void sendData(){

        Intent intent = new Intent();

        setResult(0,intent);

        finish();

    }


}