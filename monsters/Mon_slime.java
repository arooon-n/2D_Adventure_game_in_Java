package monsters;

import java.util.Random;

import entity.characters;
import main.GamePanel;

public class Mon_slime extends characters{

    public Mon_slime(GamePanel gp) {
        super(gp);
        //Properties
        name = "slime";
        maxLife = 5;
        Life = maxLife;
        speed = 1;

        //solid area
        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreadefX = solidArea.x;
        solidAreadefY = solidArea.y;
        
        type = MonsterType;

        getImage();
    }

    public void getImage(){
        up1 = setup("res/monster/slime_down_1", gp.tilesize, gp.tilesize);
        up2 = setup("res/monster/slime_down_2", gp.tilesize, gp.tilesize);
        down1 = setup("res/monster/slime_down_1", gp.tilesize, gp.tilesize);
        down2 = setup("res/monster/slime_down_2", gp.tilesize, gp.tilesize);
        left1 = setup("res/monster/slime_down_1", gp.tilesize, gp.tilesize);
        left2 = setup("res/monster/slime_down_2", gp.tilesize, gp.tilesize);
        right1 = setup("res/monster/slime_down_1", gp.tilesize, gp.tilesize);
        right2 = setup("res/monster/slime_down_2", gp.tilesize, gp.tilesize);
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
    
}
