package com.example.rafa.przepisykulinarne;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rafa.przepisykulinarne.mListView.ViewPagerAdapter;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;
import android.support.v7.widget.Toolbar;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;



public class Recipe2DetailsActivity extends YouTubeBaseActivity {

    //String urlAddress= "http://przepisy-kulinarne.cba.pl/public/android_app/recipes.php";
    //String urlAddress= "http://192.168.0.100/praca_inz/public/android_app/recipes_test.php";  //router dom
    //String urlAddress= "http://192.168.43.75/praca_inz/public/android_app/recipes_test.php";  //Redmi

    public final String API_KEY = "AIzaSyAzu6YOqargM0mKbOjL_O12cHaDhfnQmBY";

    ListView lv; //możliwe że niepotrzebne tutaj już!!!

    ImageView main_photo_iv,photo1_iv,photo2_iv,photo3_iv;
    TextView name_tv,desc_et,components_et, wop_et;
    //EditText components_et, wop_et;
    Toolbar toolbar;

    YouTubePlayerView playerView;

    private String[] imageURLs;

    JSONParser jsonParser=new JSONParser();

    //String urlAddress= "http://192.168.1.146/praca_inz/public/android_app/favoriteRecipe.php";
    //String urlAddress2= "http://192.168.1.146/praca_inz/public/android_app/userRating.php";

    String urlAddress= "http://przepisy-kulinarne.cba.pl/public/android_app/add_del_favoriteRecipe.php";
    String urlAddress2= "http://przepisy-kulinarne.cba.pl/public/android_app/userRating.php";

    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe2_details);

        toolbar = findViewById(R.id.toolbar);


        Intent intent = getIntent();
        final int id_recipe = intent.getIntExtra("id_recipe",0);
        String name = intent.getStringExtra("name");
        String main_photo = intent.getStringExtra("main_photo");

        String desc = intent.getStringExtra("description");
        String components = intent.getStringExtra("components");
        String wop = intent.getStringExtra("way_of_preparation");
        /*
        String photo1 = intent.getStringExtra("photo1");
        String photo2 = intent.getStringExtra("photo2");
        String photo3 = intent.getStringExtra("photo3"); */

        String videoURL = intent.getStringExtra("URL_video");
        int id_favorite_recipes = intent.getIntExtra("id_favorite_recipes",0);
        int rating = intent.getIntExtra("rating", 0);
        final int id_user  = intent.getIntExtra("id_user",0);

        //Toast.makeText(this, "ID favorite_recipe:" + id_favorite_recipes, Toast.LENGTH_SHORT).show();



        int it =0;

        for(int i=1; i<4; i++){
            if(!intent.getStringExtra("photo"+i).equals("null")){
                //imageURLs[it] = intent.getStringExtra("photo"+i);
                it++;
            }
        }

        imageURLs = new String[it];

        //Toast.makeText(this, "it:" + it, Toast.LENGTH_SHORT).show();

        it =0;

        for(int i=1; i<4; i++){
            if(!intent.getStringExtra("photo"+i).equals("null")){
                imageURLs[it] = intent.getStringExtra("photo"+i);
                it++;
            }
        }

        /*if(intent.getStringExtra("photo2").equals("null")){
            Toast.makeText(this, "NULL photo2", Toast.LENGTH_SHORT).show();
        }*/
        //Toast.makeText(this, "tablica:" + imageURLs.length, Toast.LENGTH_SHORT).show();



        main_photo_iv = (ImageView) findViewById(R.id.main_photo_iv_details);
        name_tv = (TextView) findViewById(R.id.name_tv_details);
        desc_et = (TextView) findViewById(R.id.desc_et_details);
        desc_et.setEnabled(false);
        components_et = (TextView) findViewById(R.id.components_et_details);
        components_et.setEnabled(false);
        wop_et = (TextView) findViewById(R.id.wop_et_details);
        wop_et.setEnabled(false);

        name_tv.setText(name);
        desc_et.setText(desc);
        components_et.setText(components);
        wop_et.setText(wop);

        if(main_photo != null && main_photo.length() > 0)
        {
            Picasso.get().load(main_photo).into(main_photo_iv);
        }else {
            Toast.makeText(this, "Empty Image URL", Toast.LENGTH_LONG).show();
            Picasso.get().load(R.drawable.placeholder).into(main_photo_iv);
        }


        ViewPager viewPager = findViewById(R.id.viewPager);
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        if(it != 0) {

            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, imageURLs);
            viewPager.setAdapter(viewPagerAdapter);

            indicator.setViewPager(viewPager);
            //viewPagerAdapter.registerDataSetObserver(indicator.getDataSetObserver());
        }else {
            viewPager.setVisibility(View.GONE);
            indicator.setVisibility(View.GONE);
        }


        playerView = (YouTubePlayerView) findViewById(R.id.playerYT);

        if (!videoURL.equals("null")) {
            String[] splitedArray = null;
            splitedArray = videoURL.split("/");
            for (int i = 0; i < splitedArray.length; i++) {
                System.out.println(splitedArray[i]);
            }
            System.out.println(splitedArray[4]);
            final String VIDEO_CODE = splitedArray[4];


            playerView.initialize(API_KEY, new YouTubePlayer.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                    if (!b) {
                        youTubePlayer.loadVideo(VIDEO_CODE);

                        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                        youTubePlayer.pause();
                        youTubePlayer.seekToMillis(10);
                    }
                }

                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                    //Toast.makeText(Recipe2DetailsActivity.this, youTubeInitializationResult.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            playerView.setVisibility(View.GONE);
        }


        //Toast.makeText(this, "ID user:" + id_user, Toast.LENGTH_SHORT).show();

        //Downloander_recipe2_details d = new Downloander_recipe2_details(Recipe2DetailsActivity.this,urlAddress,lv);
        //d.execute(""+id_recipe);

        final ImageButton ButtonStar = (ImageButton) findViewById(R.id.favorite);

        if (id_favorite_recipes>0){
            //ButtonStar.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.star_on));
            ButtonStar.setImageResource(R.drawable.star_on);
            ButtonStar.setActivated(true);
        }else{
            //ButtonStar.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.star_off));
            ButtonStar.setImageResource(R.drawable.star_off);
            ButtonStar.setActivated(false);
        }



        ButtonStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(this, "ID user:" + ), Toast.LENGTH_SHORT).show();
                /*
                if (ButtonStar.isActivated()){

                    EditFravoriteRecipe editFav = new EditFravoriteRecipe();
                    editFav.execute("" + id_user,"" + id_recipe);


                    ButtonStar.setImageResource(R.drawable.star_off);
                    ButtonStar.setActivated(false);
                }else {
                    ButtonStar.setImageResource(R.drawable.star_on);
                    ButtonStar.setActivated(true);
                }*/

                EditFravoriteRecipe editFav = new EditFravoriteRecipe();
                editFav.execute("" + id_user,"" + id_recipe);
                //Log.e("API4567","Po obiekcie");

                if (ButtonStar.isActivated()){
                    ButtonStar.setImageResource(R.drawable.star_off);
                    ButtonStar.setActivated(false);
                }else {
                    ButtonStar.setImageResource(R.drawable.star_on);
                    ButtonStar.setActivated(true);
                }

            }
        });

        ratingBar = (RatingBar) findViewById(R.id.star);

        if(rating>0){
            ratingBar.setRating(rating);
        }

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                if(rating<1.0f) {
                    rating=1.0f;
                    ratingBar.setRating(rating);
                }

                EditUserRating userRating = new EditUserRating();
                userRating.execute("" + id_user,"" + id_recipe,"" + rating);

                //Toast.makeText(getApplicationContext(), "Stars:" + rating, Toast.LENGTH_LONG).show();
            }
        });


        Button opinions = findViewById(R.id.btnOpinion);
        opinions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Recipe2DetailsActivity.this,OpinionsActivity.class);
                intent.putExtra("id_recipe",id_recipe);
                intent.putExtra("id_users",id_user);
                startActivity(intent);
            }
        });

    }


    private class EditFravoriteRecipe extends AsyncTask<String, String, JSONObject> {

        @Override

        protected void onPreExecute() {

            super.onPreExecute();

        }

        @Override

        protected JSONObject doInBackground(String... args) {


            String id_android_users= args[0];
            String id_recipe= args[1];

            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id_android_users", id_android_users));
            params.add(new BasicNameValuePair("id_recipe", id_recipe));

            JSONObject json = jsonParser.makeHttpRequest(urlAddress, "POST", params);


            return json;

        }

        protected void onPostExecute(JSONObject result) {

            // dismiss the dialog once product deleted
            //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();

            final ImageButton ButtonStar = (ImageButton) findViewById(R.id.favorite);

            try {
                if (result != null) {

                    //Toast.makeText(getApplicationContext(),result.getString("message"),Toast.LENGTH_LONG).show();

                    if(result.getString("message").equals("Dodano do ulubionych")){

                        //int id = result.getString("id");
                        //int id = Integer.parseInt(result.getString("id"));
                        Log.e("API4567","Dodano do ulubionych");

                        Toast.makeText(getApplicationContext(), "Dodano do ulubionych", Toast.LENGTH_LONG).show();

                        //ButtonStar.setImageResource(R.drawable.star_on);
                        ButtonStar.setActivated(true);


                    } else if (result.getString("message").equals("Skasowano z ulubionych")) {

                        Log.e("API4567", "Skasowano z ulubionych");

                        Toast.makeText(getApplicationContext(), "Usunięto z ulubionych", Toast.LENGTH_LONG).show();

                        //ButtonStar.setImageResource(R.drawable.star_off);
                        ButtonStar.setActivated(false);
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "Nie można dokonać zmiany w tym momencie", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }



    private class EditUserRating extends AsyncTask<String, String, JSONObject> {

        @Override

        protected void onPreExecute() {

            super.onPreExecute();

        }

        @Override

        protected JSONObject doInBackground(String... args) {


            String id_android_users = args[0];
            String id_recipe = args[1];
            String rating = args[2];

            Log.e("API45678","id_android_users:"+id_android_users);
            Log.e("API45678","id_recipe:"+id_recipe);
            Log.e("API45678","rating:"+rating);

            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id_android_users", id_android_users));
            params.add(new BasicNameValuePair("id_recipe", id_recipe));
            params.add(new BasicNameValuePair("rating", rating));

            JSONObject json = jsonParser.makeHttpRequest(urlAddress2, "POST", params);


            return json;

        }

        protected void onPostExecute(JSONObject result) {

            // dismiss the dialog once product deleted
            //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();

            final ImageButton ButtonStar = (ImageButton) findViewById(R.id.favorite);

            try {
                if (result != null) {

                    //Toast.makeText(getApplicationContext(),result.getString("message"),Toast.LENGTH_LONG).show();

                    if(result.getString("message").equals("Dodano ocene")){

                        //int id = result.getString("id");
                        //int id = Integer.parseInt(result.getString("id"));
                        Log.e("API4567","Dodano ocene");

                        Toast.makeText(getApplicationContext(), "Dodano ocene", Toast.LENGTH_LONG).show();

                        //ButtonStar.setImageResource(R.drawable.star_on);
                        ButtonStar.setActivated(true);


                    } else if (result.getString("message").equals("Zmieniono")) {

                        Log.e("API4567", "Zmieniono");

                        Toast.makeText(getApplicationContext(), "Zmieniono", Toast.LENGTH_LONG).show();

                        //ButtonStar.setImageResource(R.drawable.star_off);
                        ButtonStar.setActivated(false);
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
