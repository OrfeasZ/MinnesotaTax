package gr.uoi.cse.taxcalc.data;

import gr.uoi.cse.taxcalc.data.receipts.Receipt;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Taxpayer {
    private static final Map<FamilyStatus, Double[]> incomeBoundaries;
    private static final Map<FamilyStatus, Double[]> incomeTaxPercentages;

    static {
        incomeBoundaries = new HashMap<>();
        incomeBoundaries.put(FamilyStatus.MARRIED_JOINTLY, new Double[]{0.0, 36080.0, 90000.0, 143350.0, 25420.0});
        incomeBoundaries.put(FamilyStatus.MARRIED_SEPARATELY, new Double[]{0.0, 18040.0, 71680.0, 90000.0, 127120.0});
        incomeBoundaries.put(FamilyStatus.SINGLE, new Double[]{0.0, 24680.0, 81080.0, 90000.0, 152540.0});
        incomeBoundaries.put(FamilyStatus.HOUSEHOLD_HEAD, new Double[]{0.0, 30390.0, 90000.0, 122110.0, 203390.0});
    }

    static {
        incomeTaxPercentages = new HashMap<>();
        incomeTaxPercentages.put(FamilyStatus.MARRIED_JOINTLY, new Double[]{0.0535, 0.0705, 0.0705, 0.0785, 0.0985});
        incomeTaxPercentages.put(FamilyStatus.MARRIED_SEPARATELY, new Double[]{0.0535, 0.0705, 0.0785, 0.0785, 0.0985});
        incomeTaxPercentages.put(FamilyStatus.SINGLE, new Double[]{0.0535, 0.0705, 0.0785, 0.0785, 0.0985});
        incomeTaxPercentages.put(FamilyStatus.HOUSEHOLD_HEAD, new Double[]{0.0535, 0.0705, 0.0705, 0.0785, 0.0985});
    }

    private String name;
    private String afm;
    private FamilyStatus familyStatus;
    private double income;
    private double basicTax;
    private double taxIncrease;
    private double taxDecrease;
    private double totalTax;
    private ArrayList<Receipt> receipts;

    public Taxpayer(String name, String afm, FamilyStatus familyStatus, Double income) {
        this.name = name;
        this.afm = afm;
        this.familyStatus = familyStatus;
        this.income = income;

        setBasicTaxBasedOnFamilyStatus();
        taxIncrease = 0;
        taxDecrease = 0;

        receipts = new ArrayList<Receipt>();
    }

    private void setBasicTaxBasedOnFamilyStatus() {
        basicTax = calculateTax();
        totalTax = basicTax;
    }

    double calculateBaseTax(int index) {
        if (index == 0) {
            return 0.0;
        }

        Double[] bounds = incomeBoundaries.get(familyStatus);
        Double[] perc = incomeTaxPercentages.get(familyStatus);

        // ((nextBoundary - lastBoundary) * percentage) + lastBase
        return ((bounds[index] - bounds[index - 1]) * perc[index - 1]) + calculateBaseTax(index - 1);
    }

    double calculateTax() {
        Double[] bounds = incomeBoundaries.get(familyStatus);
        Double[] perc = incomeTaxPercentages.get(familyStatus);

        for (int i = 1; i < bounds.length; ++i) {
            if (income >= bounds[i]) {
                continue;
            }

            return calculateBaseTax(i - 1) + (perc[i] * (income - bounds[i - 1]));
        }

        return calculateBaseTax(4) + (perc[4] * (income - bounds[4]));
    }

    public String toString() {
        return "Name: " + name
                + "\nAFM: " + afm
                + "\nStatus: " + familyStatus
                + "\nIncome: " + String.format("%.2f", income)
                + "\nBasicTax: " + String.format("%.2f", basicTax)
                + "\nTaxIncrease: " + String.format("%.2f", taxIncrease)
                + "\nTaxDecrease: " + String.format("%.2f", taxDecrease);
    }

    public Receipt getReceipt(int receiptID) {
        return receipts.get(receiptID);
    }

    public ArrayList<Receipt> getReceiptsArrayList() {
        return receipts;
    }

    public String[] getReceiptsList() {
        String[] receiptsList = new String[receipts.size()];

        int c = 0;
        for (Receipt receipt : receipts) {
            receiptsList[c++] = receipt.getId() + " | " + receipt.getDate() + " | " + receipt.getAmount();
        }

        return receiptsList;
    }

    public double getBasicReceiptsTotalAmount() {
        double basicReceiptsTotalAmount = 0;

        for (Receipt receipt : receipts) {
            if (receipt.getKind().equals("Basic")) {
                basicReceiptsTotalAmount += receipt.getAmount();
            }
        }

        return (new BigDecimal(basicReceiptsTotalAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
    }

    public double getEntertainmentReceiptsTotalAmount() {
        double entertainmentReceiptsTotalAmount = 0;

        for (Receipt receipt : receipts) {
            if (receipt.getKind().equals("Entertainment")) {
                entertainmentReceiptsTotalAmount += receipt.getAmount();
            }
        }

        return (new BigDecimal(entertainmentReceiptsTotalAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
    }

    public double getTravelReceiptsTotalAmount() {
        double travelReceiptsTotalAmount = 0;

        for (Receipt receipt : receipts) {
            if (receipt.getKind().equals("Travel")) {
                travelReceiptsTotalAmount += receipt.getAmount();
            }
        }

        return (new BigDecimal(travelReceiptsTotalAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
    }

    public double getHealthReceiptsTotalAmount() {
        double healthReceiptsTotalAmount = 0;

        for (Receipt receipt : receipts) {
            if (receipt.getKind().equals("Health")) {
                healthReceiptsTotalAmount += receipt.getAmount();
            }
        }

        return (new BigDecimal(healthReceiptsTotalAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
    }


    public double getOtherReceiptsTotalAmount() {
        double otherReceiptsTotalAmount = 0;

        for (Receipt receipt : receipts) {
            if (receipt.getKind().equals("Other")) {
                otherReceiptsTotalAmount += receipt.getAmount();
            }
        }

        return (new BigDecimal(otherReceiptsTotalAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
    }

    public double getTotalReceiptsAmount() {
        double totalReceiptsAmount = 0;

        for (Receipt receipt : receipts) {
            totalReceiptsAmount += receipt.getAmount();
        }

        return (new BigDecimal(totalReceiptsAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
    }

    public String getName() {
        return name;
    }

    public String getAFM() {
        return afm;
    }

    public FamilyStatus getFamilyStatus() {
        return familyStatus;
    }

    public double getIncome() {
        return (new BigDecimal(income).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
    }

    public double getBasicTax() {
        return (new BigDecimal(basicTax).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
    }

    public double getTaxInxrease() {
        return (new BigDecimal(taxIncrease).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
    }

    public double getTaxDecrease() {
        return (new BigDecimal(taxDecrease).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
    }

    public double getTotalTax() {
        return (new BigDecimal(totalTax).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
    }

    public void addReceiptToList(Receipt receipt) {
        receipts.add(receipt);

        calculateTaxpayerTaxIncreaseOrDecreaseBasedOnReceipts();
    }

    public void removeReceiptFromList(int index) {
        receipts.remove(index);

        calculateTaxpayerTaxIncreaseOrDecreaseBasedOnReceipts();
    }

    private void calculateTaxpayerTaxIncreaseOrDecreaseBasedOnReceipts() {
        double totalReceiptsAmount = 0;
        for (Receipt receipt : receipts) {
            totalReceiptsAmount += receipt.getAmount();
        }

        taxIncrease = 0;
        taxDecrease = 0;
        if ((totalReceiptsAmount / (double) income) < 0.2) {
            taxIncrease = basicTax * 0.08;
        } else if ((totalReceiptsAmount / (double) income) < 0.4) {
            taxIncrease = basicTax * 0.04;
        } else if ((totalReceiptsAmount / (double) income) < 0.6) {
            taxDecrease = basicTax * 0.15;
        } else {
            taxDecrease = basicTax * 0.30;
        }

        totalTax = basicTax + taxIncrease - taxDecrease;
    }
}