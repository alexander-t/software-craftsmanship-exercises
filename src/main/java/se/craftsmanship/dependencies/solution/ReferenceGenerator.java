package se.craftsmanship.dependencies.solution;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class ReferenceGenerator {
    private static ReferenceGenerator instance;
    private final Random random = new Random();
    private static final long MASK = 0xabc0cba;

    private ReferenceGenerator() {
    }

    public static ReferenceGenerator getInstance() {
        if (instance == null) {
            instance = new ReferenceGenerator();
        }
        return instance;
    }

    public String generateInvoiceReference(long customerId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(Calendar.getInstance().getTime()) + "-"
                + (customerId ^ MASK) + "-"
                + random.longs(10000000, 99999999).findFirst().getAsLong();
    }
}