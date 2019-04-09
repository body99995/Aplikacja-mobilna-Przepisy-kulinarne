package com.example.rafa.przepisykulinarne;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rafa.przepisykulinarne.mMySQL.DownloanderOpinions;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.ArrayList;

import okio.Utf8;

public class OpinionsActivity extends AppCompatActivity {

    //String urlAddress= "http://192.168.1.146/praca_inz/public/android_app/recipe_opinions.php";  //router dom
    //String urlAddress2= "http://192.168.1.146/praca_inz/public/android_app/userOpinion.php";  //router dom
    String urlAddress= "http://przepisy-kulinarne.cba.pl/public/android_app/recipe_opinions.php";  //router dom
    String urlAddress2= "http://przepisy-kulinarne.cba.pl/public/android_app/userOpinion.php";  //router dom

    ListView listView;
    Button btnOpinion;
    EditText opinion;
    JSONParser jsonParser=new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_model_opinions);

        Intent intent = getIntent();
        final int id_recipe = intent.getIntExtra("id_recipe",0);
        final int id_user  = intent.getIntExtra("id_users",0);


        listView = (ListView) findViewById(R.id.lvOpinions);

        DownloanderOpinions d = new DownloanderOpinions(OpinionsActivity.this,urlAddress,listView,id_user);
        d.execute(""+id_recipe);

        btnOpinion = (Button) findViewById(R.id.btnOpinion);
        opinion = (EditText) findViewById(R.id.yourOpinion);

        btnOpinion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!opinion.getText().toString().equals("")){


                    //Toast.makeText(getApplicationContext(),"Opinia: "+opinion.getText(),Toast.LENGTH_SHORT).show();
                    UserOpinion userRating = new UserOpinion();
                    userRating.execute("" + id_user,"" + id_recipe, opinion.getText().toString());
                    //setContentView(R.layout.content_model_opinions);
                    DownloanderOpinions d = new DownloanderOpinions(OpinionsActivity.this,urlAddress,listView,id_user);
                    d.execute(""+id_recipe);
                }else{
                    Toast.makeText(getApplicationContext(),"Wpisz najpierw swoją opinię "+opinion.getText(),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private class UserOpinion extends AsyncTask<String, String, JSONObject> {

        @Override

        protected void onPreExecute() {

            super.onPreExecute();

        }

        @Override

        protected JSONObject doInBackground(String... args) {


            String id_android_users = args[0];
            String id_recipe = args[1];
            String opinion = args[2];

            Log.e("API45678","id_android_users:"+id_android_users);
            Log.e("API45678","id_recipe:"+id_recipe);
            Log.e("API45678","Opinion:"+opinion);

            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id_android_users", id_android_users));
            params.add(new BasicNameValuePair("id_recipe", id_recipe));
            params.add(new BasicNameValuePair("opinion", opinion));

            JSONObject json = jsonParser.makeHttpRequest(urlAddress2, "POST", params);


            return json;

        }

        protected void onPostExecute(JSONObject result) {



            try {
                if (result != null) {

                    //Toast.makeText(getApplicationContext(),result.getString("message"),Toast.LENGTH_LONG).show();

                    if(result.getString("message").equals("Dodano ocene")){

                        //int id = result.getString("id");
                        //int id = Integer.parseInt(result.getString("id"));
                        Log.e("API4567","Dodano ocene");

                        Toast.makeText(getApplicationContext(), "Dodano opinie", Toast.LENGTH_LONG).show();




                    } else if (result.getString("message").equals("Zmieniono")) {

                        Log.e("API4567", "Zmieniono opinie");

                        Toast.makeText(getApplicationContext(), "Zmieniono", Toast.LENGTH_LONG).show();


                    }

                }else{
                    Toast.makeText(getApplicationContext(), "Nie można dokonać zmiany w tym momencie", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }
}
