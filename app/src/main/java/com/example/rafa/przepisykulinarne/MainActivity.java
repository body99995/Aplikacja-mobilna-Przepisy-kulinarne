package com.example.rafa.przepisykulinarne;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnCiasta,btnDrinki,btnNapoje,btnPrzystawki,btnZupy,btnUlubione,btnSklaniki,btnNazwa;
    Toolbar toolbar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent1 = getIntent();
        final int id_user  = intent1.getIntExtra("id_users",0);

        switch (item.getItemId()) {
            case R.id.itemChangePass:

                Intent intent = new Intent(MainActivity.this,ChangePassActivity.class);
                intent.putExtra("id_users",id_user);
                startActivity(intent);
                break;
            case R.id.itemLogout:

                Intent intent2 = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent2);
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        final int id_user  = intent.getIntExtra("id_users",0);

        btnCiasta = (Button) findViewById(R.id.btnCiasta);
        btnDrinki = (Button) findViewById(R.id.btnDrinki);
        btnNapoje = (Button) findViewById(R.id.btnNapoje);
        btnPrzystawki = (Button) findViewById(R.id.btnPrzystawki);
        btnZupy = (Button) findViewById(R.id.btnZupy);
        btnUlubione = (Button) findViewById(R.id.btnUlubione);
        btnSklaniki = (Button) findViewById(R.id.btnSkladniki);
        btnNazwa = (Button) findViewById(R.id.btnNazwa);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Przepisy kulinarne");




        btnCiasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Recipe1Activity.class);
                intent.putExtra("kategoria","Ciastka");
                intent.putExtra("id_users",id_user);
                startActivity(intent);
            }
        });

        btnDrinki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Recipe1Activity.class);
                intent.putExtra("kategoria","Drinki");
                intent.putExtra("id_users",id_user);
                startActivity(intent);
            }
        });

        btnNapoje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Recipe1Activity.class);
                intent.putExtra("kategoria","Napoje");
                intent.putExtra("id_users",id_user);
                startActivity(intent);
            }
        });

        btnPrzystawki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Recipe1Activity.class);
                intent.putExtra("kategoria","Przystawki");
                intent.putExtra("id_users",id_user);
                startActivity(intent);
            }
        });

        btnZupy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Recipe1Activity.class);
                intent.putExtra("kategoria","Zupy");
                intent.putExtra("id_users",id_user);
                startActivity(intent);
            }
        });


        btnUlubione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Recipe1Activity.class);
                intent.putExtra("kategoria","ulubione");
                intent.putExtra("id_users",id_user);
                startActivity(intent);
            }
        });

        btnSklaniki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ComponentsSearchActivity.class);
                intent.putExtra("id_users",id_user);
                startActivity(intent);
            }
        });

        btnNazwa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,NameSearchActivity.class);
                intent.putExtra("id_users",id_user);
                startActivity(intent);
            }
        });
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
