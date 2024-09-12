package object;

import entity.characters;
import main.GamePanel;

public class Obj_boots extends characters{
    public Obj_boots(GamePanel gp){
        super(gp);
        name = "boots";
        down1 = setup("res/objects/boots", gp.tilesize, gp.tilesize);
    }
}
