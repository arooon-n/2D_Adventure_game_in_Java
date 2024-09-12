package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.Utilitytool;

public class characters {
    public GamePanel gp;
    public int x, y;
    public int speed;
    public int worldX, worldY;

    public BufferedImage up1, up2, down1, down2, right1, right2, left1, left2;
    public BufferedImage attackup1, attackup2, attackdown1, attackdown2, attackright1, attackright2, attackleft1, attackleft2;
    public String direction = "down";
    public int spritenumber = 1;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle AttackArea = new Rectangle(0 ,0, 0, 0);
    public int solidAreadefX, solidAreadefY;
    public boolean collisionOn = false;
    public boolean Invincible = false;
    public String Dialogues[] = new String[20];
    public int dialougeIndex = 0;

    //counters
    public int actionLockCounter = 0;
    int Invinciblecounter = 0;
    public int spritecounter = 0;

    //OBJECTS
    public BufferedImage image , image2, image3;
    public String name;
    public boolean collision = false;
    public int type; // 0 = player, 1 = npc, 2 = monster
    public final int playerType = 0;
    public final int NpcType = 1;
    public final int MonsterType = 2;
    

    //Charecter status
    public int maxLife;
    public int Life;
    public boolean Attacking  = false;
    public String drops = null;

    public characters(GamePanel gp){
        this.gp = gp;
    }

    public BufferedImage setup(String imgpath, int height, int width){
        Utilitytool utool = new Utilitytool();  
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(imgpath+".png"));
            image = utool.scaleImage(image, height, width);
        } catch (IOException e) {
            // TODO: handle exception
        }
        return image;
    }

    public void setAction(){

    }

    public void Speak(){
        if(Dialogues[dialougeIndex] == null){
            dialougeIndex = 0;
        }
        gp.ui.currentDialogue = Dialogues[dialougeIndex];
        dialougeIndex++;

        switch (gp.p.direction) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    }

    public void update(){
        setAction();
        collisionOn = false;
        //tile collision
        gp.Cchecker.Checktile(this);

        //object collision
        gp.Cchecker.CheckObj(this, false);

        //Npc and player collision
        gp.Cchecker.checkEntity(this, gp.npc);
        gp.Cchecker.checkEntity(this, gp.mon);
        boolean contactPlayer = gp.Cchecker.checkPlayer(this);

        if(this.type == MonsterType && contactPlayer == true){
            if(gp.p.Invincible == false){
                gp.p.Life-=1;
                gp.p.Invincible = true;
                if(gp.p.Life <=0){
                    gp.stopMusic();
                    gp.playSE(6);
                    gp.gameState = gp.GameoverState;
                }
            }
        }

         //if collision false npc can move
         if(collisionOn == false){
            switch (direction) {
            case "up":
                worldY -= speed;
                break;
            case "down":
                worldY += speed;
                break;
            case "left":
                worldX -= speed;
                break;
            case "right":
                worldX += speed;
                break;
            }
            spritecounter++;
            if(spritecounter > 12){
                if(spritenumber ==1){
                    spritenumber = 2;
                }
            else if(spritenumber == 2){
                spritenumber = 1;
            }
            spritecounter = 0;
            }
        }

        if(Invincible == true){
            Invinciblecounter++;
            if(Invinciblecounter > 30){
                    Invincible = false;
                    Invinciblecounter = 0;
            }
        }

    }

    public void draw(Graphics2D g2){
        BufferedImage image = null;
        int screenX = worldX - gp.p.worldX + gp.p.screenX;
        int screenY = worldY - gp.p.worldY +gp.p.screenY;

        if(worldX+gp.tilesize>(gp.p.worldX-gp.p.screenX) && worldX-gp.tilesize<(gp.p.worldX+gp.p.screenX) && worldY+gp.tilesize > (gp.p.worldY-gp.p.screenY) && worldY-gp.tilesize < (gp.p.worldY+gp.p.screenY)){
            switch(direction){
                case "up":
                    if(spritenumber == 1){image = up1;}
                    if(spritenumber == 2){image = up2;}
                    break;
                case "down":
                    if(spritenumber == 1){image = down1;}
                    if(spritenumber == 2){image = down2;}
                    break;
                case "left":
                    if(spritenumber == 1){image = left1;}
                    if(spritenumber == 2){image = left2;}
                    break;
                case "right":
                    if(spritenumber == 1){image = right1;}
                    if(spritenumber == 2){image = right2;}
                    break;
                }
                if(Invincible == true){
                    //set alpha
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
                }
            g2.drawImage(image, screenX, screenY, gp.tilesize, gp.tilesize, null);
            
            //reset opacity
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }
}
