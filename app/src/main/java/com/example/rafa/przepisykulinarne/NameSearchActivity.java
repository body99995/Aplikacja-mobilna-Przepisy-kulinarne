package com.example.rafa.przepisykulinarne;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rafa.przepisykulinarne.mMySQL.JSONParserNames;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NameSearchActivity extends AppCompatActivity {

    Button btnSearch;
    AutoCompleteTextView etName;

    String URL= "http://przepisy-kulinarne.cba.pl/public/android_app/recipes_names.php";

    JSONParserNames jsonParser=new JSONParserNames();

    List<String> zoom = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_search);

        Intent intent = getIntent();
        final int id_user  = intent.getIntExtra("id_users",0);

        btnSearch = (Button) findViewById(R.id.btnSearch);
        etName = (AutoCompleteTextView) findViewById(R.id.etName);



        GetRecipesName attemptLogin= new GetRecipesName();
        attemptLogin.execute(""+id_user);


        ArrayAdapter adapter = new
                ArrayAdapter(this,android.R.layout.simple_list_item_1,zoom);

        etName.setAdapter(adapter);
        etName.setThreshold(1); //ustawienie liczby liter po ilu pojawi się podpowiedź

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!etName.getText().toString().equals("")) {
                    Intent intent = new Intent(NameSearchActivity.this, Recipe1Activity.class);
                    intent.putExtra("nazwa", etName.getText().toString());
                    intent.putExtra("kategoria", "nazwa");
                    intent.putExtra("id_users", id_user);
                    startActivity(intent);
                }
            }
        });
    }


    private class GetRecipesName extends AsyncTask<String, String, JSONArray> {

        @Override

        protected void onPreExecute() {

            super.onPreExecute();

        }

        @Override

        protected JSONArray doInBackground(String... args) {

            String id_user= args[0];

            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id_user", id_user));

            JSONArray json = jsonParser.makeHttpRequest(URL, "POST", params);


            return json;

        }

        protected void onPostExecute(JSONArray result) {


            if (result != null) {
                Log.e("API123","Result: "+result);

                //Toast.makeText(getApplicationContext(),"Długość: "+result.length(),Toast.LENGTH_LONG).show();

                try {
                    JSONObject jo = null;

                    for (int i=0; i<result.length(); i++) {
                        jo = result.getJSONObject(i);

                        String name = jo.getString("name");
                        Log.d("API456","name:"  + name);

                        zoom.add(name);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Toast.makeText(getApplicationContext(), "Nie można pobrać żadnych danych z serwera", Toast.LENGTH_LONG).show();
            }

        }

    }
}
