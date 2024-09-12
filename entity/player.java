package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.KeyHandler;
import object.Obj_boots;
import object.Obj_hearts;
import object.Obj_key;
import main.GamePanel;

public class player extends characters {
    private KeyHandler key; // Key handler for player input

    public final int screenX; // X coordinate of player screen position
    public final int screenY; // Y coordinate of player screen position
    public int Keycount; // Number of keys collected by the player

    public player(GamePanel gp, KeyHandler key1){
        super(gp); // Call superclass constructor
        this.gp = gp; // Initialize game panel reference
        this.key = key1; // Initialize key handler
        screenX = gp.screenwidth/2 - (gp.tilesize/2); // Calculate initial screen X position
        screenY = gp.screenheight/2 - (gp.tilesize/2); // Calculate initial screen Y position
        solidArea = new Rectangle(12, 20, 28, 26); // Define player's collision solid area
        solidAreadefX = solidArea.x; // Store default X coordinate of solid area
        solidAreadefY = solidArea.y; // Store default Y coordinate of solid area

        AttackArea.width = 36; // Set width of attack area
        AttackArea.height = 36; // Set height of attack area
        setDefaultValues(); // Initialize default player values
        getplayerImage(); // Load player's sprite images
        getplayerAttackImage(); // Load player's attack sprite images
        type = playerType; // Set entity type to player
    }

    public void setDefaultValues(){
        worldX = gp.tilesize*22; // Set initial world X position
        worldY = gp.tilesize*8; // Set initial world Y position
        speed = 4; // Set player movement speed
        direction = "down"; // Set initial player direction
        
        // Player status initialization
        maxLife = 6; // Set maximum player life
        Life = maxLife; // Set current player life to maximum
        Keycount = 0; // Set initial key count
        Invincible = false; // Set player invincibility state
    }

    public void getplayerImage(){
        // Load player's sprite images for different directions
        up1 = setup("res/Player/boy_up_1", gp.tilesize, gp.tilesize);
        up2 = setup("res/Player/boy_up_2", gp.tilesize, gp.tilesize);
        down1 = setup("res/Player/boy_down_1", gp.tilesize, gp.tilesize);
        down2 = setup("res/Player/boy_down_2", gp.tilesize, gp.tilesize);
        left1 = setup("res/Player/boy_left_1", gp.tilesize, gp.tilesize);
        left2 = setup("res/Player/boy_left_2", gp.tilesize, gp.tilesize);
        right1 = setup("res/Player/boy_right_1", gp.tilesize, gp.tilesize);
        right2 = setup("res/Player/boy_right_2", gp.tilesize, gp.tilesize);
    }

    public void getplayerAttackImage(){
        // Load player's attack sprite images for different directions
        attackup1 = setup("res/Player/boy_attack_up_1", gp.tilesize*2, gp.tilesize);
        attackup2 = setup("res/Player/boy_attack_up_2", gp.tilesize*2, gp.tilesize);
        attackdown1 = setup("res/Player/boy_attack_down_1", gp.tilesize*2, gp.tilesize);
        attackdown2 = setup("res/Player/boy_attack_down_2", gp.tilesize*2, gp.tilesize);
        attackleft1 = setup("res/Player/boy_attack_left_1", gp.tilesize, gp.tilesize*2);
        attackleft2 = setup("res/Player/boy_attack_left_2", gp.tilesize, gp.tilesize*2);
        attackright1 = setup("res/Player/boy_attack_right_1", gp.tilesize, gp.tilesize*2);
        attackright2 = setup("res/Player/boy_attack_right_2", gp.tilesize, gp.tilesize*2);
    }


    public void update(){
        if(Attacking == true){
            attacking(); // Perform player attack logic
        }
        else if(key.upPressed == true || key.downPressed == true || key.leftPressed == true || key.rightPressed == true|| key.enterPressed == true){
            // Check if the player is moving or interacting
            if(key.upPressed == true){
                direction = "up"; // Set player direction to up
            }
            if(key.downPressed == true){
                direction = "down"; // Set player direction to down
            }
            if(key.leftPressed == true){
                direction = "left"; // Set player direction to left
            }
            if(key.rightPressed == true){
                direction = "right"; // Set player direction to right
            }
            
            // Disable collision detection temporarily
            collisionOn = false;
            
            // Check collision with tiles
            gp.Cchecker.Checktile(this);
            
            // Check collision with objects and handle interactions
            int Objindex = gp.Cchecker.CheckObj(this, true);
            PickObj(Objindex);
            
            // Check collision with NPCs and handle interactions
            int npcIndex = gp.Cchecker.checkEntity(this, gp.npc);
            InteractNpc(npcIndex);
            
            // Check collision with monsters and handle interactions
            int MonIndex = gp.Cchecker.checkEntity(this, gp.mon);
            InteractMonster(MonIndex);
            
            // Check for game events triggered by player actions
            gp.eHandler.checkEvent();
            
            // Reset enter key press
            gp.key.enterPressed = false;

            // Move the player if no collision occurs and no enter key press detected
            if(collisionOn == false && key.enterPressed == false){
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }
            
            // Reset enter key press
            key.enterPressed = false;
            
            // Increment sprite counter for animation
            spritecounter++;
            
            // Switch sprite number for animation
            if(spritecounter > 10){
                if(spritenumber == 1){
                    spritenumber = 2;
                }
                else if(spritenumber == 2){
                    spritenumber = 1;
                }
                spritecounter = 0;
            }
        }
        
        // Handle player invincibility duration
        if(Invincible == true){
            Invinciblecounter++;
            if(Invinciblecounter > 60){
                    Invincible = false;
                    Invinciblecounter = 0;
            }
        }
    }
    // Method to handle player attacking action
    public void attacking(){
        // Increment sprite counter for animation
        spritecounter++;

        // Switch sprite number for animation
        if(spritecounter <=5){
            spritenumber = 1;
        }
        if(spritecounter > 5 && spritecounter <= 25){
            spritenumber = 2;

            // Save current positions and sizes for attack animation
            int currentworldX = worldX;
            int currentworldY = worldY;
            int SolidareaWidth = solidArea.width;
            int SolidareaHeight = solidArea.height;

            // Adjust player position for the attack based on direction
            switch (direction) {
                case "up":
                    worldY -= AttackArea.height;
                    break;
                case "down":
                    worldY += AttackArea.height;
                    break;
                case "left":
                    worldX -= AttackArea.width;
                    break;
                case "right":
                    worldX += AttackArea.width;
                    break;
            }

            // Set solid area size to match attack area
            solidArea.width = AttackArea.width;
            solidArea.height = AttackArea.height;

            // Check for collision with monsters and apply damage
            int monIdx = gp.Cchecker.checkEntity(this, gp.mon);
            DamageMonster(monIdx);

            // Reset positions and sizes after attack animation
            worldX = currentworldX;
            worldY = currentworldY;
            solidArea.width = SolidareaWidth;
            solidArea.height = SolidareaHeight;
        }

        // Reset sprite number and counter after attack animation
        if(spritecounter > 25){
            spritenumber = 1;
            spritecounter = 0;
            Attacking = false;
        }
    }

    // Method to handle picking up objects and interacting with them
    public void PickObj(int i){
        // Check if the object index is valid
        if(i != 999){
            // Get the name of the object
            String Objname = gp.obj[i].name;
            // Perform different actions based on the type of object
            if(Objname.equals("key")){
                Keycount++; // Increment key count
                gp.playSE(1); // Play sound effect
                gp.obj[i] = null; // Remove object from the game
                System.out.println("KEY: "+Keycount); // Print key count for debugging
            }
            else if(Objname.equals("door")){
                // Check if player has keys to open door
                if(Keycount > 0){
                    gp.playSE(3); // Play sound effect
                    gp.obj[i] = null; // Remove object from the game
                    Keycount--; // Decrement key count
                }
            }
            else if(Objname.equals("boots")){
                gp.playSE(2); // Play sound effect
                gp.obj[i] = null; // Remove object from the game
                speed += 1; // Increase player speed
            }
            else if(Objname.equals("heart")){
                // Check if player's life is not full
                if(Life < maxLife){
                    gp.playSE(2); // Play sound effect
                    gp.obj[i] = null; // Remove object from the game
                    // Increase player's life by 2 (or set to max if exceeds maximum life)
                    Life = Math.min(Life + 2, maxLife);
                }
            }
            else if(Objname.equals("chest")){
                gp.stopMusic(); // Stop background music
                gp.playSE(5); // Play sound effect
                gp.gameState = gp.YouwonState; // Set game state to "You Won" state
            }
        }
    }

    // Method to handle player interaction with NPCs
    public void InteractNpc(int index){
        // Check if the NPC index is valid and if the enter key is pressed
        if(index != 999 && gp.key.enterPressed == true){
            gp.gameState = gp.dialogueState; // Set game state to dialogue state
            gp.npc[index].Speak(); // Call NPC's speak method
        }
        else{
            // If enter key is pressed and no NPC is nearby, initiate player attack
            if(gp.key.enterPressed == true){
                Attacking = true; // Set attacking flag to true
            }
        }
    }

    // Method to handle player interaction with monsters
    public void InteractMonster(int index){
        // Check if the monster index is valid and player is not invincible
        if(index != 999 && Invincible == false){
            Life--; // Decrease player's life
            Invincible = true; // Set player to invincible state
            // If player's life reaches zero or below, initiate game over state
            if(Life <= 0){
                gp.stopMusic(); // Stop background music
                gp.playSE(6); // Play game over sound effect
                gp.gameState = gp.GameoverState; // Set game state to game over state
            }
        }
    }

    // Method to handle damage dealt to monsters by the player
    public void DamageMonster(int idx){
        // Check if the monster index is valid
        if(idx != 999){
            // Check if the monster is not already invincible
            if(!gp.mon[idx].Invincible){
                // Decrease monster's life by 1
                gp.mon[idx].Life--;
                // Set monster to invincible state
                gp.mon[idx].Invincible = true;
                // Check if monster's life is zero or below after damage
                if(gp.mon[idx].Life <= 0){
                    // Check if the monster has a drop
                    if(gp.mon[idx].drops != null){
                        // Spawn an object based on the monster's drop type
                        switch(gp.mon[idx].drops){
                            case "key":
                                // Loop through object array to find a vacant slot
                                for(int i = 0; i < gp.obj.length; i++){
                                    // If a vacant slot is found, create a key object and assign its position to the monster's position
                                    if(gp.obj[i] == null){
                                        gp.obj[i] = new Obj_key(gp);
                                        gp.obj[i].worldX = gp.mon[idx].worldX;
                                        gp.obj[i].worldY = gp.mon[idx].worldY;
                                        break;
                                    }
                                }
                                break;
                            case "boots":
                                // Loop through object array to find a vacant slot
                                for(int i = 0; i < gp.obj.length; i++){
                                    // If a vacant slot is found, create a boots object and assign its position to the monster's position
                                    if(gp.obj[i] == null){
                                        gp.obj[i] = new Obj_boots(gp);
                                        gp.obj[i].worldX = gp.mon[idx].worldX;
                                        gp.obj[i].worldY = gp.mon[idx].worldY;
                                        break;
                                    }
                                }
                                break;
                            case "heart":
                                // Loop through object array to find a vacant slot
                                for(int i = 0; i < gp.obj.length; i++){
                                    // If a vacant slot is found, create a heart object and assign its position to the monster's position
                                    if(gp.obj[i] == null){
                                        gp.obj[i] = new Obj_hearts(gp);
                                        gp.obj[i].worldX = gp.mon[idx].worldX;
                                        gp.obj[i].worldY = gp.mon[idx].worldY;
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                    // Set monster to null after it is defeated
                    gp.mon[idx] = null;
                }
            }
        }
    }

    // Method to draw the player on the game screen
    public void draw(Graphics2D g){
        // Temporary screen positions for drawing
        int TempscreenX = screenX;
        int TempscreenY = screenY;

        // Temporary buffered image for player sprite
        BufferedImage image = null;

        // Switch case to determine which sprite to draw based on player direction and action
        switch(direction){
            case "up":
                if(!Attacking){
                    image = spritenumber == 1 ? up1 : up2;
                }
                if(Attacking){
                    TempscreenY = screenY - gp.tilesize;
                    image = spritenumber == 1 ? attackup1 : attackup2;
                }
                break;
            case "down":
                if(!Attacking){
                    image = spritenumber == 1 ? down1 : down2;
                }
                if(Attacking){
                    image = spritenumber == 1 ? attackdown1 : attackdown2;
                }
                break;
            case "left":
                if(!Attacking){
                    image = spritenumber == 1 ? left1 : left2;
                }
                if(Attacking){
                    TempscreenX = screenX - gp.tilesize;
                    image = spritenumber == 1 ? attackleft1 : attackleft2;
                }
                break;
            case "right":
                if(!Attacking){
                    image = spritenumber == 1 ? right1 : right2;
                }
                if(Attacking){
                    image = spritenumber == 1 ? attackright1 : attackright2;
                }
                break;
        }

        // Apply transparency if player is invincible
        if(Invincible){
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        }

        // Draw player image on the screen
        g.drawImage(image, TempscreenX, TempscreenY, null);

        // Reset transparency
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}
