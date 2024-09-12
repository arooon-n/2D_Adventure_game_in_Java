package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import entity.characters;
import entity.player;
import tile.Tilemanager;

public class GamePanel extends JPanel implements Runnable {
    // Screen settings
    final int originaltilesize = 16;
    final int scale = 3; // we scale the tile size by this int variable
    public final int tilesize = originaltilesize * scale;//48x48

    public final int maxscreencol = 16;
    public final int maxscreenrow = 12;

    public final int screenwidth = tilesize * maxscreencol;
    public final int screenheight = tilesize * maxscreenrow;

    //World settings 
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 80;

    //FPS
    int FPS = 60;

    //SYSTEM
    public Tilemanager tileM = new Tilemanager(this);
    public KeyHandler key = new KeyHandler(this);
    public Sound music = new Sound();
    public Sound se = new Sound();
    public UI ui = new UI(this);
    public Collisionchecker Cchecker = new Collisionchecker(this);
    public Assetsetter assetSet = new Assetsetter(this);
    Thread gameThread;
    public EventHandler eHandler = new EventHandler(this); 


    //ENTITIES AND OBJECTS
    public player p = new player(this,key);
    public characters obj[] = new characters[10];
    public characters npc[] = new characters[10];
    public characters mon[] = new characters[10];

    //GAME STATE
    public int gameState;
    public final int titlestate = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int GameoverState = 4;
    public final int YouwonState = 5;
    public final int ControlsState = 6;
    

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenwidth, screenheight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(key);
        this.setFocusable(true);
    }

    public void setupGame(){
        assetSet.setObject();
        assetSet.setNpc();
        assetSet.setMonster();
        gameState = titlestate;
    }

    public void startgameThread(){
        gameThread = new Thread(this);
        gameThread.start();
        System.out.println("Thread started");
    }

    @Override
    public void run() {
        double drawinterval = 1000000000/FPS;
        double nextdrawtime = System.nanoTime() + drawinterval;
        while(gameThread != null){
            
            update();

            repaint();

            try {
                double remainingtime = nextdrawtime - System.nanoTime();
                remainingtime /= 1000000;

                if(remainingtime < 0){
                    remainingtime = 0;
                }

                Thread.sleep((long)remainingtime);

                nextdrawtime += drawinterval;
            } 
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(){
        if(gameState == playState){
            //PLAYER
            p.update();

            //NPC
            for(int i=0;i<npc.length;i++){
                if(npc[i]!=null){
                    npc[i].update();
                }
            }

            //Monsters
            for(int i=0;i<mon.length;i++){
                if(mon[i]!=null){
                    mon[i].update();
                }
            }
        }
        if(gameState == pauseState){
            
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
    
        //TITLE SCREEN
        if(gameState == titlestate){
            ui.draw(g2);
        }
        else{
            //TILE
            tileM.draw(g2);

            //Draw entities
            p.draw(g2);
            for(int i=0;i<npc.length;i++){
                if(npc[i]!=null){
                    npc[i].draw(g2);
                }
            }
            for(int i=0;i<obj.length;i++){
                if(obj[i]!=null){
                    obj[i].draw(g2);
                }
            }
            for(int i=0;i<mon.length;i++){
                if(mon[i]!=null){
                    mon[i].draw(g2);
                }
            }

            //UI
            ui.draw(g2);
        }
    }

    public void playMusic(int i){
        music.setFile(i);
        music.Play();
        music.loop();
    }

    public void stopMusic(){
        music.stop();
    }

    public void playSE(int i){
        se.setFile(i);
        se.Play();
    }
}
