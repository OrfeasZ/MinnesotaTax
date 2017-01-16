package gr.uoi.cse.taxcalc.data.receipts;

import gr.uoi.cse.taxcalc.util.Utils;

public class Receipt {
    private ReceiptKind kind;
    private String id;
    private String date;
    private double amount;
    private String company;
    private String country;
    private String city;
    private String street;
    private String number;

    public Receipt(final ReceiptKind receiptKind,

                   final String receiptId,
                   final String receiptDate,
                   final double receiptAmount,
                   final String compName,
                   final String compCountry,
                   final String compCity,
                   final String compStreet,
                   final String compNumber) {
        kind = receiptKind;
        id = receiptId;
        date = receiptDate;
        amount = receiptAmount;
        company = compName;
        country = compCountry;
        city = compCity;
        street = compStreet;
        number = compNumber;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public ReceiptKind getKind() {
        return kind;
    }

    public double getAmount() {
        return Utils.roundDouble(amount);
    }

    public String getCompany() {
        return company;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getNumber() {
        return number;
    }

    public String toString() {
        return "ID: " + id
                + "\nDate: " + date
                + "\nKind: " + kind
                + "\nAmount: " + String.format("%.2f", amount)
                + "\nCompany: " + company
                + "\nCountry: " + country
                + "\nCity: " + city
                + "\nStreet: " + street
                + "\nNumber: " + number;
    }
}
