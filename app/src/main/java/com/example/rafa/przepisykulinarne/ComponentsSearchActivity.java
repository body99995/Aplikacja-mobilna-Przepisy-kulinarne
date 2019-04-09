package com.example.rafa.przepisykulinarne;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rafa.przepisykulinarne.mMySQL.Downloander;
import com.example.rafa.przepisykulinarne.mMySQL.DownloanderComponentsSearch;

public class ComponentsSearchActivity extends AppCompatActivity {

    Button btnAdd, btnSearch, btnSearch2, btnDelete;
    AutoCompleteTextView etSkl1,etSkl2,etSkl3,etSkl4,etSkl5;


    String[] components={"ananas","banan","cebula","cytryna","czekolada","indyk","jabłka","kaczka","kakao",
            "kapusta","karp","kurczak","likier","masło","mleko","ogórki","papryka","pomidory","schab","sok","szpinak","śmietana","wódka"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_components_search);

        Intent intent = getIntent();
        final int id_user  = intent.getIntExtra("id_users",0);

        btnAdd = (Button) findViewById(R.id.btnDodajSkladnik);
        btnDelete = (Button) findViewById(R.id.btnUsunSkladnik);
        btnSearch = (Button) findViewById(R.id.btnSzukaj);
        btnSearch2 = (Button) findViewById(R.id.btnSzukaj2);
        etSkl1 = (AutoCompleteTextView) findViewById(R.id.etSkl1);
        etSkl2 = (AutoCompleteTextView) findViewById(R.id.etSkl2);
        etSkl3 = (AutoCompleteTextView) findViewById(R.id.etSkl3);
        etSkl4 = (AutoCompleteTextView) findViewById(R.id.etSkl4);
        etSkl5 = (AutoCompleteTextView) findViewById(R.id.etSkl5);



        ArrayAdapter adapter = new
                ArrayAdapter(this,android.R.layout.simple_list_item_1,components);

        etSkl1.setAdapter(adapter);
        etSkl1.setThreshold(1); //ustawienie liczby liter po ilu pojawi się podpowiedź
        etSkl2.setAdapter(adapter);
        etSkl2.setThreshold(1);
        etSkl3.setAdapter(adapter);
        etSkl3.setThreshold(1);
        etSkl4.setAdapter(adapter);
        etSkl4.setThreshold(1);
        etSkl5.setAdapter(adapter);
        etSkl5.setThreshold(1);


        final int[] i = {1};

        if(i[0]==1)
            btnDelete.setVisibility(View.GONE);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i[0]<=5)
                    i[0] += 1;

                switch (i[0]) {
                    case 2:  etSkl2.setVisibility(View.VISIBLE);
                        btnDelete.setVisibility(View.VISIBLE);
                        break;
                    case 3:  etSkl3.setVisibility(View.VISIBLE);;
                        break;
                    case 4:  etSkl4.setVisibility(View.VISIBLE);
                        break;
                    case 5:  etSkl5.setVisibility(View.VISIBLE);
                        btnAdd.setVisibility(View.GONE);
                        break;
                }

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (i[0]) {
                    case 2:  etSkl2.setVisibility(View.GONE);
                        etSkl2.getText().clear();
                        btnDelete.setVisibility(View.GONE);
                        break;
                    case 3:  etSkl3.setVisibility(View.GONE);
                        etSkl3.getText().clear();
                        break;
                    case 4:  etSkl4.setVisibility(View.GONE);
                        etSkl4.getText().clear();
                        break;
                    case 5:  etSkl5.setVisibility(View.GONE);
                        etSkl5.getText().clear();
                        btnAdd.setVisibility(View.VISIBLE);
                        break;
                }


                if(i[0]<=5 && i[0]>1)
                    i[0] -= 1;

            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!etSkl1.getText().toString().equals("")){

                    Intent intent = new Intent(ComponentsSearchActivity.this,Recipe1Activity.class);
                    intent.putExtra("kategoria","skladniki");
                    intent.putExtra("id_users",id_user);
                    intent.putExtra("typ","1");


                    String skl1 = etSkl1.getText().toString();
                    if (skl1.length()>1)
                        skl1 = skl1.substring(0,skl1.length()-1); //przycinanie końcówki stringa
                    intent.putExtra("etSkl1",skl1);

                    if(!etSkl2.getText().toString().equals("")) {
                        String skl2 = etSkl2.getText().toString();
                        if (skl2.length()>1)
                            skl2 = skl2.substring(0,skl2.length()-1); //przycinanie końcówki stringa
                        intent.putExtra("etSkl2", skl2);
                    }else{
                        intent.putExtra("etSkl2", skl1);
                    }
                    if(!etSkl3.getText().toString().equals("")) {
                        String skl3 = etSkl3.getText().toString();
                        if (skl3.length()>1)
                            skl3 = skl3.substring(0,skl3.length()-1); //przycinanie końcówki stringa
                        intent.putExtra("etSkl3", skl3);
                    }else {
                        intent.putExtra("etSkl3", skl1);
                    }
                    if(!etSkl4.getText().toString().equals("")) {
                        String skl4 = etSkl4.getText().toString();
                        if (skl4.length()>1)
                            skl4 = skl4.substring(0,skl4.length()-1); //przycinanie końcówki stringa
                        intent.putExtra("etSkl4", skl4);
                    }else {
                        intent.putExtra("etSkl4", skl1);
                    }
                    if(!etSkl5.getText().toString().equals("")) {
                        String skl5 = etSkl5.getText().toString();
                        if (skl5.length()>1)
                            skl5 = skl5.substring(0,skl5.length()-1); //przycinanie końcówki stringa
                        intent.putExtra("etSkl5", skl5);
                    }else {
                        intent.putExtra("etSkl5", skl1);
                    }
                    startActivity(intent);

                }else {
                    Toast.makeText(getApplicationContext(),"Wpisz chociaż jeden składnik", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnSearch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!etSkl1.getText().toString().equals("")){

                    Intent intent = new Intent(ComponentsSearchActivity.this,Recipe1Activity.class);
                    intent.putExtra("kategoria","skladniki");
                    intent.putExtra("id_users",id_user);
                    intent.putExtra("typ","2");

                    String skl1 = etSkl1.getText().toString();
                    if (skl1.length()>1)
                        skl1 = skl1.substring(0,skl1.length()-1); //przycinanie końcówki stringa
                    intent.putExtra("etSkl1",skl1);

                    if(!etSkl2.getText().toString().equals("")) {
                        String skl2 = etSkl2.getText().toString();
                        if (skl2.length()>1)
                            skl2 = skl2.substring(0,skl2.length()-1); //przycinanie końcówki stringa
                        intent.putExtra("etSkl2", skl2);
                    }else{
                        intent.putExtra("etSkl2", skl1);
                    }
                    if(!etSkl3.getText().toString().equals("")) {
                        String skl3 = etSkl3.getText().toString();
                        if (skl3.length()>1)
                            skl3 = skl3.substring(0,skl3.length()-1); //przycinanie końcówki stringa
                        intent.putExtra("etSkl3", skl3);
                    }else {
                        intent.putExtra("etSkl3", skl1);
                    }
                    if(!etSkl4.getText().toString().equals("")) {
                        String skl4 = etSkl4.getText().toString();
                        if (skl4.length()>1)
                            skl4 = skl4.substring(0,skl4.length()-1); //przycinanie końcówki stringa
                        intent.putExtra("etSkl4", skl4);
                    }else {
                        intent.putExtra("etSkl4", skl1);
                    }
                    if(!etSkl5.getText().toString().equals("")) {
                        String skl5 = etSkl5.getText().toString();
                        if (skl5.length()>1)
                            skl5 = skl5.substring(0,skl5.length()-1); //przycinanie końcówki stringa
                        intent.putExtra("etSkl5", skl5);
                    }else {
                        intent.putExtra("etSkl5", skl1);
                    }
                    startActivity(intent);

                }else {
                    Toast.makeText(getApplicationContext(),"Wpisz chociaż jeden składnik", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
