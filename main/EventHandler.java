package main;

public class EventHandler{
    GamePanel gp;
    EventRect eventRect[][];

    int previousEventX, previousEventY;
    boolean canTriggerEvent = true;

    public EventHandler(GamePanel gp){
        this.gp = gp;
        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];

        int col = 0;
        int row = 0;
        while(col<gp.maxWorldCol && row<gp.maxWorldRow){
            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;
            eventRect[col][row].eventRectdefX = eventRect[col][row].x;
            eventRect[col][row].eventRectdefY = eventRect[col][row].y;   

            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row++;
            }
        }
    }

    public void checkEvent(){

        int XDis = Math.abs(gp.p.worldX - previousEventX);
        int YDis = Math.abs(gp.p.worldY - previousEventY);
        int Dis = Math.max(XDis, YDis);
        if(Dis > gp.tilesize/2){
            canTriggerEvent = true;
        }

        if(canTriggerEvent == true){
            if (hit(25,15,"right") == true){
                damagePit(25, 15, gp.dialogueState);
            }
            if(hit(22,7,"any")){
                healingPool(gp.dialogueState);
            }
            if(hit(23,7,"any")){
                healingPool(gp.dialogueState);
            }
            if(hit(24,7,"any")){
                healingPool(gp.dialogueState);
            }
            if(hit(26,7,"any")){
                Teleport(gp.dialogueState,34,7);
            }
            if(hit(33,7,"any")){
                Teleport(gp.dialogueState,25,7);
            }
            if(hit(26,30,"any")){
                Teleport(gp.dialogueState,8,51);
            }
            if(hit(8,50,"any")){
                Teleport(gp.dialogueState,26,29);
            }
            if(hit(39,69,"any")){
                Teleport(gp.dialogueState,14,27);
            }
            if(hit(14,26,"any")){
                Teleport(gp.dialogueState,39,68);
            }
        }
    }

    public boolean hit (int eventCol, int eventRow, String reqDirection){
        boolean hit = false;

        //Get the location of player and event
        gp.p.solidArea.x += gp.p.worldX;
        gp.p.solidArea.y += gp.p.worldY;
        eventRect[eventCol][eventRow].x = eventCol*gp.tilesize + eventRect[eventCol][eventRow].x;
        eventRect[eventCol][eventRow].y = eventRow*gp.tilesize + eventRect[eventCol][eventRow].y;

        if(gp.p.solidArea.intersects(eventRect[eventCol][eventRow]) && eventRect[eventCol][eventRow].EventDone == false){
            if(gp.p.direction == reqDirection || reqDirection == "any"){
                hit = true;

                previousEventX = gp.p.worldX;
                previousEventY = gp.p.worldY;
            }
        }
        //Reset the valuesw
        gp.p.solidArea.x = gp.p.solidAreadefX;
        gp.p.solidArea.y = gp.p.solidAreadefY;
        eventRect[eventCol][eventRow].x = eventRect[eventCol][eventRow].eventRectdefX;
        eventRect[eventCol][eventRow].y = eventRect[eventCol][eventRow].eventRectdefY;
        return hit;
    }

    public void damagePit (int col, int row, int gameState){
        gp.gameState = gameState;
        gp.ui.currentDialogue = "You fill into a pit.\nLife -1";
        gp.p.Life -= 1;

        if(gp.p.Life<=0){
            gp.stopMusic();
            gp.playSE(6);
            gp.gameState = gp.GameoverState;
        }

        //eventRect[col][row].EventDone = true;
        canTriggerEvent = false;
    }

    public void healingPool(int gameState){
        if(gp.key.enterPressed == true){
            gp.gameState = gameState;
            if(gp.p.Life<gp.p.maxLife){
                gp.ui.currentDialogue = "The water has healing properties. \nHealth regained.";
                gp.p.Life = gp.p.maxLife;
            }
            else{
                gp.ui.currentDialogue = "You already have max health.";
            }
        }
    }

    public void Teleport(int gameState, int x, int y){
        gp.p.worldX = gp.tilesize*x;
        gp.p.worldY = gp.tilesize*y;

        gp.gameState = gameState;
        gp.playSE(7);
        gp.ui.currentDialogue = "You got Teleported!!";
    }
}