package com.example.rafa.przepisykulinarne.mMySQL;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

public class DownloanderComponentsSearch extends AsyncTask<String,Void,String> {

    Context c;
    String urlAddress;
    ListView lv;
    int id_user;
    ProgressDialog pd;

    JSONParserRecipeCategory jsonParserRecipeCategory=new JSONParserRecipeCategory();

    public DownloanderComponentsSearch(Context c, String urlAddress, ListView lv, int id_user) {
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

        String typ= args[0];
        String comp1= args[1];
        String comp2= args[2];
        String comp3= args[3];
        String comp4= args[4];
        String comp5= args[5];

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id_user", ""+id_user));
        params.add(new BasicNameValuePair("type", typ));
        params.add(new BasicNameValuePair("comp1", comp1));
        params.add(new BasicNameValuePair("comp2", comp2));
        params.add(new BasicNameValuePair("comp3", comp3));
        params.add(new BasicNameValuePair("comp4", comp4));
        params.add(new BasicNameValuePair("comp5", comp5));

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
