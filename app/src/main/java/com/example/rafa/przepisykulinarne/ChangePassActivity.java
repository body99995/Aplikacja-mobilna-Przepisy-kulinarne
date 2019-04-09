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

public class ChangePassActivity extends AppCompatActivity {

    EditText editOldPass,editNewPass,editNewPass2;
    Button btnChange;

    String URL= "http://przepisy-kulinarne.cba.pl/public/android_app/index.php";

    JSONParser jsonParser=new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);

        Intent intent = getIntent();
        final int id_user  = intent.getIntExtra("id_users",0);

        editOldPass = (EditText) findViewById(R.id.editOldPass);
        editNewPass = (EditText) findViewById(R.id.editNewPassword);
        editNewPass2 = (EditText) findViewById(R.id.editNewPassword2);

        btnChange = (Button) findViewById(R.id.btnChangePass);

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(editOldPass.getText().toString().equals(""))&&!(editNewPass.getText().toString().equals(""))&&!(editNewPass2.getText().toString().equals(""))){
                    if(editOldPass.getText().toString().length() < 5 || editNewPass.getText().toString().length() < 5  || editNewPass2.getText().toString().length() < 5) {
                        Toast.makeText(getApplicationContext(), "Minimalna długość hasła to 5 znaków", Toast.LENGTH_SHORT).show();
                    }else {

                        if (editNewPass.getText().toString().equals(editNewPass2.getText().toString())) {

                            ChangePass change = new ChangePass();
                            change.execute("" + id_user, editOldPass.getText().toString(), editNewPass.getText().toString());

                        } else {
                            Toast.makeText(getApplicationContext(), "Podaj w polu 2 i 3 to samo hasło", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Uzupełnij wszystkie pola",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class ChangePass extends AsyncTask<String, String, JSONObject> {

        @Override

        protected void onPreExecute() {

            super.onPreExecute();

        }

        @Override

        protected JSONObject doInBackground(String... args) {

            String newPassword = args[2];
            String password = args[1];
            String id= args[0];

            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id_user", id));
            params.add(new BasicNameValuePair("password", password));
            params.add(new BasicNameValuePair("new_password", newPassword));


            JSONObject json = jsonParser.makeHttpRequest(URL, "POST", params);


            return json;

        }

        protected void onPostExecute(JSONObject result) {


            try {
                if (result != null) {

                    //Toast.makeText(getApplicationContext(),result.getString("message"),Toast.LENGTH_LONG).show();

                    if(result.getString("message").equals("Poprawna zmiana")){

                        Toast.makeText(getApplicationContext(),"Zmieniono hasło",Toast.LENGTH_LONG).show();
                        int id = Integer.parseInt(result.getString("id"));
                        Log.e("API123","ID users:"+id);


                        editOldPass.getText().clear();
                        editNewPass.getText().clear();
                        editNewPass2.getText().clear();
                    }else if(result.getString("message").equals("Niepoprawne stare haslo")){
                        Toast.makeText(getApplicationContext(),"Niepoprawne stare haslo",Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Nie można pobrać żadnych danych z serwera", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }
}
