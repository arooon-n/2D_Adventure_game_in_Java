package object;

import entity.characters;
import main.GamePanel;

public class Obj_hearts extends characters {
    public Obj_hearts(GamePanel gp){
        super(gp);
        name = "heart";

        image = setup("res/objects/heart_full", gp.tilesize, gp.tilesize);
        image2 = setup("res/objects/heart_half", gp.tilesize, gp.tilesize);
        image3 = setup("res/objects/heart_blank", gp.tilesize, gp.tilesize);

        down1 = image; 
    }
}
