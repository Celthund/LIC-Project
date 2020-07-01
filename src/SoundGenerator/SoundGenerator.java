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
        SerialEmitter.init();
        setVolume(0);
        setSound(0);
        stop();
    }

    // Envia comando para definir o volume do som
    public static void setVolume(int volume){
        SerialEmitter.send(SerialEmitter.Destination.SSC, DATA_SIZE, VOLUME_CMD | (volume << 2 & DATA_MASK));
    }

    // Envia comando para reproduzir um som, com a identificação deste
    public static void play(int sound){
        setSound(sound);
        SerialEmitter.send(SerialEmitter.Destination.SSC, DATA_SIZE, PLAY_CMD);
    }

    private static void setSound(int sound){
        SerialEmitter.send(SerialEmitter.Destination.SSC, DATA_SIZE, SOUND_CMD | (sound << 2 & DATA_MASK));
    }

    // Envia comando para parar o som
    public static void stop(){
        SerialEmitter.send(SerialEmitter.Destination.SSC, DATA_SIZE, STOP_CMD);
    }

}
