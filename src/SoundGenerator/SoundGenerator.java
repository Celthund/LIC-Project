package SoundGenerator;

import SerialEmitter.SerialEmitter;

public class SoundGenerator {
    private static int DATA_SIZE = 4;
    private static int STOP_CMD = 0x0, PLAY_CMD = 0x1, SOUND_CMD = 0x2, VOLUME_CMD = 0x3, DATA_MASK = 0xC;

    public static void main(String[] args) {
        init();
    }

    // Inicia a classe, estabelecendo os valores iniciais.
    public static void init(){
        /**
         Initializes soundController to a known state.
         **/
        SerialEmitter.init();
        setVolume(0);
        setSound(0);
        stop();
    }

    // Envia comando para definir o volume do som
    public static void setVolume(int volume){
        /**
         Send command to serialReceiver to set volume
         **/
        SerialEmitter.send(SerialEmitter.Destination.SSC, DATA_SIZE, VOLUME_CMD | (volume << 2 & DATA_MASK));
    }


    public static void play(int sound){
        /**
         Send command to serialReceiver to play a sound.
         **/
        setSound(sound);
        SerialEmitter.send(SerialEmitter.Destination.SSC, DATA_SIZE, PLAY_CMD);
    }

    private static void setSound(int sound){
        /**
         Send command to serialReceiver to set sound
         **/
        SerialEmitter.send(SerialEmitter.Destination.SSC, DATA_SIZE, SOUND_CMD | (sound << 2 & DATA_MASK));
    }

    // Envia comando para parar o som
    public static void stop(){
        /**
         Send command to serialReceiver to stop playing.
         **/
        SerialEmitter.send(SerialEmitter.Destination.SSC, DATA_SIZE, STOP_CMD);
    }

}
