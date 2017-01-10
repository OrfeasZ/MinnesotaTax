package gr.uoi.cse.taxcalc.data.receipts;

import org.junit.Test;

import static org.junit.Assert.*;

public class ReceiptTest {
    @Test
    public void testReceiptAmount() throws Exception {
        Receipt receipt = new Receipt(ReceiptKind.BASIC, "a", "b", 13.37, "c", "d", "e", "f", "g");
        assertEquals(ReceiptKind.BASIC, receipt.getKind());
        assertEquals(13.37, receipt.getAmount(), Math.ulp(1.0));
    }
}