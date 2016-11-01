package com.fortefort.goout;

import android.content.Context;
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
 */

public class ServerHandle {

    static URL url;
    static Context baseContext;

    public static void init(Context c){
        baseContext = c;
        try{
            url = new URL("http://www.fortefort.com/goout.php");
        }catch(MalformedURLException e){
            e.printStackTrace();
            Toast.makeText(c, "Could not parse URL!", Toast.LENGTH_SHORT).show();
        }
    }

    public static ArrayList<String> RequestHost(JSONObject json){
        ArrayList<String> ret = new ArrayList<>();
        System.out.println(json.toString());
        try {
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setConnectTimeout(20000);
            httpCon.setReadTimeout(15000);
            httpCon.setRequestMethod("POST");
            httpCon.setDoInput(true);
            httpCon.setDoOutput(true);
            httpCon.addRequestProperty("Accept","application/json");
            httpCon.addRequestProperty("Content-Type","application/json");
            OutputStream os = httpCon.getOutputStream();
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            out.write("json="+json.toString());
            out.flush();
            out.close();
            os.flush();
            os.close();

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
            else{
                Toast.makeText(baseContext, "HTTP Response not OK", Toast.LENGTH_SHORT).show();
            }


            //httpCon.connect();
        }catch(IOException ioe){
            ioe.printStackTrace();
            Toast.makeText(baseContext, "Could not connect to server!",Toast.LENGTH_SHORT).show();
        }
         return ret;
    }

}
