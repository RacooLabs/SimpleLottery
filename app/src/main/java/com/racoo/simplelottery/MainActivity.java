package com.racoo.simplelottery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private NumberPicker pick_ball;
    private NumberPicker all_ball;
    private NumberPicker bonus_ball;
    private TextView TextView_startButton;
    private TextView ball_object[];
    private TextView bonus_ball_object[];
    private LinearLayout LinearLayout_ballLine[];
    private LinearLayout LinearLayout_bonusBallLine;
    private LinearLayout LinearLayout_plusLine;
    private LinearLayout LinearLayout_history;

    private AdView mAdView;


    private int pick_num = 0;
    private int total_num = 0;
    private int bonus_num = 0;

    private int lotto[] = null;
    private int bonusLotto[] = null;

    private List<LottoData> arrayLottoData;
    private Date date_now = new Date(System.currentTimeMillis());




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this,  "ca-app-pub-7972968096388401~2845347199");
        mAdView = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        arrayLottoData = load();

        setBind();
        setNpicker();
        initialize();


        TextView_startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGetNumber();
                save(arrayLottoData);
            }
        });

        LinearLayout_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                //intent.putExtra("HistoryData", data);
                intent.putExtra("arrayLottoData", (Serializable) arrayLottoData);
                startActivityForResult(intent, 0);


            }
        });


    }

    public void initialize(){
        if(arrayLottoData.size()==0){
            setObjectClear();

            pick_ball.setValue(6);
            all_ball.setValue(45);
            bonus_ball.setValue(1);

        }else{

            setObjectClear();
            LottoData lottoData = arrayLottoData.get(arrayLottoData.size()-1);


            pick_num = lottoData.getPick_num();
            total_num = lottoData.getTotal_num();
            bonus_num = lottoData.getBonus_num();

            pick_ball.setValue(pick_num);
            all_ball.setValue(total_num);
            bonus_ball.setValue(bonus_num);

            lotto = new int[lottoData.getPick_num()];
            bonusLotto = new int[lottoData.getBonus_num()];


            for(int i=0;i<lottoData.getPick_num();i++){
                lotto[i] = lottoData.getPick_ball_arr()[i];
            }

            for(int i=0;i<lottoData.getBonus_num();i++){
                bonusLotto[i] = lottoData.getBonus_ball_arr()[i];
            }

            setBallObject();

        }
    }


    public void setBind(){

        pick_ball = (NumberPicker) findViewById(R.id.pick_ball);
        all_ball = (NumberPicker) findViewById(R.id.all_ball);
        bonus_ball = (NumberPicker) findViewById(R.id.bonus_ball);
        TextView_startButton = (TextView) findViewById(R.id.start_button);

        ball_object = new TextView[] {
                (TextView) findViewById(R.id.ball_object1),
                (TextView) findViewById(R.id.ball_object2),
                (TextView) findViewById(R.id.ball_object3),
                (TextView) findViewById(R.id.ball_object4),
                (TextView) findViewById(R.id.ball_object5),
                (TextView) findViewById(R.id.ball_object6),
                (TextView) findViewById(R.id.ball_object7),
                (TextView) findViewById(R.id.ball_object8),
                (TextView) findViewById(R.id.ball_object9),
                (TextView) findViewById(R.id.ball_object10),
                (TextView) findViewById(R.id.ball_object11),
                (TextView) findViewById(R.id.ball_object12),
                (TextView) findViewById(R.id.ball_object13),
                (TextView) findViewById(R.id.ball_object14),
                (TextView) findViewById(R.id.ball_object15),
                (TextView) findViewById(R.id.ball_object16),
                (TextView) findViewById(R.id.ball_object17),
                (TextView) findViewById(R.id.ball_object18)
        };

        bonus_ball_object = new TextView[]{
                (TextView) findViewById(R.id.bonus_ball_object1),
                (TextView) findViewById(R.id.bonus_ball_object2),
                (TextView) findViewById(R.id.bonus_ball_object3)
        };

        LinearLayout_ballLine = new LinearLayout[]{
                (LinearLayout) findViewById(R.id.ballLine1),
                (LinearLayout) findViewById(R.id.ballLine2),
                (LinearLayout) findViewById(R.id.ballLine3)
        };

        LinearLayout_bonusBallLine = findViewById(R.id.bonusBallLine1);
        LinearLayout_plusLine = findViewById(R.id.plusLine);

        LinearLayout_history= (LinearLayout) findViewById(R.id.LinearLayout_history);




    }

    public void setNpicker(){


        pick_ball.setMinValue(1);
        pick_ball.setMaxValue(18);

        all_ball.setMinValue(1);
        all_ball.setMaxValue(100);

        bonus_ball.setMinValue(0);
        bonus_ball.setMaxValue(3);

    }

    public void startGetNumber(){
        pick_num = pick_ball.getValue();
        total_num = all_ball.getValue();
        bonus_num = bonus_ball.getValue();

        if(pick_num + bonus_num>total_num){
            Toast.makeText(getApplication(), "Too many numbers to pick than whole numbers. ",Toast.LENGTH_LONG).show();
        } else {
            lotto = new int[pick_num];
            bonusLotto = new int[bonus_num];

            if(lotto != null && bonusLotto != null){

                for(int i=0;i<lotto.length;i++){
                    lotto[i] = (int)(Math.random()*total_num)+1;

                    for(int j = 0; j<i;j++){
                        if(lotto[j] == lotto[i]){
                            i--;
                            break;
                        }
                    }
                }

                for(int i=0;i<bonusLotto.length;i++){

                    bonusLotto[i] = (int)(Math.random()*total_num)+1;

                    for(int j = 0;j<i;j++){
                        if(bonusLotto[j] == bonusLotto[i]){
                            i--;
                            break;
                        }
                    }

                    for(int j=0; j<lotto.length;j++){
                        if(lotto[j] == bonusLotto[i]){
                            i--;
                            break;
                        }
                    }
                }

                Arrays.sort(lotto);
                Arrays.sort(bonusLotto);

                setBallObject();
                LottoData lottoData = new LottoData();
                lottoData.setPick_ball_arr(lotto);
                lottoData.setBonus_ball_arr(bonusLotto);

                lottoData.setPick_num(pick_num);
                lottoData.setTotal_num(total_num);
                lottoData.setBonus_num(bonus_num);

                lottoData.setDate_now(date_now);

                arrayLottoData.add(lottoData);

            }

        }


    }

    public void setObjectClear(){
        for(int i = 0;i<ball_object.length;i++){
            ball_object[i].setVisibility(View.GONE);
        }
        for(int i = 0;i<LinearLayout_ballLine.length;i++){
            LinearLayout_ballLine[i].setVisibility(View.GONE);
        }

        for (int i = 0; i<bonus_ball_object.length;i++){
            bonus_ball_object[i].setVisibility(View.GONE);
        }

        LinearLayout_bonusBallLine.setVisibility(View.GONE);
        LinearLayout_plusLine.setVisibility(View.GONE);

    }

    public void setBallObject(){

        setObjectClear();

        if(0<pick_num && pick_num<=6){
            LinearLayout_ballLine[0].setVisibility(View.VISIBLE);
            for(int i = 0;i<pick_num;i++){
                ball_object[i].setVisibility(View.VISIBLE);
                ball_object[i].setText(lotto[i]+"");
                setColor(ball_object[i], lotto[i]);

            }

        } else if (6<pick_num && pick_num<=12){

            LinearLayout_ballLine[0].setVisibility(View.VISIBLE);
            LinearLayout_ballLine[1].setVisibility(View.VISIBLE);
            for(int i = 0;i<pick_num;i++){
                ball_object[i].setVisibility(View.VISIBLE);
                ball_object[i].setText(lotto[i]+"");
                setColor(ball_object[i], lotto[i]);
            }

        } else if (12<pick_num && pick_num<=18){
            LinearLayout_ballLine[0].setVisibility(View.VISIBLE);
            LinearLayout_ballLine[1].setVisibility(View.VISIBLE);
            LinearLayout_ballLine[2].setVisibility(View.VISIBLE);
            for(int i = 0;i<pick_num;i++){
                ball_object[i].setVisibility(View.VISIBLE);
                ball_object[i].setText(lotto[i]+"");
                setColor(ball_object[i], lotto[i]);
            }

        }

        if (0<bonus_num && bonus_num<=3){
            LinearLayout_plusLine.setVisibility(View.VISIBLE);
            LinearLayout_bonusBallLine.setVisibility(View.VISIBLE);

            for(int i = 0;i<bonus_num;i++){
                bonus_ball_object[i].setVisibility(View.VISIBLE);
                bonus_ball_object[i].setText(bonusLotto[i]+"");
                setColor(bonus_ball_object[i], bonusLotto[i]);
            }
        }

    }

    public void setColor(TextView ball_object, int lotto_num){

        if(ball_object != null && lotto_num != 0){
            int value = (int) (lotto_num/10);
            GradientDrawable setColorShape = (GradientDrawable) ball_object.getBackground();

            switch(value){
                case 0:
                    setColorShape.setColor(Color.parseColor("#CC2400"));
                    break;
                case 1:
                    setColorShape.setColor(Color.parseColor("#E6A200"));
                    break;
                case 2:
                    setColorShape.setColor(Color.parseColor("#00B050"));
                    break;
                case 3:
                    setColorShape.setColor(Color.parseColor("#0029FA"));
                    break;
                case 4:
                    setColorShape.setColor(Color.parseColor("#870DFF"));
                    break;
                case 5:
                    setColorShape.setColor(Color.parseColor("#C55A11"));
                    break;
                case 6:
                    setColorShape.setColor(Color.parseColor("#B30047"));
                    break;
                case 7:
                    setColorShape.setColor(Color.parseColor("#0099FF"));
                    break;
                case 8:
                    setColorShape.setColor(Color.parseColor("#008080"));
                    break;
                case 9:
                    setColorShape.setColor(Color.parseColor("#313732"));
                    break;

                case 10:
                    setColorShape.setColor(Color.parseColor("#000000"));
                    break;


            }
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {

            case 0:


                if (data != null) {

                    arrayLottoData.clear();
                    setObjectClear();
                    save(arrayLottoData);
                    break;

                }


                break;

        }



    }

    void save(List<LottoData> arrayLottoData){

        List<LottoData> inputdata = new ArrayList<>();
        inputdata.addAll(arrayLottoData);


        SharedPreferences sp = getSharedPreferences("shared", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = gson.toJson(inputdata);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("Set",json );
        editor.commit();

    }


    List<LottoData> load() {


        SharedPreferences sp = getSharedPreferences("shared", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sp.getString("Set", "");
        if (json.isEmpty()) {
            return new ArrayList<>();
        } else {
            Type type = new TypeToken<ArrayList<LottoData>>() {
            }.getType();
            return gson.fromJson(json, type);

        }

    }

}