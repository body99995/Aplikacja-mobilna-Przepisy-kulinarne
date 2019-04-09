package com.example.rafa.przepisykulinarne.mMySQL;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rafa.przepisykulinarne.mDataObject.Recipe;
import com.example.rafa.przepisykulinarne.mListView.CustomAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DataParser extends AsyncTask<Void,Void,Integer> {

    Context c;
    ListView lv;
    String jsonData;
    int id_user;

    ProgressDialog pd;
    ArrayList<Recipe> recipes = new ArrayList<>();

    public DataParser(Context c, ListView lv, String jsonData, int id_user) {
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
            CustomAdapter adapter=new CustomAdapter(c,recipes,id_user);
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
                String name = jo.getString("name");
                Log.d("API456","name:"  + name);
                String description = jo.getString("description");
                Log.d("API456","desc:"  + description);

                String main_photo = jo.getString("main_photo");
                String components = jo.getString("components");
                String way_of_preparation = jo.getString("way_of_preparation");
                String photo1 = jo.getString("photo1");
                String photo2 = jo.getString("photo2");
                String photo3 = jo.getString("photo3");
                String URL_video = jo.getString("URL_video");
                int id_favorite_recipes;

                if(!jo.getString("favorite_recipes").equals("null")) {
                    id_favorite_recipes = jo.getInt("favorite_recipes");
                }else {
                    id_favorite_recipes = 0;
                }

                int rating;

                if(!jo.getString("rating").equals("")&&!jo.getString("rating").equals("null")) {
                    rating = jo.getInt("rating");
                }else {
                    rating = 0;
                }

                Log.d("API456","rating:"  + rating);
                String URLphotos = "http://przepisy-kulinarne.cba.pl/public/upload/";

                r = new Recipe();
                r.setId(id);
                r.setName(name);
                r.setDescription(description);
                r.setImage(URLphotos+main_photo);
                r.setWay_of_preparation(way_of_preparation);
                r.setComponents(components);
                r.setId_favorite_recipes(id_favorite_recipes);
                r.setRating(rating);

                if(photo1 != "null") {
                    r.setPhoto1(URLphotos + photo1);
                }else {
                    r.setPhoto1(photo1);
                }

                if(photo2 != "null") {
                    r.setPhoto2(URLphotos + photo2);
                }else {
                    r.setPhoto2(photo2);
                }

                if(photo3 != "null") {
                    r.setPhoto3(URLphotos + photo3);
                }else {
                    r.setPhoto3(photo3);
                }

                r.setURL_video(URL_video);
                recipes.add(r);
            }

            return 1;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
