package main;

import entity.Oldman;
import monsters.Mon_slime;
import object.*;

public class Assetsetter {
    GamePanel gp;

    public Assetsetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){
        gp.obj[0] = new Obj_chest(gp);
        gp.obj[0].worldX = 9*gp.tilesize;
        gp.obj[0].worldY = 7*gp.tilesize;

        gp.obj[1] = new Obj_door(gp);
        gp.obj[1].worldX = 23*gp.tilesize;
        gp.obj[1].worldY = 10*gp.tilesize;

        gp.obj[2] = new Obj_door(gp);
        gp.obj[2].worldX = 8*gp.tilesize;
        gp.obj[2].worldY = 28*gp.tilesize;

        gp.obj[3] = new Obj_door(gp);
        gp.obj[3].worldX = 10*gp.tilesize;
        gp.obj[3].worldY = 11*gp.tilesize;

        gp.obj[4] = new Obj_key(gp);
        gp.obj[4].worldX = 24*gp.tilesize;
        gp.obj[4].worldY = 27*gp.tilesize;
    }

    public void setNpc(){
        //npc 1
        gp.npc[0] = new Oldman(gp);
        gp.npc[0].worldX = gp.tilesize*21;
        gp.npc[0].worldY = gp.tilesize*21;

    }

    public void setMonster(){
        gp.mon[0] = new Mon_slime(gp);
        gp.mon[0].worldX = gp.tilesize*37;
        gp.mon[0].worldY = gp.tilesize*32;
        gp.mon[0].drops = "boots";

        gp.mon[1] = new Mon_slime(gp);
        gp.mon[1].worldX = gp.tilesize*20;
        gp.mon[1].worldY = gp.tilesize*38;
        gp.mon[1].drops = "key";

        gp.mon[2] = new Mon_slime(gp);
        gp.mon[2].worldX = gp.tilesize*8;
        gp.mon[2].worldY = gp.tilesize*16;
        gp.mon[2].drops = "key";

        gp.mon[3] = new Mon_slime(gp);
        gp.mon[3].worldX = gp.tilesize*37;
        gp.mon[3].worldY = gp.tilesize*66;
        gp.mon[3].drops = "heart";
    }
}
