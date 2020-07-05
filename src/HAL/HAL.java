package HAL;

import isel.leic.UsbPort;

public class HAL {
    private static final int ZEROS = 0xFF;
    private static int state = 0xF0;

    // CONSIGO LER INPUT E SABER SE SAO BITS COM BASE NA MASCARA
    // CONSIGO ESCREVER NO OUTPUT COM FARTURA
    public static void main(String[] args) {
//        System.out.println("INPORT = " + readBits(0x0F));
//        setBits(0xF0);
//        System.out.println(isBit(0xF0));
//        writeBits(0x03, 2);
        init();
    }

    public static void init() {
        /**
         Resets output bits.
         **/
        clrBits(ZEROS);
    }
    // INPORT Retorna true se o bit tiver o valor lógico 1
    public static boolean isBit(int mask) {
        /**
         Read input to bits based on given mask
         Ex.: mask 0xAA (1010 1010) value 2 will change the bits to (0-0- 0-1-)
         **/
        return readBits(mask) != 0;
    }
    // INPORT Retorna os valores dos bits representados por mask presentes no UsbPort
    public static int readBits(int mask) {
        /**
         Read input to bits based on given mask
         Ex.: mask 0xAA (1010 1010) value 2 will change the bits to (0-0- 0-1-)
         **/
        return mask & UsbPort.in();
    }
    // OUTPUT Escreve nos bits representados por mask o valor de value
    public static void writeBits(int mask, int value) {
        /**
         Set bits to value restricted by a given mask
         Ex.: mask 0xAA (1010 1010) value 7 (0000 0111) will change the bits to (0-0- 0-1-)
         **/
        state = (state & ~mask) | (mask & value);
        setBits(state);
    }
    //  OUTPUT Coloca os bits representados por mask no valor lógico '1'
    public static void setBits(int mask) {
        /**
         Set bits to '1' restricted by a given mask
         Ex.: mask 0xAA (1010 1010) will set '1' where '1' is placed
         **/
        state |= mask;
        UsbPort.out(state);
    }
    // OUTPUT Coloca os bits representados por mask no valor lógico '0'
    public static void clrBits(int mask) {
        /**
         Set bits to '0' restricted by a given mask
         Ex.: mask 0xAA (1010 1010) will set '0' where '1' is placed
         **/
        state &= ~mask;
        UsbPort.out(state);
    }
}