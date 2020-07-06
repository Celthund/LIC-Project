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
    /*
         0x08   -  0 0 0 0 1 0 0 0
         0x10   -  0 0 0 1 0 0 0 0
         usbOUT -  7 6 5 4 3 2 1 0
         kit.O3 -> srl./SS LCD
         kit.O4 -> srs./SS SSC
    * */

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
        for (int addr : DESTINATION_MASKS) {  //dá disable LOW dos modulos LCD e SSC
            HAL.setBits(addr);
        }
    }

    //envia trama em serie
    public static void send(Destination addr, int size, int data) {
        /**
         Sends the number of bits in data based on the size, to the given addr.
         Bits are sent one at a time.
         Parity bit is the odd variant.
         **/
        //EX: DATA = 0x1F -> 0001 1111
        int parityBit = 0;
        HAL.clrBits(DESTINATION_MASKS[addr.getPosition()]);  //ENABLE low DO ENDEREÇO A ENTREGAR TRAMA
        data = data << SHIFT_REQUIRED;  // Este shift se deve há MASK = 0000 0010
        // Data = 1 11X1 Vamos fazendo shift right para que os valores calhem no "burado" 0000 0010
        // se nao fizessemos o left shift, no i = 0  o valor passado para SDX seria o X
        for(int i = 0; i < size; i++){
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