package com.qfang.examples.katas.moviestore;

/**
 * Created by walle on 2017/4/30.
 */
public class CommonRental implements Rental {

    private final Movie movie;
    private final int daysRented;

    public CommonRental(Movie movie, int daysRented) {
        this.movie = movie;
        this.daysRented = daysRented;
    }

    public int getDaysRented() {
        return daysRented;
    }

    public Movie getMovie() {
        return movie;
    }

    @Override
    public float calculateRentalMoney() {
        return movie.calculateRentalMoney(this.daysRented);
    }

    @Override
    public int calculateFrequentRenterPoints() {
        return movie.calculateFrequentRenterPoints(this.daysRented);
    }

}
