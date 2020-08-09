package com.racoo.simplelottery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    private List<LottoData> mDataset;
    private SimpleDateFormat fourteen_format = new SimpleDateFormat("HH:mm MM/dd/yyyy");



    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView TextView_lotto_num;
        public TextView TextView_pickForm;
        public TextView TextView_date;


        public MyViewHolder(View v) {
            super(v);

            TextView_lotto_num = (TextView) v.findViewById(R.id.TextView_lotto_num);
            TextView_pickForm = (TextView) v.findViewById(R.id.TextView_pickForm);
            TextView_date = (TextView) v.findViewById(R.id.TextView_date);

        }
    }


    public HistoryAdapter(List<LottoData> arrayLottoData){
        mDataset = arrayLottoData;
    }


    @Override
    public HistoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view

        LinearLayout v;

        v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_history_adapter_layout, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;

    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        if(mDataset != null){
            String String_textNum = "";
            String String_textForm = "";
            String String_date = "";

            Date date_now = mDataset.get(position).getDate_now();

            int pick_ball_arr[] = mDataset.get(position).getPick_ball_arr();
            int bonus_ball_arr[] = mDataset.get(position).getBonus_ball_arr();

            int pick_num = mDataset.get(position).getPick_num();
            int total_num = mDataset.get(position).getTotal_num();
            int bonus_num = mDataset.get(position).getBonus_num();


            for(int i = 0; i<pick_num;i++){
                String_textNum += pick_ball_arr[i] + " ";
            }

            if(bonus_num>0){
                String_textNum += "+ ";
                for(int i = 0; i<bonus_num;i++){
                    String_textNum += bonus_ball_arr[i] + " ";
                }
            }

            String_textNum = String_textNum.substring(0, String_textNum.length()-1);

            String_textForm = pick_num+"/"+total_num+"/"+bonus_num;

            String_date = fourteen_format.format(date_now);


            holder.TextView_lotto_num.setText(String_textNum);

            holder.TextView_pickForm.setText(String_textForm);

            holder.TextView_date.setText(String_date);

        }

    }

    @Override
    public int getItemCount() {
        return mDataset != null  ?  mDataset.size() : 0;
    }


}