package com.example.rafa.przepisykulinarne.mMySQL;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;

public class Downloander extends AsyncTask<String,Void,String> {

    Context c;
    String urlAddress;
    ListView lv;
    int id_user;
    ProgressDialog pd;

    JSONParserRecipeCategory jsonParserRecipeCategory=new JSONParserRecipeCategory();

    public Downloander(Context c, String urlAddress, ListView lv, int id_user) {
        this.c = c;
        this.urlAddress = urlAddress;
        this.lv = lv;
        this.id_user = id_user;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(c);
        pd.setTitle("Pobieranie");
        pd.setMessage("Pobieranie... Proszę czekać");
        pd.show();
    }

    @Override
    protected String doInBackground(String... args) {
        String category= args[0];
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("category", category));
        params.add(new BasicNameValuePair("id_user", ""+id_user));

        if (category.equals("nazwa")){
            String nazwa= args[1];
            params.add(new BasicNameValuePair("name", nazwa));
        }

        String json = jsonParserRecipeCategory.makeHttpRequest(urlAddress, "POST", params);
        return json;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        pd.dismiss();

        if (s == null){
            Toast.makeText(c,"Niepowodzenie, Null zwrócony",Toast.LENGTH_SHORT).show();
        }else {
            //CALL DATA PARSER TO PARSE
            DataParser parser = new DataParser(c,lv,s,id_user);
            parser.execute();
        }
    }


}
