package gr.uoi.cse.taxcalc.data;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private static String dataFolder;
    private static ArrayList<Taxpayer> taxpayers = new ArrayList<>();

    public static String getDataFolder() {
        return dataFolder;
    }

    public static void setDataFolder(final String folder) {
        dataFolder = folder;
    }

    public static void addTaxpayer(final Taxpayer taxpayer) {
        taxpayers.add(taxpayer);
    }

    public static int getTaxpayerCount() {
        return taxpayers.size();
    }

    public static List<Taxpayer> getTaxpayers() {
        return taxpayers;
    }

    public static Taxpayer getTaxpayerByIndex(final int index) {
        return taxpayers.get(index);
    }

    public static void removeTaxpayerByIndex(final int index) {
        taxpayers.remove(index);
    }
}
