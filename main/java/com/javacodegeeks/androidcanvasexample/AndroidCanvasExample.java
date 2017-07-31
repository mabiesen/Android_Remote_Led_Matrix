package com.javacodegeeks.androidcanvasexample;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

//Async class placed in activity for postexecute options to be immediately available to UI

public class AndroidCanvasExample extends Activity {

    private CanvasView customCanvas;

    private Socket socket;
    public PrintWriter out;
    public BufferedReader in ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customCanvas = (CanvasView) findViewById(R.id.signature_canvas);
    }

    //Button events
    public void clearCanvas(View v) {
        customCanvas.clearCanvas();
    }
    public void colorBtnHndlr(View v) {
        customCanvas.colorBtnHndlr(v);
    }
    public void flipTcpSwitch(View v) {
        customCanvas.flipTcpSwitch(v);
    }

    public void sendData(String coordinate){

        String[] mytaskparams = {"192.168.254.41","8080",coordinate};
        try{

            new AsyncTcpClient().execute(mytaskparams);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class AsyncTcpClient extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... args) {
            try {
                InetAddress serverAddr = InetAddress.getByName(args[0]);
                socket = new Socket(serverAddr, Integer.parseInt(args[1]));
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                out.println(args[2]);
                out.flush();

            } catch (IOException e) {}
            catch (Exception e){}

            return null;//returns what you want to pass to the onPostExecute()
        }

        @Override
        protected void onPostExecute(Void v){

        }
    }

}