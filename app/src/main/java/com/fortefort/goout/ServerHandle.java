package com.fortefort.goout;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Vaishnav on 11/1/2016.
 * Meant to
 */

public class ServerHandle extends AsyncTask<JSONObject, String, ArrayList<String> >{

    static URL url;

    public static void init(Context c){
        try{
            url = new URL("http://www.fortefort.com/goout.php");
        }catch(MalformedURLException e){
            e.printStackTrace();
            Toast.makeText(c, "Could not parse URL!", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected ArrayList<String> doInBackground(JSONObject...jsons){
        return RequestHost(jsons[0]);
    }
    private ArrayList<String> RequestHost(JSONObject json){
        ArrayList<String> ret = new ArrayList<>();
        System.out.println(json.toString());
        HttpURLConnection httpCon;
        try {
            System.err.println("OK");
            httpCon = (HttpURLConnection) url.openConnection();

            httpCon.setConnectTimeout(20000);
            httpCon.setReadTimeout(15000);
            httpCon.setRequestMethod("POST");
            httpCon.setDoInput(true);
            httpCon.setDoOutput(true);
            //httpCon.addRequestProperty("Accept","application/json");
            //httpCon.addRequestProperty("Content-Type","application/json");
            httpCon.connect();
            OutputStream os = httpCon.getOutputStream();

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            System.err.println("OK");
            out.write("json="+json.toString());
            out.flush();
            out.close();
            os.close();
            System.err.println("OK");
            int response = httpCon.getResponseCode();
            if(response == HttpURLConnection.HTTP_OK){
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
                while((line=br.readLine()) != null){
                    ret.add(line);
                    System.out.println(line);
                }
                br.close();
            }


            //httpCon.connect();
        }catch(IOException ioe){
            System.err.println("DAMN");
            ioe.printStackTrace();
            //Toast.makeText(baseContext, "Could not connect to server!",Toast.LENGTH_SHORT).show();
        }
         return ret;
    }

}
