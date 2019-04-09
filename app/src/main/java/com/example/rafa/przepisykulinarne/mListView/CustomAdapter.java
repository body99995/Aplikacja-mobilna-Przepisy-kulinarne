package com.example.rafa.przepisykulinarne.mListView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rafa.przepisykulinarne.MainActivity;
import com.example.rafa.przepisykulinarne.R;
import com.example.rafa.przepisykulinarne.Recipe1Activity;
import com.example.rafa.przepisykulinarne.Recipe2DetailsActivity;
import com.example.rafa.przepisykulinarne.mDataObject.Recipe;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    Context c;
    ArrayList<Recipe> recipes;
    int id_user;

    public CustomAdapter(Context c, ArrayList<Recipe> recipes, int id_user) {
        this.c = c;
        this.recipes = recipes;
        this.id_user = id_user;


        if (recipes.toString().equals("[]")){
            Toast.makeText(c,"Brak przepisów do wyświetlenia",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public int getCount() {
        return recipes.size();
    }

    @Override
    public Object getItem(int position) { return recipes.get(position); }

    @Override
    public long getItemId(int position) { return recipes.get(position).getId(); }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView= LayoutInflater.from(c).inflate(R.layout.activity_recipe1,parent,false);
        }
        final Recipe s= (Recipe) this.getItem(position);

        ImageView img= (ImageView) convertView.findViewById(R.id.cardImage);
        TextView nameTxt = (TextView) convertView.findViewById(R.id.cardTitle);

        nameTxt.setText(s.getName());


        if(s.getImage() != null && s.getImage().length() > 0)
        {
            Picasso.get().load(s.getImage()).into(img);
        }else {
            Toast.makeText(c, "Empty Image URL", Toast.LENGTH_LONG).show();
            Picasso.get().load(R.drawable.placeholder).into(img);
        }



        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(c,Recipe2DetailsActivity.class);
                intent.putExtra("id_recipe",s.getId());
                intent.putExtra("name",s.getName());
                intent.putExtra("main_photo",s.getImage());
                intent.putExtra("description",s.getDescription());
                intent.putExtra("components",s.getComponents());
                intent.putExtra("way_of_preparation",s.getWay_of_preparation());
                intent.putExtra("photo1",s.getPhoto1());
                intent.putExtra("photo2",s.getPhoto2());
                intent.putExtra("photo3",s.getPhoto3());
                intent.putExtra("URL_video",s.getURL_video());
                intent.putExtra("id_favorite_recipes",s.getId_favorite_recipes());
                intent.putExtra("rating",s.getRating());
                intent.putExtra("id_user",id_user);

                c.startActivity(intent);
            }
        });

        return convertView;
    }
}
