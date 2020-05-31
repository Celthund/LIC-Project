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

    private static int dataAddr = 0x02, shiftRequired = 1, clockAddr = 0x04;

    // zero é o lcd e um é o sound
    private static int DESTINATION_MASKS[] = {0x08, 0x10};

    public static void main(String[] args) {
        init();
        SerialEmitter.send(SerialEmitter.Destination.SLCD, 5, 0x18);
    }

    // Inicia a classe
    public static void init() {
        HAL.init();
        for (int addr : DESTINATION_MASKS) {
            HAL.setBits(addr);
        }
    }


    // Envia uma trama para o SerialReceiver identificado por addr, com a dimensão de size e os bits de ‘data’.
    public static void send(Destination addr, int size, int data) {
        int parityBit = 0;
        HAL.clrBits(DESTINATION_MASKS[addr.getPosition()]);
        data = data << shiftRequired;
        for(int i = 0; i < size; i++){
            parityBit ^= (dataAddr & data >> i);
            HAL.writeBits(dataAddr, (data >> i));
            HAL.setBits(clockAddr);
            HAL.clrBits(clockAddr);
        }
        HAL.writeBits(dataAddr, parityBit);
        HAL.setBits(clockAddr);
        HAL.clrBits(clockAddr);
        HAL.setBits(DESTINATION_MASKS[addr.getPosition()]);
    }
}