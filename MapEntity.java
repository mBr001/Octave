/*
Copyright (C) 2012 Aravind Kumar

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>

Please note that in the event that any source file or other resource in this project does not include the above header, it should be assumed to be under the same license.
*/

 import java.awt.image.BufferedImage;
 import java.awt.image.ImageObserver;
 import javax.imageio.ImageIO;
 import java.io.IOException;
 import java.awt.Graphics;
 import java.io.File;

/**
 * Superclass of any object that the player can interact with on the screen.
**/
public class MapEntity{
    /**
     * A list of the entity's sprite frames.
    **/
    BufferedImage frames[];
    
    /**
     * The number of horizontal images in frames.
    **/
    int w;
    
    /**
     * The number of vertical images in frames.
    **/
    int h;
    
    /**
     * The speed at which the entity's sprite updates.
    **/
    int imgspeed;
    
    /**
     * The counter for the entity's sprite image updater;
    **/
    int imgcount;
    
    /**
     * The mode of the entity (used to implement different animation cycles).
    **/
    int mode;
    
    /**
     * The current frame of the current cycle.
    **/
    int frame;
    
    /**
     * The x component of the entity's position.
    **/
    int x;
    
    /**
     * The y component of the entity's position.
    **/
    int y;
    
    /**
     * @param url The URL (or local file path) of the entity's image.
     * @param cols The number of image columns used in the sprite (used to implement animation).
     * @param rows The number of image rows used in the sprite (used to implement animation).
     * @param hsep The horizontal separation of the frames.
     * @param vsep The vertical separation of the frames.
     * @param hoff The horizontal offset of the upper left frame.
     * @param voff The vertical offset of the upper left frame.
    **/
    MapEntity(String url,int cols,int rows,int ispeed,int hsep,int vsep,int hoff,int voff){
        BufferedImage img=null;
        try{
            img=ImageIO.read(new File(url));
        }
        catch(IOException e){}
        w=cols;
        h=rows;
        frames=new BufferedImage[w*h];
        imgspeed=ispeed;
        //frame width and height
        int fw=(img.getWidth()-hoff)/cols-hsep;
        int fh=(img.getHeight()-voff)/rows-vsep;
        for(int i=0;i<rows;++i){
            for(int j=0;j<cols;++j){
                frames[w*i+j]=img.getSubimage(hoff+j*fw,voff+i*fh,fw,fh);
            }
        }
    }
    
    MapEntity(String url,int cols,int rows,int ispeed){
        this(url,cols,rows,ispeed,0,0,0,0);
    }
    
    /**
     * Draw the entity to the grapic.
     *
     * @param g The graphic to which the entity should be drawn.
    **/
    void draw(Graphics g,ImageObserver o){
        g.drawImage(frames[mode*w+frame],x,y,o);
        //Update the imgcount/frame
        imgcount+=imgspeed;
        if(imgcount>=Octave.FPS){
            frame+=1;
            frame%=w;
            imgcount=0;
        }
    }
    
    /**
     * Sets the entity's sprite mode.
     *
     * @param m The new mode.
    **/
    void setMode(int m){
        mode=m;
        frame=0;
        imgcount=0;
    }
}