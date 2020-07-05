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


    private static int SDX_MASK = 0x02, SHIFT_REQUIRED = 1, SCLK_MASK = 0x04; //TODO porque sao estes valores especificos?

    private static int DESTINATION_MASKS[] = {0x08, 0x10}; //TODO porque sao estes valores especificos?

    public static void main(String[] args) {
        init();
        SerialEmitter.send(SerialEmitter.Destination.SLCD, 5, 0x1F);
    }

    //dá disable low dos modulos LCD e SSC
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

    //envia trama em serie
    public static void send(Destination addr, int size, int data) {
        /**
         Sends the number of bits in data based on the size, to the given addr.
         Bits are sent one at a time.
         Parity bit is the odd variant.

         0x08 = 0000 1000 - Bit selecionado O4
         0x10 = 0001 0000 - Bit selecionado O5
         kit.O3 -> srl./SS LCD - 0001 0000
         kit.O4 -> srs./SS SSC - 00

         0x08   -  0 0 0 0 1 0 0 0
         usbOUT  - 7 6 5 4 3 2 1 0
         **/

        //EX: DATA = 0x1F -> 0001 1111
        int parityBit = 0;
        HAL.clrBits(DESTINATION_MASKS[addr.getPosition()]);  //ENABLE low DO ENDEREÇO A ENTREGAR TRAMA
        data = data << SHIFT_REQUIRED;  //TODO porque é que o primeiro bit a enviar para o Serial receiver é 0?
        for(int i = 0; i < size; i++){
            int a = (data >> i);
            parityBit ^= (SDX_MASK & data >> i); // ACUMULA BIT PARIDADE A MEDIDA QUE FAZ XOR DOS BITS DE DATA(length = size)
            HAL.writeBits(SDX_MASK, (data >> i)); // envia o currbit da DATA -> Shift Register do Serial Receiver
            HAL.setBits(SCLK_MASK); //CLK = 1 ao Bloco Serial Receiver(ShiftRegister e Counter)
            HAL.clrBits(SCLK_MASK); //CLK = 0
        }
        HAL.writeBits(SDX_MASK, ~parityBit); //Envia bit paridade IMPAR(~)
        HAL.setBits(SCLK_MASK); //CLK = 1
        HAL.clrBits(SCLK_MASK); //CLK = 0
        HAL.setBits(DESTINATION_MASKS[addr.getPosition()]); // DISABLE DO MODULO ESCOLHIDO
    }
}