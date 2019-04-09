package com.example.rafa.przepisykulinarne.mMySQL;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rafa.przepisykulinarne.mDataObject.Recipe;
import com.example.rafa.przepisykulinarne.mListView.OpinionsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DataOpinionParser extends AsyncTask<Void,Void,Integer> {

    Context c;
    ListView lv;
    String jsonData;
    int id_user;

    ProgressDialog pd;
    ArrayList<Recipe> recipes = new ArrayList<>();

    public DataOpinionParser(Context c, ListView lv, String jsonData, int id_user) {
        this.c = c;
        this.lv = lv;
        this.jsonData = jsonData;
        this.id_user = id_user;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd = new ProgressDialog(c);
        pd.setTitle("Parsowanie");
        pd.setMessage("Parsowanie...Proszę czekać");
        pd.show();
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        return this.parseData();
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);

        pd.dismiss();
        if (result == 0){
            Toast.makeText(c,"Niemożliwe parsowanie",Toast.LENGTH_SHORT).show();
        }else {
            //CALL ADAPTER TO BIND DATA
            OpinionsAdapter adapter=new OpinionsAdapter(c,recipes,id_user);
            lv.setAdapter(adapter);

        }
    }

    private int parseData(){

        try {
            JSONArray ja = new JSONArray(jsonData);
            JSONObject jo = null;

            recipes.clear();
            Recipe r=null;

            for (int i=0; i<ja.length(); i++){
                jo = ja.getJSONObject(i);

                int id = jo.getInt("id");
                Log.d("API456","id:"  + id);


                int rating;

                //if(!jo.getString("rating").equals("null")) {
                if(!jo.getString("rating").equals("")) {
                    rating = jo.getInt("rating");
                }else {
                    rating = 0;
                }

                Log.d("API456","rating:"  + rating);


                String opinion = jo.getString("opinion");
                Log.d("API456","opinion:"  + opinion);


                String login = jo.getString("login");
                Log.d("API456","login:"  + login);


                r = new Recipe();
                r.setId(id);
                r.setRating(rating);
                r.setOpinion(opinion);
                r.setLogin(login);


                recipes.add(r);
            }

            return 1;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
