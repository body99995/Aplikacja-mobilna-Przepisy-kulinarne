package com.example.rafa.przepisykulinarne;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ForgotPassActivity extends AppCompatActivity {

    EditText editNameForgot, editEmailForgot;
    Button btnRemind;

    String URL= "http://przepisy-kulinarne.cba.pl/public/android_app/index.php";

    JSONParser jsonParser=new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        editNameForgot = (EditText)findViewById(R.id.editNameForgot);
        editEmailForgot = (EditText)findViewById(R.id.editEmailForgot);
        btnRemind = (Button)findViewById(R.id.btnRemind);


        btnRemind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((!editNameForgot.getText().toString().equals("")) && (!editEmailForgot.getText().toString().equals(""))) {
                     if (editNameForgot.getText().toString().length() < 4) {
                        Toast.makeText(getApplicationContext(), "Minimalna długość loginu to 4 znaki", Toast.LENGTH_SHORT).show();
                    }else {
                         AttemptRemind attemptLogin = new AttemptRemind();
                         attemptLogin.execute(editNameForgot.getText().toString(), editEmailForgot.getText().toString());
                     }
                }else {
                    Toast.makeText(getApplicationContext(),"Wprowadź dane",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private class AttemptRemind extends AsyncTask<String, String, JSONObject> {

        @Override

        protected void onPreExecute() {

            super.onPreExecute();

        }

        @Override

        protected JSONObject doInBackground(String... args) {



            String email = args[1];
            String name= args[0];

            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("login", name));
            params.add(new BasicNameValuePair("email", email));

            JSONObject json = jsonParser.makeHttpRequest(URL, "POST", params);


            return json;

        }

        protected void onPostExecute(JSONObject result) {

            try {
                if (result != null) {

                    if (result.getString("message").equals("Poprawne zresetowanie")) {

                        Toast.makeText(getApplicationContext(), "Nowe hasło wysłano na emaila. Użyj go do logowania", Toast.LENGTH_LONG).show();

                    } else if (result.getString("message").equals("Problem z wyslaniem")) {

                        Toast.makeText(getApplicationContext(), "Problem z wyslaniem maila", Toast.LENGTH_LONG).show();

                    } else if (result.getString("message").equals("Problem ze zmiana")) {

                        Toast.makeText(getApplicationContext(), "Problem ze zmianą hasła", Toast.LENGTH_LONG).show();

                    }else {
                        Toast.makeText(getApplicationContext(), "Nie można pobrać żadnych danych z serwera", Toast.LENGTH_LONG).show();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }
}
