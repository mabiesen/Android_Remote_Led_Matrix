package com.javacodegeeks.androidcanvasexample;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matthew on 7/15/17.
 */

public class LedGrid {
    double gridSize;
    double firstsize;
    double boxSize;
    int gridDimension;
    int gridpad;
    LetterNumberGridRepo repo;
    List<Led> ledMatrix;

    //Width and height should resemble the view being altered.
    LedGrid(double width, double height, int dimension){
        this.repo = new LetterNumberGridRepo();
        this.gridDimension = dimension;
        this.ledMatrix = new ArrayList<Led>();

        //Confirm is square, if not take smaller and make square
        if(width != height){
            if(width < height){
                this.firstsize = (int)width;
            }else{
                this.firstsize = (int)height;
            }
        }else{
            this.firstsize = (int)height;
        }

        //Determine box size
        this.boxSize = Math.floor(this.firstsize/this.gridDimension);
        this.gridSize = this.boxSize*this.gridDimension;

        //The above provided integer values, now determine remaining space, divide by 2 and thats your pad
        this.gridpad = (int)(this.firstsize - this.gridSize)/2;

        CreateLedMatrix();

    }


    public void CreateLedMatrix(){
        int xbase = this.gridpad;
        int ybase = this.gridpad;
        String xlbl = "";
        String ylbl = "";

        for(int xnum = 0; xnum < gridDimension; xnum++){
            for(int ynum = 0; ynum < gridDimension; ynum++){
                //Get xy letter coordinate for python
                ylbl = Integer.toString(ynum+1);
                xlbl = this.repo.NumToAlph(xnum);

                //Create the led in the list
                this.ledMatrix.add(new Led(xbase, ybase, (int)this.boxSize, xlbl, ylbl));
                ybase = ybase + (int)this.boxSize;
            }
            ybase = this.gridpad;
            xbase = xbase + (int)this.boxSize;
        }
    }

    public String ChangeColorReturnPythonLabel(int x, int y, String newcolor){
        int thisposition = WhichLedPosToArrayPosition(x,y);
        if(thisposition != -1) {
            String firstcolor = this.ledMatrix.get(thisposition).color;
            if (firstcolor == newcolor) {
                return "nochange";
            } else {
                this.ledMatrix.get(thisposition).ChangeColorSetPaintFill(newcolor);
                String pythonlabel = CombineLabelsAndColor(this.ledMatrix.get(thisposition));
                return pythonlabel;
            }
        }else{
            return "nochange";
        }
    }


   public int WhichLedPosToArrayPosition(int xpos, int ypos){
        int count = 0;

        //Look through Leds for the right bases
        for(Led thisled : this.ledMatrix){

            if((int)thisled.rectangle.left <= xpos && (int)thisled.rectangle.right >= xpos){
                if((int)thisled.rectangle.top <= ypos && (int)thisled.rectangle.bottom >=ypos) {
                    return count;
                }
            }
            count++;
        }
        return -1;
   }

    public String CombineLabelsAndColor(Led led){
        String astring = "";
        astring = led.xlabel + led.ylabel + ":" + led.color;
        //astring = String.valueOf(led.starty);
        return astring;
    }


}
