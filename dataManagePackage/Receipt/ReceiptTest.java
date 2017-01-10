package dataManagePackage.Receipt;

import org.junit.Test;

import static org.junit.Assert.*;

public class ReceiptTest {
    @Test
    public void testReceiptAmount() throws Exception {
        Receipt receipt = ReceiptFactory.createNewReceipt("Basic", "a", "b", "13.37", "c", "d", "e", "f", "g");
        assertTrue(receipt instanceof BasicReceipt);
        assertEquals(receipt.getKind(), "Basic");
        assertEquals(receipt.getAmount(), 13.37, Math.ulp(1.0));
    }
}