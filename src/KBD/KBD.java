package KBD;

import HAL.HAL;
import isel.leic.utils.*;

public class KBD {
    private static final int ACK = 0x80;
    private static final int VAL = 0x10;
    private static final int DATA = 0x0F;
    public static final char NONE = 0;
    private static final char[] keys = {
            '1', '4', '7',
            '*', '2', '5',
            '8', '0', '3',
            '6', '9', '#'};

    public static void main(String[] args) {
        init();
        while(true){
            char c = waitKey(10000);
            if(c != NONE)
                System.out.println("TECLA = " + c);
        }
    }

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
        boolean val = HAL.isBit(VAL); //Obtem VAL do KeyBuffer INPORT
        int key = HAL.readBits(DATA); //Obtem DATA Q0:3 do KeyBuffer INPORT
        if (!val) {                   // se VAL for false nao retorna porque nao ha nada no buffer
            return NONE;
        }
        // Entrou aqui significa que VAL = true e há DATA para o Control consumir!
        // Send ACK signal - Envia para o Keybuffer o ACK a indicar que foi consumido
        HAL.setBits(ACK);
        while (HAL.isBit(VAL)) { //Enquanto o VAL KeyBuffer for true
            Time.sleep(10); // TODO - Serve para garantir que o Keybuffer devolva uma nova tecla?
        }
        HAL.clrBits(ACK);            //Limpa o ACK - KeyBuffer recebe ACK = 0
        if (key > keys.length - 1) // Garante que le um valor valido entre 0-11 (12 teclas)
            return NONE;
        return keys[key]; //devolve a tecla correspondente à tradução em bits recebida no INPORT
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