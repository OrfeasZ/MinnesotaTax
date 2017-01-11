package gr.uoi.cse.taxcalc.data.receipts;

import java.math.BigDecimal;

public class Receipt {
    private ReceiptKind kind;
    private String id;
    private String date;
    private double amount;
    private Company company;

    public Receipt(final ReceiptKind receiptKind,
                   final String receiptId,
                   final String receiptDate,
                   final double receiptAmount,
                   final String name,
                   final String country,
                   final String city,
                   final String street,
                   final String number) {
        kind = receiptKind;
        id = receiptId;
        date = receiptDate;
        amount = receiptAmount;
        company = new Company(name, country, city, street, number);
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
        return (new BigDecimal(amount)
                .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
    }

    public Company getCompany() {
        return company;
    }

    public String toString() {
        return "ID: " + id
                + "\nDate: " + date
                + "\nKind: " + kind
                + "\nAmount: " + String.format("%.2f", amount)
                + "\nCompany: " + company.getName()
                + "\nCountry: " + company.getCountry()
                + "\nCity: " + company.getCity()
                + "\nStreet: " + company.getStreet()
                + "\nNumber: " + company.getNumber();
    }
}
