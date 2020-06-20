package M;

import HAL.HAL;

public class M {
    private static int M_MASK = 0x80;

    public static boolean checkMButton(){

        return HAL.isBit(M_MASK);
    }
}
