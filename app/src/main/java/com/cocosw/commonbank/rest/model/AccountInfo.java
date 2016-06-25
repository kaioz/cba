package com.cocosw.commonbank.rest.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Project: CommonBank
 * Created by LiaoKai(soarcn) on 2015/5/5.
 */
@SuppressWarnings("unused")
public class AccountInfo implements Serializable{
    public Account account;
    public ArrayList<Transaction> transactions;
    public ArrayList<Transaction> pending;
    public ArrayList<Atm> atms;

    /**
     * Account Model for transition json
     *
     * example
     *
     * "accountName": "Complete Access",
     * "accountNumber": "062005 1709 5888",
     * "available": 226.76,
     * "balance": 246.76
     },
     *
     */
    @SuppressWarnings("unused")
    public static class Account implements Serializable {
        public String accountName;
        public String accountNumber;
        public float available;
        public float balance;
    }


    /**
     * Atm Model
     *
     * example
     *
     * "id": "129382",
     * "name": "Circular Quay Station",
     * "address": "8 Alfred St, Sydney, NSW 2000",
     * "location": {
     * "lat": -33.861382,
     * "lng": 151.210316
     * }
     * },
     *
     */
    @SuppressWarnings("unused")
    public static class Atm implements Serializable{

        public int id;
        public String name;
        public String address;
        public Location location;

        @Override
        public String toString() {
            return "Atm{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", address='" + address + '\'' +
                    ", location=" + location +
                    '}';
        }
    }

    /**
     * Transaction Model
     *
     * example
     *
     * "id": "7ecc19e1a0be36ba2c6f05d06b5d3058",
     * "description": "Wdl ATM CBA ATM TOWN HALL SQUARE NSW 253432 AUS",
     * "effectiveDate": "04/09/2013",
     * "amount": -50.00,
     * "atmId": "137483"
     *
     */
    @SuppressWarnings("unused")
    public static class Transaction implements Serializable{
        public String id;
        public String description;
        public Date effectiveDate;
        public float amount;
        public int atmId;
        public boolean pending;

        @Override
        public String toString() {
            return "Transaction{" +
                    "id='" + id + '\'' +
                    ", description='" + description + '\'' +
                    ", effectiveDate=" + effectiveDate +
                    ", amount=" + amount +
                    ", atmId=" + atmId +
                    ", pending=" + pending +
                    '}';
        }
    }


    /**
     * Location Model
     *
     * example
     *
     * "location": {
     * "lat": -33.861382,
     * "lng": 151.210316
     * }
     *
     */
    @SuppressWarnings("unused")
    public static class Location implements Serializable{
        public float lat;
        public float lng;

        @Override
        public String toString() {
            return "Location{" +
                    "lat=" + lat +
                    ", lng=" + lng +
                    '}';
        }
    }
}
