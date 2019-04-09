package com.example.rafa.przepisykulinarne.mListView;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rafa.przepisykulinarne.R;
import com.example.rafa.przepisykulinarne.Recipe2DetailsActivity;
import com.example.rafa.przepisykulinarne.mDataObject.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OpinionsAdapter extends BaseAdapter {

    Context c;
    ArrayList<Recipe> recipes;
    int id_user;


    public OpinionsAdapter(Context c, ArrayList<Recipe> recipes, int id_user) {
        this.c = c;
        this.recipes = recipes;
        this.id_user = id_user;
    }

    @Override
    public int getCount() {
        return recipes.size();
    }

    @Override
    public Object getItem(int position) {
        return recipes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return recipes.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView= LayoutInflater.from(c).inflate(R.layout.model_opinions,parent,false);
        }


        final Recipe s= (Recipe) this.getItem(position);

        Log.d("API456","W OpinionsAdapter");

        TextView opinion = (TextView) convertView.findViewById(R.id.opinion);
        RatingBar ratingBar = (RatingBar) convertView.findViewById(R.id.starOpinions);
        TextView login = (TextView) convertView.findViewById(R.id.userName);

        if(!s.getOpinion().equals("")) {
            opinion.setText(s.getOpinion());
        }else {
            opinion.setVisibility(View.GONE);
        }

        int rating = s.getRating();


        if (rating>0){
            ratingBar.setRating(rating);
        }else {
            ratingBar.setVisibility(View.GONE);
        }


        login.setText("Użytkownik: "+s.getLogin());

        return convertView;
    }
}
