package entity;
import java.util.Random;

import main.GamePanel;

public class Oldman extends characters {

    public Oldman(GamePanel gp) {
        super(gp);
        direction = "down";
        speed = 1;
        getImage();
        SetDialogue();

        solidArea.x = 4;
        solidArea.y = 16;
        solidArea.width = 40;
        solidArea.height = 32;
        solidAreadefX = solidArea.x;
        solidAreadefY = solidArea.y;

        type = NpcType;
    }

    public void getImage(){
        up1 = setup("res/npc/oldman_up_1", gp.tilesize, gp.tilesize);
        up2 = setup("res/npc/oldman_up_2", gp.tilesize, gp.tilesize);
        down1 = setup("res/npc/oldman_down_1", gp.tilesize, gp.tilesize);
        down2 = setup("res/npc/oldman_down_2", gp.tilesize, gp.tilesize);
        left1 = setup("res/npc/oldman_left_1", gp.tilesize, gp.tilesize);
        left2 = setup("res/npc/oldman_left_2", gp.tilesize, gp.tilesize);
        right1 = setup("res/npc/oldman_right_1", gp.tilesize, gp.tilesize);
        right2 = setup("res/npc/oldman_right_2", gp.tilesize, gp.tilesize);
    }

    public void setAction(){
        actionLockCounter++;

        if(actionLockCounter == 120){
            actionLockCounter = 0;
            Random random = new Random();
            int i =  random.nextInt(100) + 1;
            if(i<=25){
                direction = "up";
            }
            if(i>=75){
                direction = "down";
            }
            if(i>25 && i<50){
                direction = "left";
            }
            if(i>=50 && i<75){
                direction = "right";
            }
        }
    }

    public void Speak(){
        super.Speak();
    }

    public void SetDialogue(){
        Dialogues[0] = "Oldman: Hello young adventurer!!!";
        Dialogues[1] = "Oldman: I see, you are here seeking for \ngreat treasures...";
        Dialogues[2] =  "Oldman: You must pass through great \ndanger to get it...";
        Dialogues[3] = "Oldman: I too was once was young \nand adventurous...";
        Dialogues[4] = "Oldman: But now i am to old for any kind \nof adventure... Anyways good luck on \nyour journey ahead!";
    }
}
