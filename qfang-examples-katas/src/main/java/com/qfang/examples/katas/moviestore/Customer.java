package com.qfang.examples.katas.moviestore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by walle on 2017/4/30.
 */
public class Customer {

    private String name;
    private List<Rental> rentals = new ArrayList<Rental>();

    public Customer(String name) {
        this.name = name;
    }

    public void addRental(Rental arg) {
        rentals.add(arg);
    }

    public String getName() {
        return name;
    }

    public String statement() {
        String result = "Rental Record for " + getName() + "\n";

        Rental rental = new RentalCollection(this.rentals);

        //add footer lines
        result += "Amount owed is " + String.valueOf(rental.calculateRentalMoney()) + "\n";
        result += "You earned " + String.valueOf(rental.calculateFrequentRenterPoints()) +
                " frequent renter points";
        return result;
    }


}
