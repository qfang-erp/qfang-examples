package com.qfang.examples.katas.moviestore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by walle on 2017/4/30.
 */
public class RentalCollection implements Rental {

    private List<Rental> list = new ArrayList<>();

    public RentalCollection(Collection<Rental> rentals) {
        this.list.addAll(rentals);
    }

    public RentalCollection addRental(Rental rental) {
        list.add(rental);
        return this;
    }

    @Override
    public float calculateRentalMoney() {
        return (float) list.stream().mapToDouble(Rental::calculateRentalMoney).sum();
    }

    @Override
    public int calculateFrequentRenterPoints() {
        return list.stream().mapToInt(Rental::calculateFrequentRenterPoints).sum();
    }
}
