package object;

import entity.characters;
import main.GamePanel;

public class Obj_door extends characters{
    GamePanel gp;

    public Obj_door(GamePanel gp){
        super(gp);
        name = "door";
        down1 = setup("res/objects/door", gp.tilesize, gp.tilesize);
        collision = true;
    }
}
