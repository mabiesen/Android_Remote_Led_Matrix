package com.javacodegeeks.androidcanvasexample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class CanvasView extends View {


    private Bitmap mBitmap;
    private Canvas mCanvas;
    Context context;
    LedGrid newgrid;
    public String currentcolor = "#ffffff";
    public String isTcpOn = "Off";

    public CanvasView(Context c, AttributeSet attrs) {
        super(c, attrs);
        context = c;
    }

    // override onSizeChanged
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //Set led matrix
        this.newgrid = new LedGrid(this.getWidth(),this.getHeight(),32);
        // your Canvas will draw onto the defined Bitmap
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    // override onDraw
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(Led thisled : this.newgrid.ledMatrix){
            canvas.drawRect(thisled.rectangle,thisled.borderpaint);
            canvas.drawRect(thisled.rectangle,thisled.fillpaint);
        }
    }

    public void clearCanvas() {
        invalidate();
        //Clear the matrix
        for(Led thisled : newgrid.ledMatrix){
            thisled.ChangeColorSetPaintFill("#000000");
        }
        //Send clear command to python
        if(this.isTcpOn == "On") {
            CallAsyncFromActivity("clear");
        }
    }

    public void colorBtnHndlr(View v){

        this.currentcolor = Integer.toHexString(((ColorDrawable)v.getBackground()).getColor());
        this.currentcolor = "#" + this.currentcolor;
    }

    public void flipTcpSwitch(View v){
        Button thisbtn = (Button)v;
        if(this.isTcpOn == "Off"){
            this.isTcpOn = "On";
            thisbtn.setText("TCP Comm: On");
        }else{
            this.isTcpOn = "Off";
            thisbtn.setText("TCP Comm: Off");
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        //Get the matrix coordinate, change color, call async
        String coordcombo = this.newgrid.ChangeColorReturnPythonLabel((int)(x),(int)(y),currentcolor);

        if(coordcombo.contains("nochange")){

        }
        else{
//            CharSequence text = this.isTcpOn;
//            int duration = Toast.LENGTH_SHORT;
//
//            Toast toast = Toast.makeText(context, text, duration);
//            toast.show();
            if(this.isTcpOn == "On"){
                CallAsyncFromActivity(coordcombo);
            }
        }
        invalidate();
        return true;
    }

    public void CallAsyncFromActivity(String coordinate){
            AndroidCanvasExample myactivity = (AndroidCanvasExample)getContext();
            myactivity.sendData(coordinate);
    }
}
