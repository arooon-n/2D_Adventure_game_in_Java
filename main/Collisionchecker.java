package main;
import entity.characters;
public class Collisionchecker {

    GamePanel gp;
    public Collisionchecker(GamePanel gp){
        this.gp = gp;
    }

    /**
     * @param entity
     */
    public void Checktile(characters entity){
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX +entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY= entity.worldY + entity.solidArea.y + entity.solidArea.height;
        
        int entityLeftcol = entityLeftWorldX/gp.tilesize;
        int entityRightcol = entityRightWorldX/gp.tilesize;
        int entityTopRow = entityTopWorldY/gp.tilesize;
        int entityBottomRow = entityBottomWorldY/gp.tilesize;

        int tileNum1, tileNum2;

        switch(entity.direction){
        case "up":
            entityTopRow = (entityTopWorldY - entity.speed)/gp.tilesize;
            tileNum1 = gp.tileM.mapTileNum[entityLeftcol][entityTopRow];
            tileNum2 = gp.tileM.mapTileNum[entityRightcol][entityTopRow];
            if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                entity.collisionOn = true;
            }
            break;
        case "down":


        
            entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tilesize;
            tileNum1 = gp.tileM.mapTileNum[entityLeftcol][entityBottomRow];
            tileNum2 = gp.tileM.mapTileNum[entityRightcol][entityBottomRow];
            if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                entity.collisionOn = true;
            }
            break;
        case "left":
            entityLeftcol = (entityLeftWorldX - entity.speed)/gp.tilesize;
            tileNum1 = gp.tileM.mapTileNum[entityLeftcol][entityTopRow];
            tileNum2 = gp.tileM.mapTileNum[entityLeftcol][entityBottomRow];
            if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                entity.collisionOn = true;
            }
            break;

        case "right":
            entityRightcol = (entityRightWorldX + entity.speed)/gp.tilesize;
            tileNum1 = gp.tileM.mapTileNum[entityRightcol][entityTopRow];
            tileNum2 = gp.tileM.mapTileNum[entityRightcol][entityBottomRow];
            if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                entity.collisionOn = true;
            }
            break;
        }
    }

    public int CheckObj(characters entity, boolean player){
        int index = 999;

        for(int i = 0; i<gp.obj.length; i++){
            if(gp.obj[i] != null){
                entity.solidArea.x += entity.worldX;
                entity.solidArea.y += entity.worldY;

                gp.obj[i].solidArea.x += gp.obj[i].worldX;
                gp.obj[i].solidArea.y += gp.obj[i].worldY;

                switch(entity.direction){
                case "up":
                    entity.solidArea.y -= entity.speed;
                    break;
                case "down":
                    entity.solidArea.y += entity.speed;
                    break;
                case "left":
                    entity.solidArea.x -= entity.speed;
                    break;
                case "right":
                    entity.solidArea.x += entity.speed;
                    break;
                }
                if(entity.solidArea.intersects(gp.obj[i].solidArea)){
                    if(gp.obj[i].collision == true){
                        entity.collisionOn = true;
                    }
                    if(player == true){
                        index = i;
                    }
                }

                entity.solidArea.x = entity.solidAreadefX;
                entity.solidArea.y = entity.solidAreadefY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreadefX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreadefY;
            }
        }
        return index;
    }

    public int checkEntity(characters entity, characters[] target ){
        int index = 999;

        for(int i = 0; i<gp.obj.length; i++){
            if(target[i] != null){
                entity.solidArea.x += entity.worldX;
                entity.solidArea.y += entity.worldY;

                target[i].solidArea.x += target[i].worldX;
                target[i].solidArea.y += target[i].worldY;

                switch(entity.direction){
                case "up":
                    entity.solidArea.y -= entity.speed;
                    break;
                case "down":
                    entity.solidArea.y += entity.speed;
                    break;
                case "left":
                    entity.solidArea.x -= entity.speed;
                    break;
                case "right":
                    entity.solidArea.x += entity.speed;
                    break;
                }
                if(entity.solidArea.intersects(target[i].solidArea)){
                    if(target[i] != entity){
                        entity.collisionOn = true;
                        index = i;
                    }
                }
                
                entity.solidArea.x = entity.solidAreadefX;
                entity.solidArea.y = entity.solidAreadefY;
                target[i].solidArea.x = target[i].solidAreadefX;
                target[i].solidArea.y = target[i].solidAreadefY;
            }
        }
        return index;
    }

    public boolean checkPlayer(characters entity){

        boolean contactPlayer = false;
        entity.solidArea.x += entity.worldX;
        entity.solidArea.y += entity.worldY;

        gp.p.solidArea.x += gp.p.worldX;
        gp.p.solidArea.y += gp.p.worldY;

        switch(entity.direction){
        case "up":
            entity.solidArea.y -= entity.speed;
            break;
        case "down":
            entity.solidArea.y += entity.speed;
            break;
        case "left":
            entity.solidArea.x -= entity.speed;
            break;
        case "right":
            entity.solidArea.x += entity.speed;
            break;
        }
        if(entity.solidArea.intersects(gp.p.solidArea)){
            entity.collisionOn = true;
            contactPlayer = true;
        }

        entity.solidArea.x = entity.solidAreadefX;
        entity.solidArea.y = entity.solidAreadefY;
        gp.p.solidArea.x = gp.p.solidAreadefX;
        gp.p.solidArea.y = gp.p.solidAreadefY;  
        
        return contactPlayer;
    }
}
