package gr.uoi.cse.taxcalc.data.receipts;

public class Company {
    private String name;
    private String country;
    private String city;
    private String street;
    private String number;

    Company(final String compName,
            final String compCountry,
            final String compCity,
            final String compStreet,
            final String compNumber) {
        name = compName;
        country = compCountry;
        city = compCity;
        street = compStreet;
        number = compNumber;
    }

    public String getName() {
        return name;
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
}
