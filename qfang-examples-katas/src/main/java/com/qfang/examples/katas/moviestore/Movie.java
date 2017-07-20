package com.qfang.examples.katas.moviestore;

/**
 * Created by walle on 2017/4/30.
 */
public abstract class Movie {

    protected final String title;

    protected Movie(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public abstract float calculateRentalMoney(int daysRented);

    public int calculateFrequentRenterPoints(int daysRented) {
        return 1;
    }

}
