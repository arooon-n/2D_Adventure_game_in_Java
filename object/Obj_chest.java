package object;

import entity.characters;
import main.GamePanel;

public class Obj_chest extends characters{
    public Obj_chest(GamePanel gp){
        super(gp);
        name = "chest";
        down1 = setup("res/objects/chest", gp.tilesize, gp.tilesize);
        collision = true;
    }
}