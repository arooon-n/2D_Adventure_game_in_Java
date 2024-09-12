package object;

import entity.characters;
import main.GamePanel;

public class Obj_key extends characters {
    public Obj_key(GamePanel gp){
        super(gp);
        name = "key";
        down1 = setup("res/objects/key", gp.tilesize, gp.tilesize);
    }
}
