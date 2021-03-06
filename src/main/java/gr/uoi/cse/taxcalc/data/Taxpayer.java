package gr.uoi.cse.taxcalc.data;

import gr.uoi.cse.taxcalc.data.receipts.Receipt;
import gr.uoi.cse.taxcalc.data.receipts.ReceiptKind;
import gr.uoi.cse.taxcalc.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Taxpayer {
    private static final Map<FamilyStatus, Double[]> IncomeBoundaries;
    private static final Map<FamilyStatus, Double[]> IncomeTaxPercentages;

    static {
        IncomeBoundaries = new HashMap<>();
        IncomeBoundaries.put(FamilyStatus.MARRIED_JOINTLY, new Double[]{0.0, 36080.0, 90000.0, 143350.0, 25420.0});
        IncomeBoundaries.put(FamilyStatus.MARRIED_SEPARATELY, new Double[]{0.0, 18040.0, 71680.0, 90000.0, 127120.0});
        IncomeBoundaries.put(FamilyStatus.SINGLE, new Double[]{0.0, 24680.0, 81080.0, 90000.0, 152540.0});
        IncomeBoundaries.put(FamilyStatus.HOUSEHOLD_HEAD, new Double[]{0.0, 30390.0, 90000.0, 122110.0, 203390.0});
    }

    static {
        IncomeTaxPercentages = new HashMap<>();
        IncomeTaxPercentages.put(FamilyStatus.MARRIED_JOINTLY, new Double[]{0.0535, 0.0705, 0.0705, 0.0785, 0.0985});
        IncomeTaxPercentages.put(FamilyStatus.MARRIED_SEPARATELY, new Double[]{0.0535, 0.0705, 0.0785, 0.0785, 0.0985});
        IncomeTaxPercentages.put(FamilyStatus.SINGLE, new Double[]{0.0535, 0.0705, 0.0785, 0.0785, 0.0985});
        IncomeTaxPercentages.put(FamilyStatus.HOUSEHOLD_HEAD, new Double[]{0.0535, 0.0705, 0.0705, 0.0785, 0.0985});
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

    public Taxpayer(final String taxpayerName,
                    final String taxpayerAfm,
                    final FamilyStatus taxpayerStatus,
                    final Double taxpayerIncome) {
        name = taxpayerName;
        afm = taxpayerAfm;
        familyStatus = taxpayerStatus;
        income = taxpayerIncome;
        receipts = new ArrayList<>();

        calculateTaxes();
    }

    private void calculateTaxes() {
        basicTax = calculateTax();
        totalTax = basicTax;
        taxIncrease = 0;
        taxDecrease = 0;
    }

    double calculateBaseTax(final int index) {
        if (index == 0) {
            return 0.0;
        }

        Double[] bounds = IncomeBoundaries.get(familyStatus);
        Double[] perc = IncomeTaxPercentages.get(familyStatus);

        return ((bounds[index] - bounds[index - 1])
                * perc[index - 1]) + calculateBaseTax(index - 1);
    }

    private double calculateTax() {
        Double[] bounds = IncomeBoundaries.get(familyStatus);
        Double[] perc = IncomeTaxPercentages.get(familyStatus);

        for (int i = 1; i < bounds.length; ++i) {
            if (income >= bounds[i]) {
                continue;
            }

            return calculateBaseTax(i - 1)
                    + (perc[i] * (income - bounds[i - 1]));
        }

        return calculateBaseTax(4) + (perc[4] * (income - bounds[4]));
    }

    private void calculateTaxVariation() {
        double totalReceiptsAmount = getTotalReceiptsAmount();

        taxIncrease = 0;
        taxDecrease = 0;

        if ((totalReceiptsAmount / income) < 0.2) {
            taxIncrease = basicTax * 0.08;
        } else if ((totalReceiptsAmount / income) < 0.4) {
            taxIncrease = basicTax * 0.04;
        } else if ((totalReceiptsAmount / income) < 0.6) {
            taxDecrease = basicTax * 0.15;
        } else {
            taxDecrease = basicTax * 0.30;
        }

        totalTax = basicTax + taxIncrease - taxDecrease;
    }

    public Receipt getReceiptById(final int receiptID) {
        return receipts.get(receiptID);
    }

    public ArrayList<Receipt> getReceipts() {
        return receipts;
    }

    public void addReceipt(final Receipt receipt) {
        receipts.add(receipt);
        calculateTaxVariation();
    }

    public void removeReceiptByIndex(final int index) {
        receipts.remove(index);
        calculateTaxVariation();
    }

    public double getReceiptsTotalAmount(final ReceiptKind kind) {
        double basicReceiptsTotalAmount = 0;

        for (Receipt receipt : receipts) {
            if (receipt.getKind() == kind) {
                basicReceiptsTotalAmount += receipt.getAmount();
            }
        }

        return Utils.roundDouble(basicReceiptsTotalAmount);
    }

    public double getTotalReceiptsAmount() {
        double totalReceiptsAmount = 0;

        for (Receipt receipt : receipts) {
            totalReceiptsAmount += receipt.getAmount();
        }

        return Utils.roundDouble(totalReceiptsAmount);
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
        return Utils.roundDouble(income);
    }

    public double getBasicTax() {
        return Utils.roundDouble(basicTax);
    }

    public double getTaxIncrease() {
        return Utils.roundDouble(taxIncrease);
    }

    public double getTaxDecrease() {
        return Utils.roundDouble(taxDecrease);
    }

    public double getTotalTax() {
        return Utils.roundDouble(totalTax);
    }

    public String toString() {
        return "Name: " + name + "\nAFM: " + afm
                + "\nStatus: " + familyStatus
                + "\nIncome: " + String.format("%.2f", income)
                + "\nBasicTax: " + String.format("%.2f", basicTax)
                + "\nTaxIncrease: " + String.format("%.2f", taxIncrease)
                + "\nTaxDecrease: " + String.format("%.2f", taxDecrease);
    }
}
