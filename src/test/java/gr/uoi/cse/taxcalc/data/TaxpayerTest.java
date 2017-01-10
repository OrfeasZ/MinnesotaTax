package gr.uoi.cse.taxcalc.data;

import gr.uoi.cse.taxcalc.data.receipts.Receipt;
import gr.uoi.cse.taxcalc.data.receipts.ReceiptKind;
import org.junit.Test;

import gr.uoi.cse.taxcalc.data.receipts.ReceiptFactory;

import static org.junit.Assert.*;

public class TaxpayerTest {
    @Test
    public void getTotalReceiptsAmount() throws Exception {
        Taxpayer taxpayer = new Taxpayer("John", "123456789", FamilyStatus.SINGLE, 85000.0);
        taxpayer.addReceiptToList(new Receipt(ReceiptKind.ENTERTAINMENT, "a", "b", 100.0, "c", "d", "e", "f", "g"));
        taxpayer.addReceiptToList(new Receipt(ReceiptKind.ENTERTAINMENT, "a", "b", 200.0, "c", "d", "e", "f", "g"));
        taxpayer.addReceiptToList(new Receipt(ReceiptKind.ENTERTAINMENT, "a", "b", 300.0, "c", "d", "e", "f", "g"));

        assertEquals(taxpayer.getTotalReceiptsAmount(), 600.0, Math.ulp(1.0));
    }

    @Test
    public void getIncome() throws Exception {
        Taxpayer taxpayer = new Taxpayer("John", "123456789", FamilyStatus.SINGLE, 85000.0);

        assertEquals(taxpayer.getIncome(), 85000.0, Math.ulp(1.0));
    }

    @Test
    public void getBasicTax() throws Exception {
        Taxpayer taxpayer = new Taxpayer("John", "123456789", FamilyStatus.SINGLE, 85000.0);

        assertEquals(taxpayer.getBasicTax(), 5604.3, Math.ulp(1.0));
    }

    @Test
    public void getTaxInxrease() throws Exception {
        Taxpayer taxpayer = new Taxpayer("John", "123456789", FamilyStatus.SINGLE, 85000.0);
        taxpayer.addReceiptToList(new Receipt(ReceiptKind.ENTERTAINMENT, "a", "b", 100.0, "c", "d", "e", "f", "g"));

        assertEquals(taxpayer.getBasicTax(), 5604.3, Math.ulp(1.0));
        assertEquals(taxpayer.getTaxInxrease(), 448.34, Math.ulp(1.0));
    }

    @Test
    public void getTaxDecrease() throws Exception {
        Taxpayer taxpayer = new Taxpayer("John", "123456789", FamilyStatus.SINGLE, 85000.0);
        taxpayer.addReceiptToList(new Receipt(ReceiptKind.ENTERTAINMENT, "a", "b", 42500.0, "c", "d", "e", "f", "g"));

        assertEquals(taxpayer.getBasicTax(), 5604.3, Math.ulp(1.0));
        assertEquals(taxpayer.getTaxDecrease(), 840.64, Math.ulp(1.0));
    }

    @Test
    public void getTotalTax() throws Exception {
        Taxpayer taxpayer = new Taxpayer("John", "123456789", FamilyStatus.SINGLE, 85000.0);
        taxpayer.addReceiptToList(new Receipt(ReceiptKind.ENTERTAINMENT, "a", "b", 42500.0, "c", "d", "e", "f", "g"));

        assertEquals(taxpayer.getBasicTax(), 5604.3, Math.ulp(1.0));
        assertEquals(taxpayer.getTotalTax(), 4763.66, Math.ulp(1.0));
    }

    @Test
    public void calculateBaseTax() throws Exception {
        Taxpayer taxpayer = new Taxpayer("John", "123456789", FamilyStatus.SINGLE, 85000.0);

        assertEquals(0.0, taxpayer.calculateBaseTax(0), Math.ulp(1.0));
        assertEquals(1320.3799999999999, taxpayer.calculateBaseTax(1), Math.ulp(1.0));
        assertEquals(5296.58, taxpayer.calculateBaseTax(2), Math.ulp(1.0));
        assertEquals(5996.80, taxpayer.calculateBaseTax(3), Math.ulp(1.0));
        assertEquals(10906.19, taxpayer.calculateBaseTax(4), Math.ulp(1.0));
    }

}