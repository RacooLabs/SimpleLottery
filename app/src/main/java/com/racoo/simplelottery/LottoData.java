package com.racoo.simplelottery;

import java.io.Serializable;
import java.util.Date;

public class LottoData implements Serializable {


    int pick_ball_arr[] = new int[20];
    int bonus_ball_arr[] = new int[3];

    int pick_num;
    int total_num;
    int bonus_num;

    Date date_now;

    public int[] getPick_ball_arr() {
        return pick_ball_arr;
    }

    public void setPick_ball_arr(int[] pick_ball_arr) {
        this.pick_ball_arr = pick_ball_arr.clone();
    }

    public int[] getBonus_ball_arr() {
        return bonus_ball_arr;
    }

    public void setBonus_ball_arr(int[] bonus_ball_arr) {
        this.bonus_ball_arr = bonus_ball_arr.clone();
    }

    public int getPick_num() {
        return pick_num;
    }

    public void setPick_num(int pick_num) {
        this.pick_num = pick_num;
    }

    public int getTotal_num() {
        return total_num;
    }

    public void setTotal_num(int total_num) {
        this.total_num = total_num;
    }

    public int getBonus_num() {
        return bonus_num;
    }

    public void setBonus_num(int bonus_num) {
        this.bonus_num = bonus_num;
    }

    public Date getDate_now() {
        return date_now;
    }

    public void setDate_now(Date date_now) {
        this.date_now = date_now;
    }


}
