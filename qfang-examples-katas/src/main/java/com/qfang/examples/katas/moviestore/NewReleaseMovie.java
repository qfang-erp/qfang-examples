package com.qfang.examples.katas.moviestore;

/**
 * Created by walle on 2017/4/30.
 */
public class NewReleaseMovie extends Movie {

    protected NewReleaseMovie(String title) {
        super(title);
    }

    @Override
    public float calculateRentalMoney(int daysRented) {
        return daysRented * 3f;
    }

    @Override
    public int calculateFrequentRenterPoints(int daysRented) {
        return daysRented > 1 ? 2 : 1;
    }

}
