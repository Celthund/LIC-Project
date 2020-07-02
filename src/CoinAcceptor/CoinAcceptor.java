package CoinAcceptor;

import HAL.HAL;
import isel.leic.utils.Time;

public class CoinAcceptor {
    private static int COIN_MASK = 0x40, ACCEPT_MASK = 0x40;

    public static boolean getCoin() {
        /**
         Check if coin there was a coin inserted.
         **/
        boolean hasCoin = HAL.isBit(COIN_MASK);
        if (!hasCoin)
            return false;
        // Send Accept signal
        HAL.setBits(ACCEPT_MASK);
        while (HAL.isBit(COIN_MASK)) {
            Time.sleep(10);
        }
        HAL.clrBits(ACCEPT_MASK);
        return true;
    }

    public static boolean waitCoin(long timeout) {
        /**
         Wait for a coin to be inserted.
         **/
        long start = Time.getTimeInMillis();
        while (true) {
            if (getCoin()) {
                return true;
            }
            if (start + timeout >= Time.getTimeInMillis())
                break;
        }
        return false;
    }

}
