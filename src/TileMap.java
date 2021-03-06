import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringReader;

public class TileMap {

    private int x;
    private int y;

    private int tileSize;
    private int[][] map;
    private int mapWidth;
    private int mapHeight;

    private int minx,miny, maxx=0, maxy=0;

    public TileMap(String s, int tileSize){
        this.tileSize = tileSize;
        try{
            BufferedReader br = new BufferedReader(new FileReader(s));
            mapWidth = Integer.parseInt(br.readLine());
            mapHeight = Integer.parseInt(br.readLine());
            map = new int[mapHeight][mapWidth];

            minx = GamePanel.WIDTH - mapWidth * tileSize;
            miny = GamePanel.HEIGHT - mapHeight * tileSize;

            String delimiters = " ";
            for(int row=0; row<mapHeight;row++){
                String line = br.readLine();
                String[] tokens = line.split(delimiters);

                for(int col=0;col<mapWidth;col++){
                    map[row][col] = Integer.parseInt(tokens[col]);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getx(){return x;}
    public int gety(){return y;}

    public void setx(int i){
        x = i;
        if(x < minx) x = minx;
        if(x > maxx) x = maxx;
    }
    public void sety(int i){
        y = i;
        if(y < miny) y = miny;
        if(y > maxy) y = maxy;
    }

    public int getColTile(int x){
        return x/tileSize;
    }
    public int getRowTile(int y){
        return y/tileSize;
    }
    public int getTile(int row, int col){
        return map[row][col];
    }
    public int getTileSize(){
        return tileSize;
    }

    //////////////////////////////////////////////////////////
    public void update(){

    }
    public void draw(Graphics2D g){
        for(int row=0; row<mapHeight;row++){
            for(int col=0;col<mapWidth;col++){
                int rc = map[row][col];
                if(rc == 0) {
                    g.setColor(Color.BLACK);
                } else if (rc == 1) {
                    g.setColor(Color.WHITE);
                }

                g.fillRect(x+col*tileSize, y+row*tileSize, tileSize, tileSize);
            }
        }
    }
}