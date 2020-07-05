package CoinAcceptor;

import HAL.HAL;
import isel.leic.utils.Time;

public class CoinAcceptor {
    private static int COIN_MASK = 0x40, ACCEPT_MASK = 0x40;
    /*
    0x40   - 0 1 0 0 0 0 0 0
    usbOUT - 7 6 5 4 3 2 1 0
    usbIn  - 7 6 5 4 3 2 1 0
    COIN_MASK = USB OUTPORT 6
    ACCEPT_MASK = USB INPORT 6
    kit.O6 -> ca.accept
    ca.Coin -> kit.I6
    */

    //Logica muito semelhante ao getKey() da classe KBD
    public static boolean getCoin() {
        /**
         Check if coin there was a coin inserted.
         **/
        boolean hasCoin = HAL.isBit(COIN_MASK); //se CoinAcceptor aka Switch tiver coin
        if (!hasCoin)
            return false;       //retorna false, nao houve moedas

        // Send ACK signal - Envia para o CoinAcceptor o ACK a indicar que Moeda foi consumida
        HAL.setBits(ACCEPT_MASK);
        while (HAL.isBit(COIN_MASK)) { // Serve para garantir que o CoinAcceptor efectivamente desligou o coin apos a recepçao do ACK
            Time.sleep(10);
        }
        HAL.clrBits(ACCEPT_MASK);   // limpa o ACCEPT
        return true;    //retorna true, houve uma moeda!
    }

    //funcao que permite estar a olhar para a recepçao de moedas durante timeout
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
        return false; //nao caiu NENHUMA moeda durante o timeout
    }

}
