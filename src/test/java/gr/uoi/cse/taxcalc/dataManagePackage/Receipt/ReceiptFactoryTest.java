package gr.uoi.cse.taxcalc.dataManagePackage.Receipt;

import org.junit.Test;

import static org.junit.Assert.*;

public class ReceiptFactoryTest {
    @Test
    public void createNewEntertainmentReceipt() throws Exception {
        Receipt receipt = ReceiptFactory.createNewReceipt("Entertainment", "a", "b", "12.0", "c", "d", "e", "f", "g");
        assertTrue(receipt instanceof EntertainmentReceipt);
        assertEquals(receipt.getKind(), "Entertainment");
    }

    @Test
    public void createNewBasicReceipt() throws Exception {
        Receipt receipt = ReceiptFactory.createNewReceipt("Basic", "a", "b", "12.0", "c", "d", "e", "f", "g");
        assertTrue(receipt instanceof BasicReceipt);
        assertEquals(receipt.getKind(), "Basic");
    }

    @Test
    public void createNewTravelReceipt() throws Exception {
        Receipt receipt = ReceiptFactory.createNewReceipt("Travel", "a", "b", "12.0", "c", "d", "e", "f", "g");
        assertTrue(receipt instanceof TravelReceipt);
        assertEquals(receipt.getKind(), "Travel");
    }

    @Test
    public void createNewHealthReceipt() throws Exception {
        Receipt receipt = ReceiptFactory.createNewReceipt("Health", "a", "b", "12.0", "c", "d", "e", "f", "g");
        assertTrue(receipt instanceof HealthReceipt);
        assertEquals(receipt.getKind(), "Health");
    }

    @Test
    public void createNewOtherReceipt() throws Exception {
        Receipt receipt = ReceiptFactory.createNewReceipt("Other", "a", "b", "12.0", "c", "d", "e", "f", "g");
        assertTrue(receipt instanceof OtherReceipt);
        assertEquals(receipt.getKind(), "Other");
    }

    @Test
    public void createNewInvalidReceipt() throws Exception {
        Receipt receipt = ReceiptFactory.createNewReceipt("Invalid", "a", "b", "12.0", "c", "d", "e", "f", "g");
        assertNull(receipt);
    }
}