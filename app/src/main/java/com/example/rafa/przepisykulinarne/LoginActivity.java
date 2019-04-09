package com.example.rafa.przepisykulinarne;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {


    EditText editEmail, editPassword, editName;
    Button btnSignIn, btnRegister;
    TextView tvLostPass;

    String URL= "http://przepisy-kulinarne.cba.pl/public/android_app/index.php";

    JSONParser jsonParser=new JSONParser();

    private CheckBox checkBoxRemember;
    private SharedPreferences sharedPrefs;
    private static final String PREFS_NAME = "PREFS_FILE";

    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail=(EditText)findViewById(R.id.editEmail);
        editName=(EditText)findViewById(R.id.editName);
        editPassword=(EditText)findViewById(R.id.editPassword);

        btnSignIn=(Button)findViewById(R.id.btnSignIn);
        btnRegister=(Button)findViewById(R.id.btnRegister);

        checkBoxRemember=(CheckBox)findViewById(R.id.checkBoxRememberMe);
        tvLostPass=(TextView)findViewById(R.id.tvLostPass);

        sharedPrefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        
        getPreferencesData();



        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ((!editName.getText().toString().equals("")) && (!editPassword.getText().toString().equals(""))) {

                    if(editPassword.getText().toString().length() < 5) {
                        Toast.makeText(getApplicationContext(), "Minimalna długość hasła to 5 znaków", Toast.LENGTH_SHORT).show();
                    }else if (editName.getText().toString().length() < 4) {
                        Toast.makeText(getApplicationContext(), "Minimalna długość loginu to 4 znaki", Toast.LENGTH_SHORT).show();
                    }else{
                        if (checkBoxRemember.isChecked()) {
                            Boolean isChecked = checkBoxRemember.isChecked();
                            SharedPreferences.Editor editor = sharedPrefs.edit();
                            editor.putString("pref_name", editName.getText().toString());
                            editor.putString("pref_pass", editPassword.getText().toString());
                            editor.putBoolean("pref_check", isChecked);
                            editor.apply();
                            Toast.makeText(getApplicationContext(), "Ustawienia zostaną zapisane", Toast.LENGTH_SHORT).show();
                        } else {
                            sharedPrefs.edit().clear().apply();
                        }

                        AttemptLogin attemptLogin = new AttemptLogin();
                        attemptLogin.execute(editName.getText().toString(), editPassword.getText().toString(), "");
                    }

                }else {
                    Toast.makeText(getApplicationContext(),"Wprowadź dane",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(i==0)
                {
                    i=1;
                    editEmail.setVisibility(View.VISIBLE);
                    btnSignIn.setVisibility(View.GONE);
                    tvLostPass.setVisibility(View.GONE);
                    btnRegister.setText("UTWÓRZ KONTO");
                }
                else{

                    if ((!editName.getText().toString().equals("")) && (!editPassword.getText().toString().equals("")) && (!editEmail.getText().toString().equals(""))) {
                        if(editPassword.getText().toString().length() < 5) {
                            Toast.makeText(getApplicationContext(), "Minimalna długość hasła to 5 znaków", Toast.LENGTH_SHORT).show();
                        }else if (editName.getText().toString().length() < 4) {
                            Toast.makeText(getApplicationContext(), "Minimalna długość loginu to 4 znaki", Toast.LENGTH_SHORT).show();
                        }else {

                            btnRegister.setText("REJESTRACJA");
                            editEmail.setVisibility(View.GONE);
                            btnSignIn.setVisibility(View.VISIBLE);
                            tvLostPass.setVisibility(View.VISIBLE);
                            i = 0;

                            AttemptLogin attemptLogin = new AttemptLogin();
                            attemptLogin.execute(editName.getText().toString(), editPassword.getText().toString(), editEmail.getText().toString());
                        }
                    }else {
                        Toast.makeText(getApplicationContext(),"Wprowadź dane",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        tvLostPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ForgotPassActivity.class);
                startActivity(intent);
            }
        });


    }

    private void getPreferencesData() {
        SharedPreferences sp = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        if (sp.contains("pref_name")){
            String u = sp.getString("pref_name", "not found.");
            editName.setText(u);
        }
        if (sp.contains("pref_pass")){
            String p = sp.getString("pref_pass", "not found.");
            editPassword.setText(p);
        }
        if(sp.contains("pref_check")){
            Boolean b = sp.getBoolean("pref_check", false);
            checkBoxRemember.setChecked(b);
        }
    }

    private class AttemptLogin extends AsyncTask<String, String, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... args) {

            String email = args[2];
            String password = args[1];
            String name= args[0];

            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("login", name));
            params.add(new BasicNameValuePair("password", password));
            if(email.length()>0)
                params.add(new BasicNameValuePair("email",email));

            JSONObject json = jsonParser.makeHttpRequest(URL, "POST", params);

            return json;
        }

        protected void onPostExecute(JSONObject result) {

            Intent intent = new Intent(LoginActivity.this,MainActivity.class);

            try {
                if (result != null) {

                    Toast.makeText(getApplicationContext(),result.getString("message"),Toast.LENGTH_LONG).show();

                    if(result.getString("message").equals("Poprawne zalogowanie")){

                        int id = Integer.parseInt(result.getString("id"));
                        Log.e("API123","ID users:"+id);
                        intent.putExtra("id_users",id);
                        startActivity(intent);
                        finish(); // po to by nie można było do tej aktywności wrócić

                        editName.getText().clear();
                        editPassword.getText().clear();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Nie można pobrać żadnych danych z serwera", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    //obsługa wyjścia podwójnym kliknięciem
    boolean doubleBackPressed = false;
    @Override
    public void onBackPressed() {

        if(doubleBackPressed) {
            super.onBackPressed();
        }else {
            doubleBackPressed = true;

            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackPressed = false;
                }
            },1000);
        }
    }
}
