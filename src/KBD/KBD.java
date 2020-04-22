package KBD;

import HAL.HAL;
import isel.leic.utils.*;

public class KBD {
    private static final int ACK = 0x80;
    private static final int VAL = 0x10;
    private static final int DATA = 0x0F;
    public static final char NONE = 0;
    private static final char[] keys = {'1', '4', '7',
            '*', '2', '5',
            '8', '0', '3',
            '6', '9', '#'};

    public static void init() {
        HAL.init();
    }

    public static char getKey() {
        /**
         Translate  bits to the corresponding char based on array keys.
         Example
         (000 1   0010) will return the char '7'
         ^   ^^^^
         val  key
         **/
        boolean val = HAL.isBit(VAL);
        int key = HAL.readBits(DATA);
        if (!val) {
            return NONE;
        }
        if (key > keys.length - 1)
            return NONE;
        // Send ACK signal
        HAL.setBits(ACK);
        while (HAL.isBit(VAL)) {
            Time.sleep(10);
        }
        HAL.clrBits(ACK);
        return keys[key];
    }

    public static char waitKey(long timeout) {
        /**
         Tries getKey for a given timeout.
         **/
        long start = Time.getTimeInMillis();
        char key;
        while (true) {
            key = getKey();
            if (key != NONE) {
                return key;
            }
            if (start + timeout >= Time.getTimeInMillis())
                break;
        }
        return NONE;
    }
}