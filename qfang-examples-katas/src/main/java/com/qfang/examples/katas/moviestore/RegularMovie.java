package com.qfang.examples.katas.moviestore;

/**
 * Created by walle on 2017/4/30.
 */
public class RegularMovie extends Movie {

    protected RegularMovie(String title) {
        super(title);
    }

    @Override
    public float calculateRentalMoney(int daysRented) {
        float amount = 2f;
        if (daysRented > 2)
            amount += (daysRented - 2) * 1.5f;
        return amount;
    }

}
