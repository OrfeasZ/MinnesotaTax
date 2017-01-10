package gr.uoi.cse.taxcalc.data;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private static ArrayList<Taxpayer> taxpayers = new ArrayList<>();

    public static void addTaxpayer(Taxpayer taxpayer) {
        taxpayers.add(taxpayer);
    }

    public static int getTaxpayerCount() {
        return taxpayers.size();
    }

    public static List<Taxpayer> getTaxpayers() {
        return taxpayers;
    }

    public static Taxpayer getTaxpayerByIndex(int index) {
        return taxpayers.get(index);
    }

    public static void removeTaxpayerByIndex(int index) {
        taxpayers.remove(index);
    }
}