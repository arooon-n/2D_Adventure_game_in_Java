package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed,  rightPressed, enterPressed;
    GamePanel gp;


    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();


        //TitleState
        if(gp.gameState == gp.titlestate){
            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP || code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
                if(gp.ui.commandNumber == 0){
                    gp.ui.commandNumber = 1;
                }
                else{
                    gp.ui.commandNumber--;
                }
            }
            if(code == KeyEvent.VK_ENTER){
                switch (gp.ui.commandNumber) {
                    case 0:
                        gp.gameState = gp.ControlsState;
                        break;
                    case 1:
                        System.exit(0);
                        break;
                }
            }
        }

        //CONTROLS
        else if(gp.gameState == gp.ControlsState){
            if(code == KeyEvent.VK_SPACE){
                gp.playMusic(0);
                gp.gameState = gp.playState;
            }
        }

        //Playstate
        else if(gp.gameState == gp.playState){
            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
                upPressed = true;
            }
            if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){
                leftPressed = true;
            }
            if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
                downPressed = true;
            }
            if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){
                rightPressed = true;
            }
            if(code == KeyEvent.VK_P){
                gp.gameState = gp.pauseState;
            }
            if(code == KeyEvent.VK_ENTER){
                enterPressed = true;
            }
        }
        //Pausestate
        else if(gp.gameState == gp.pauseState){
            if(code == KeyEvent.VK_P){
            gp.gameState = gp.playState;
            }
        }

        //Dialogue state
        else if(gp.gameState == gp.dialogueState){
            if(code == KeyEvent.VK_ENTER){
                gp.gameState = gp.playState;
            }
        }

        //Gameover state
        else if(gp.gameState == gp.GameoverState){
            if(code == KeyEvent.VK_SPACE){
                gp.setupGame();
                gp.p.setDefaultValues();
                gp.playMusic(0);
                gp.gameState = gp.playState;
            }
        }

         //Ending
         else if(gp.gameState == gp.YouwonState){
            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP || code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
                if(gp.ui.commandNumber == 0){
                    gp.ui.commandNumber = 1;
                }
                else{
                    gp.ui.commandNumber--;
                }
            }
            if(code == KeyEvent.VK_ENTER){
                switch (gp.ui.commandNumber) {
                    case 0:
                        gp.setupGame();
                        gp.p.setDefaultValues();
                        gp.playMusic(0);
                        gp.gameState = gp.playState;
                        break;
                    case 1:
                        System.exit(0);
                        break;
                }
            }
        }
        
    }
    

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
            upPressed = false;
        }
        if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){
            leftPressed = false;
        }
        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
            downPressed = false;
        }
        if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){
            rightPressed = false;
        }
    }

}
