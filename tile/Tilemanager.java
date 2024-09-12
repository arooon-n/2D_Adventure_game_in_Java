package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.Utilitytool;

public class Tilemanager {
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];


    public Tilemanager(GamePanel gp){
        this.gp = gp;
        tile = new Tile[10];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        Loadmap("res/Maps/world01.txt");
    }

    public void getTileImage(){
        setup(0, "grass", false);
        setup(1, "wall", true);
        setup(2, "water", true);
        setup(3, "earth", false);
        setup(4, "tree", true);
        setup(5, "sand", false);
        setup(6, "pit", false);
        setup(7, "portal", false);
    }

    public void setup(int index, String imagepath, boolean collision){
        Utilitytool utool = new Utilitytool();

        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("res/tiles/"+imagepath+".png"));
            tile[index].image = utool.scaleImage(tile[index].image, gp.tilesize, gp.tilesize);
            tile[index].collision = collision;
            
        } catch (IOException e) {
            // TODO: handle exception
        }
    }

    public void Loadmap(String filepath){
        try {
            InputStream is = getClass().getResourceAsStream(filepath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int col = 0;
            int row = 0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow){
                String line = br.readLine();
                while (col < gp.maxWorldCol){
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++; 
                }
                if(col == gp.maxWorldCol){
                    col = 0;
                    row++;
                } 
            }
        } catch (Exception e) {
            
        }
    }

    public void draw(Graphics2D g2){
        int worldcol = 0;
        int worldrow = 0;

        while(worldcol < gp.maxWorldCol && worldrow < gp.maxWorldRow){
            int tileNum = mapTileNum[worldcol][worldrow];

            int worldX = worldcol*gp.tilesize;
            int worldY = worldrow*gp.tilesize;
            int screenX = worldX - gp.p.worldX + gp.p.screenX;
            int screenY = worldY - gp.p.worldY +gp.p.screenY;

            if(worldX+gp.tilesize>(gp.p.worldX-gp.p.screenX) && worldX-gp.tilesize<(gp.p.worldX+gp.p.screenX) && worldY+gp.tilesize > (gp.p.worldY-gp.p.screenY) && worldY-gp.tilesize < (gp.p.worldY+gp.p.screenY)){
                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }
            worldcol++;
            if(worldcol == gp.maxWorldCol){
                worldcol = 0;
                worldrow++;
            }
        }
    }
}
