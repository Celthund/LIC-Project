package SerialEmitter;
import HAL.HAL;

public class SerialEmitter { // Envia tramas para os diferentes módulos Serial Receiver.
    public enum Destination {
        SLCD(0), SSC(1);

        // position relative to the Destination_Masks array
        private final int position;
        Destination(int i) {
            position = i;
        }

        public int getPosition() {
            return position;
        }
    }

    private static int SDX_MASK = 0x02, SHIFT_REQUIRED = 1, SCLK_MASK = 0x04;

    private static int DESTINATION_MASKS[] = {0x08, 0x10};

    public static void main(String[] args) {
        init();
        SerialEmitter.send(SerialEmitter.Destination.SLCD, 5, 0x18);
    }

    public static void init() {
        /**
         Initialize SerialEmitter by initializing HAL and set to one every Mask in DESTINATION_MASKS to
         disable all modules since the modules are active low.
         **/
        HAL.init();
        for (int addr : DESTINATION_MASKS) {
            HAL.setBits(addr);
        }
    }

    public static void send(Destination addr, int size, int data) {
        /**
         Sends the number of bits in data based on the size, to the given addr.
         Bits are sent one at a time.
         Parity bit is the odd variant.

         0x08 = 0000 1000 - Bit selecionado O4
         0x10 = 0001 0000 - Bit selecionado O5

         kit.O3 -> srl./SS LCD - 0001 0000
         kit.O4 -> srs./SS SSC - 00

         Ou OUTPUTUSB vai de O0-O7

         0x08   -  0 0 0 0 1 0 0 0
         usbOUT  - 7 6 5 4 3 2 1 0
         **/

        int parityBit = 0;
        HAL.clrBits(DESTINATION_MASKS[addr.getPosition()]);
        data = data << SHIFT_REQUIRED;
        for(int i = 0; i < size; i++){
            parityBit ^= (SDX_MASK & data >> i);
            HAL.writeBits(SDX_MASK, (data >> i));
            HAL.setBits(SCLK_MASK);
            HAL.clrBits(SCLK_MASK);
        }
        HAL.writeBits(SDX_MASK, ~parityBit);
        HAL.setBits(SCLK_MASK);
        HAL.clrBits(SCLK_MASK);
        HAL.setBits(DESTINATION_MASKS[addr.getPosition()]);
    }
}