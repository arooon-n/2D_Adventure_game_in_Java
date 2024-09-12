package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import entity.characters;
import object.Obj_hearts;

public class UI {
    public Font Magni, Capri;
    GamePanel gp; // Reference to the GamePanel object
    Graphics2D g2;
    public boolean messageOn = false;
    public String message = "";
    public int messageCounter = 0;
    public boolean Gamefinished = false;
    public String currentDialogue = "";
    public int commandNumber = 0;
    BufferedImage heart_full, heart_half, heart_empty;

    public UI(GamePanel gp){
        this.gp = gp; // Initialize the reference to the GamePanel object

        // Initialize the font object
        InputStream is = getClass().getResourceAsStream("res/fonts/magni.ttf");
        try {
            Magni = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        is = getClass().getResourceAsStream("res/fonts/Capricornus-gxz71.ttf");
        try {
            Capri = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //Create HUD object
        characters heart = new Obj_hearts(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_empty = heart.image3;
    }

    public void showMessage(String text){
        message = text;//set the message
        messageOn = true;//make it true
    }

    public void draw(Graphics2D g2){
        this.g2 = g2;
        g2.setFont(Capri);


        //TITLE STATE
        if(gp.gameState == gp.titlestate){
            drawTitleScreen();
        }
        //CONTROLS STATE
        if(gp.gameState == gp.ControlsState){
            drawCotrolScreen();
        }
        //PLAY STATE
        if(gp.gameState == gp.playState){
            drawPlayerLife();
        }
        //PAUSE STATE
        if(gp.gameState == gp.pauseState){
            drawPauseScreen();
            drawPlayerLife();
        }
        //DIALOGUE STATE
        if(gp.gameState == gp.dialogueState){
            drawPlayerLife();
            drawDialogueScreen();
        }
        //GAMEOVER STATE
        if(gp.gameState == gp.GameoverState){
            drawGameoverScreen();
        }
        //ENDING
        if(gp.gameState == gp.YouwonState){
            drawVictoryScreen();
        }
    }

    public void drawPauseScreen(){
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80f));
        g2.setColor(Color.white);
        String text = "PAUSED";
        int x, y;
        x = getXforCenter(text);
        y = gp.screenheight/2;
        g2.drawString(text, x, y);
    }

    public void drawDialogueScreen(){
        //Window
        int x = gp.tilesize*2;
        int y = gp.tilesize/2;
        int height = gp.tilesize*4;
        int width = gp.screenwidth - (gp.tilesize*4);
        drawSubwindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30f));
        x += gp.tilesize/2;
        y += gp.tilesize;

        for(String line : currentDialogue.split("\n")){
            g2.drawString(line, x, y);
            y+=40;
        }
    }

    public void drawSubwindow(int x, int y, int width, int height){
        Color c = new Color(0,0,0,200);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 30, 30);
        c = new Color(255,255,255);
        g2.setColor(c);
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 20,20);
    }

    public void drawTitleScreen(){
        //Background
        g2.setColor(new Color(0));
        g2.fillRect(0, 0, gp.screenwidth, gp.screenheight);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 70f));
        String text = "THE LOST TREASURE";
        int x = getXforCenter(text);
        int y = gp.tilesize*3;
        //Shadow effect
        g2.setColor(Color.GRAY);
        g2.drawString(text, x+3, y+3);
        //Title
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        //New game
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 38f));
        text = "Play Game";
        x = getXforCenter(text);
        y = gp.tilesize*8;
        g2.drawString(text, x, y);
        if(commandNumber == 0){
            g2.drawString("->",x-(gp.tilesize)*2,y);
        }

        //Quit
        text = "QUIT";
        x = getXforCenter(text);
        y = gp.tilesize*10;
        g2.drawString(text, x, y);
        if(commandNumber == 1){
            g2.drawString("->",x-(gp.tilesize)*2,y);
        }
    }

    public void drawCotrolScreen(){
        //Background
        g2.setColor(new Color(0));
        g2.fillRect(0, 0, gp.screenwidth, gp.screenheight);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 50f));
        String text = "CONTROLS";
        int x = getXforCenter(text);
        int y = gp.tilesize*1;
        //Shadow effect
        g2.setColor(Color.GRAY);
        g2.drawString(text, x+3, y+3);
        //Title
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        //W
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28f));
        text = "W/UP ---- Move Up";
        x = getXforCenter(text);
        y = gp.tilesize*4;
        g2.drawString(text, x, y);

        //S
        text = "S/DOWN ---- Move Down";
        x = getXforCenter(text);
        y = gp.tilesize*5;
        g2.drawString(text, x, y);

        //A
        text = "A/LEFT ---- Move Left";
        x = getXforCenter(text);
        y = gp.tilesize*6;
        g2.drawString(text, x, y);

        //D
        text = "D/RIGHT ---- Move Right";
        x = getXforCenter(text);
        y = gp.tilesize*7;
        g2.drawString(text, x, y);

        //ENTER 
        text = "ENTER ---- Interact/Attack";
        x = getXforCenter(text);
        y = gp.tilesize*8;
        g2.drawString(text, x, y);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30f));
        text = "[Press space to continue]";
        x = getXforCenter(text) + gp.tilesize*4;
        y = gp.tilesize*11;
        g2.drawString(text, x, y);
    }

    public void drawGameoverScreen(){
        //Background
        g2.setColor(new Color(0));
        g2.fillRect(0, 0, gp.screenwidth, gp.screenheight);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 70f));
        String text = "Game over";
        int x = getXforCenter(text);
        int y = gp.tilesize*6;
        //Shadow effect
        g2.setColor(Color.GRAY);
        g2.drawString(text, x+3, y+3);
        //Game over text
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30f));
        text = "Press Space To Retry";
        x = getXforCenter(text);
        y = gp.tilesize*10;
        g2.drawString(text, x, y);
    }

    public void drawVictoryScreen(){

        g2.setColor(new Color(135,180,235));
        g2.fillRect(0, 0, gp.screenwidth, gp.screenheight);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 70f));
        String text = "YOU WIN!!";
        int x = getXforCenter(text);
        int y = gp.tilesize*6;
        //Shadow effect
        g2.setColor(new Color(150,75,0));
        g2.drawString(text, x+3, y+3);
        //you won text
        g2.setColor(new Color(25,122,45));
        g2.drawString(text, x, y);
        
        //options
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 38f));
        text = "Play Again";
        x = getXforCenter(text);
        y = gp.tilesize*9;
        g2.drawString(text, x, y);
        if(commandNumber == 0){
            g2.drawString("->",x-(gp.tilesize)*2,y);
        }

        text = "Exit";
        x = getXforCenter(text);
        y = gp.tilesize*10;
        g2.drawString(text, x, y);
        if(commandNumber == 1){
            g2.drawString("->",x-(gp.tilesize)*2,y);
        }
    }

    public void drawPlayerLife(){
        int x = gp.tilesize/2;
        int y = gp.tilesize/2;
        int i = 0;

        //Draw blank heart
        while(i<gp.p.maxLife/2){
            g2.drawImage(heart_empty, x, y,null);
            i++;
            x += gp.tilesize;
        }

        //reset the values
        x = gp.tilesize/2;
        y = gp.tilesize/2;
        i = 0;

        //Draw current life
        while(i<gp.p.Life){
            g2.drawImage(heart_half, x, y, null);
            i++;
            if(i<gp.p.Life){
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += gp.tilesize;
        }
    }

    public int getXforCenter(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return (gp.screenwidth/2) - (length/2);
    }
        
}
