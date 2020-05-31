package HAL;

import isel.leic.UsbPort;

public class HAL {
    private static final int ZEROS = 0xFF;
    private static int state = 0xAF;

    public static void main(String[] args) {
        init();
    }

    public static void init() {
        /**
         Resets output bits.
         **/
        clrBits(ZEROS);
    }

    public static boolean isBit(int mask) {
        /**
         Read input to bits based on given mask
         Ex.: mask 0xAA (1010 1010) value 2 will change the bits to (0-0- 0-1-)
         **/
        return readBits(mask) != 0;
    }

    public static int readBits(int mask) {
        /**
         Read input to bits based on given mask
         Ex.: mask 0xAA (1010 1010) value 2 will change the bits to (0-0- 0-1-)
         **/
        return mask & UsbPort.in();
    }

    public static void writeBits(int mask, int value) {
        /**
         Set bits to value restricted by a given mask
         Ex.: mask 0xAA (1010 1010) value 7 will change the bits to (0-0- 0-1-)
         **/
        state = (state & ~mask) | (mask & value);
        setBits(state);
    }

    public static void setBits(int mask) {
        /**
         Set bits to '1' restricted by a given mask
         Ex.: mask 0xAA (1010 1010) will set '1' where '1' is placed
         **/
        state |= mask;
        UsbPort.out(state);
    }

    public static void clrBits(int mask) {
        /**
         Set bits to '0' restricted by a given mask
         Ex.: mask 0xAA (1010 1010) will set '0' where '1' is placed
         **/
        state &= ~mask;
        UsbPort.out(state);
    }
}