package gr.uoi.cse.taxcalc.util;

import java.math.BigDecimal;

public class Utils {
    public static double roundDouble(double value) {
        return (new BigDecimal(value)
                .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
    }
}
