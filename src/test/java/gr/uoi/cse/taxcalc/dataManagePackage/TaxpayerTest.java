package gr.uoi.cse.taxcalc.dataManagePackage;

import org.junit.Test;

import gr.uoi.cse.taxcalc.dataManagePackage.Receipt.ReceiptFactory;

import static org.junit.Assert.*;

public class TaxpayerTest {
    @Test
    public void getTotalReceiptsAmount() throws Exception {
        Taxpayer taxpayer = new Taxpayer("John", "123456789", "single", "85000");
        taxpayer.addReceiptToList(ReceiptFactory.createNewReceipt("Entertainment", "a", "b", "100.0", "c", "d", "e", "f", "g"));
        taxpayer.addReceiptToList(ReceiptFactory.createNewReceipt("Entertainment", "a", "b", "200.0", "c", "d", "e", "f", "g"));
        taxpayer.addReceiptToList(ReceiptFactory.createNewReceipt("Entertainment", "a", "b", "300.0", "c", "d", "e", "f", "g"));

        assertEquals(taxpayer.getTotalReceiptsAmount(), 600.0, Math.ulp(1.0));
    }

    @Test
    public void getIncome() throws Exception {
        Taxpayer taxpayer = new Taxpayer("John", "123456789", "single", "85000");

        assertEquals(taxpayer.getIncome(), 85000.0, Math.ulp(1.0));
    }

    @Test
    public void getBasicTax() throws Exception {
        Taxpayer taxpayer = new Taxpayer("John", "123456789", "single", "85000");

        assertEquals(taxpayer.getBasicTax(), 5604.3, Math.ulp(1.0));
    }

    @Test
    public void getTaxInxrease() throws Exception {
        Taxpayer taxpayer = new Taxpayer("John", "123456789", "single", "85000");
        taxpayer.addReceiptToList(ReceiptFactory.createNewReceipt("Entertainment", "a", "b", "100.0", "c", "d", "e", "f", "g"));

        assertEquals(taxpayer.getBasicTax(), 5604.3, Math.ulp(1.0));
        assertEquals(taxpayer.getTaxInxrease(), 448.34, Math.ulp(1.0));
    }

    @Test
    public void getTaxDecrease() throws Exception {
        Taxpayer taxpayer = new Taxpayer("John", "123456789", "single", "85000");
        taxpayer.addReceiptToList(ReceiptFactory.createNewReceipt("Entertainment", "a", "b", "42500.0", "c", "d", "e", "f", "g"));

        assertEquals(taxpayer.getBasicTax(), 5604.3, Math.ulp(1.0));
        assertEquals(taxpayer.getTaxDecrease(), 840.64, Math.ulp(1.0));
    }

    @Test
    public void getTotalTax() throws Exception {
        Taxpayer taxpayer = new Taxpayer("John", "123456789", "single", "85000");
        taxpayer.addReceiptToList(ReceiptFactory.createNewReceipt("Entertainment", "a", "b", "42500.0", "c", "d", "e", "f", "g"));

        assertEquals(taxpayer.getBasicTax(), 5604.3, Math.ulp(1.0));
        assertEquals(taxpayer.getTotalTax(), 4763.66, Math.ulp(1.0));
    }

}