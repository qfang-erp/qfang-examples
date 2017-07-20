package com.qfang.examples.katas.moviestore;

/**
 * Created by walle on 2017/4/30.
 */
public class ChildrenMovie extends Movie {

    protected ChildrenMovie(String title) {
        super(title);
    }

    @Override
    public float calculateRentalMoney(int daysRented) {
        float amount = 1.5f;
        if (daysRented > 3)
            amount += (daysRented - 3) * 1.5;
        return amount;
    }

}
