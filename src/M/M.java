package M;

import HAL.HAL;

public class M {
    private static int M_MASK = 0x80;
/*
      0x80 - 1 0 0 0 0 0 0 0
    usbOUT - 7 6 5 4 3 2 1 0

    M_MASK = USB OUTPORT 7
    m.out -> kit.I7
*/
    // verifica se switch de manutençao ficou a 1
    public static boolean checkMButton(){
        /**
         Check bit a bit maintenance button.
         **/
        return HAL.isBit(M_MASK);
    }
}
