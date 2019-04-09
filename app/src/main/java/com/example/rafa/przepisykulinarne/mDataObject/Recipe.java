package com.example.rafa.przepisykulinarne.mDataObject;

public class Recipe {

    int id;
    String name;
    String image;
    String description;
    String components;
    String way_of_preparation;
    String photo1;
    String photo2;
    String photo3;
    String URL_video;
    String opinion;
    String login;
    int id_favorite_recipes, rating;

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getId_favorite_recipes() {
        return id_favorite_recipes;
    }

    public void setId_favorite_recipes(int id_favorite_recipes) {
        this.id_favorite_recipes = id_favorite_recipes;
    }



    public String getComponents() {
        return components;
    }

    public void setComponents(String components) {
        this.components = components;
    }

    public String getWay_of_preparation() {
        return way_of_preparation;
    }

    public void setWay_of_preparation(String way_of_preparation) {
        this.way_of_preparation = way_of_preparation;
    }

    public String getPhoto1() {
        return photo1;
    }

    public void setPhoto1(String photo1) {
        this.photo1 = photo1;
    }

    public String getPhoto2() {
        return photo2;
    }

    public void setPhoto2(String photo2) {
        this.photo2 = photo2;
    }

    public String getPhoto3() {
        return photo3;
    }

    public void setPhoto3(String photo3) {
        this.photo3 = photo3;
    }

    public String getURL_video() {
        return URL_video;
    }

    public void setURL_video(String URL_video) {
        this.URL_video = URL_video;
    }


    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
