package main;

import java.net.URL;

import javax.sound.sampled.*;

public class Sound {
    Clip clip;
    URL soundURL[] = new URL[30];

    public Sound(){
        soundURL[0] = getClass().getResource("res/Sounds/Adventure.wav");
        soundURL[1] = getClass().getResource("res/Sounds/coin.wav");
        soundURL[2] = getClass().getResource("res/Sounds/powerup.wav");
        soundURL[3] = getClass().getResource("res/Sounds/unlock.wav");
        soundURL[4] = getClass().getResource("res/Sounds/fanfare.wav");
        soundURL[5] = getClass().getResource("res/Sounds/Victory.wav");
        soundURL[6] = getClass().getResource("res/Sounds/death.wav");
        soundURL[7] = getClass().getResource("res/Sounds/teleport.wav");
    }

    public void setFile(int i){
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void Play(){
        clip.start();
    }

    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop(){
        clip.stop();
    }
}
