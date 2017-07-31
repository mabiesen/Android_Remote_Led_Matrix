package com.javacodegeeks.androidcanvasexample;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by matthew on 7/15/17.
 */

public class Led {
    String color = "#000000";
    Rect rectangle;
    String xlabel;
    String ylabel;
    Paint borderpaint;
    Paint fillpaint;

    Led(int startx, int starty, int boxsize, String xlabel, String ylabel){
        this.rectangle = new Rect((int) startx, (int) starty, (int) (boxsize + startx), (int) (boxsize + starty));
        this.xlabel = xlabel;
        this.ylabel = ylabel;

        borderpaint = new Paint();
        borderpaint.setAntiAlias(true);
        borderpaint.setColor(Color.BLACK);
        borderpaint.setStyle(Paint.Style.STROKE);
        borderpaint.setStrokeJoin(Paint.Join.ROUND);
        borderpaint.setStrokeWidth(4f);

        fillpaint = new Paint();
        fillpaint.setAntiAlias(true);
        fillpaint.setColor(Color.BLACK);
        fillpaint.setStyle(Paint.Style.FILL);
    }

    public void ChangeColorSetPaintFill(String newcolor){

        this.color = newcolor;

        fillpaint.setColor(Color.parseColor(newcolor));

    }

}
