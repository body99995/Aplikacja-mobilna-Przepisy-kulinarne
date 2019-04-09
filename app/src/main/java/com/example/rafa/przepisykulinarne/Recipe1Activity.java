package com.example.rafa.przepisykulinarne;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.rafa.przepisykulinarne.mListView.CustomAdapter;
import com.example.rafa.przepisykulinarne.mMySQL.Downloander;
import com.example.rafa.przepisykulinarne.mMySQL.DownloanderComponentsSearch;

public class Recipe1Activity extends AppCompatActivity {


    //String urlAddress= "http://212.182.18.241/praca_inz/public/android_app/recipes_test1.php";  //router dom
    //String urlAddress2= "http://212.182.18.241/praca_inz/public/android_app/recipes_favorite.php";  //router dom
    //String urlAddress3= "http://212.182.18.241/praca_inz/public/android_app/componentsSearch.php";  //router dom
    String urlAddress= "http://przepisy-kulinarne.cba.pl/public/android_app/recipes_category.php";  //router dom
    String urlAddress2= "http://przepisy-kulinarne.cba.pl/public/android_app/recipes_favorite.php";  //router dom
    String urlAddress3= "http://przepisy-kulinarne.cba.pl/public/android_app/componentsSearch.php";  //router dom

    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        Intent intent = getIntent();
        String category = intent.getStringExtra("kategoria");
        final int id_user  = intent.getIntExtra("id_users",0);
        lv = (ListView) findViewById(R.id.lv);

        if (category.equals("ulubione")){

            Downloander d = new Downloander(Recipe1Activity.this,urlAddress2,lv,id_user);
            d.execute(category);

        }else if(category.equals("skladniki")) {

            String typ = intent.getStringExtra("typ");
            String etSkl1 = intent.getStringExtra("etSkl1");
            String etSkl2 = intent.getStringExtra("etSkl2");
            String etSkl3 = intent.getStringExtra("etSkl3");
            String etSkl4 = intent.getStringExtra("etSkl4");
            String etSkl5 = intent.getStringExtra("etSkl5");
            DownloanderComponentsSearch d = new DownloanderComponentsSearch(Recipe1Activity.this,urlAddress3,lv,id_user);
            d.execute(typ, etSkl1, etSkl2, etSkl3, etSkl4, etSkl5);

        }else if(category.equals("nazwa")) {

            String nazwa = intent.getStringExtra("nazwa");
            Downloander d = new Downloander(Recipe1Activity.this,urlAddress,lv,id_user);
            d.execute(category,nazwa);

        }else{
                Downloander d = new Downloander(Recipe1Activity.this,urlAddress,lv,id_user);
                d.execute(category);
        }
    }
}
