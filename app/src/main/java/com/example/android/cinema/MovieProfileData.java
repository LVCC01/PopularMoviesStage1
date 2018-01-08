package com.example.android.cinema;

/**
 * Created by hjadhav on 4/21/2017.
 */

public class MovieProfileData {
    String posterUrl;
    String titleText;
    String plotText;
    String ratingText;
    String releaseDateText;

    //Public constructor
    public MovieProfileData (String posterUrl , String titleText, String plotText, String ratingText, String releaseDateText ){
        this.posterUrl = posterUrl;
        this.titleText = titleText;
        this.plotText = plotText;
        this.ratingText = ratingText;
        this.releaseDateText = releaseDateText;
    }
    //Public constructor
    public MovieProfileData(){

    }
    // Getter for posterUrl
    public String getPosterUrl(){
        return posterUrl;
    }
    // Setter for posterUrl
    public void setPosterUrl(String posterUrl){
        this.posterUrl = posterUrl;
    }
    // Getter for titleText
    public String getTitleText(){
        return titleText;
    }
    // Setter for titleText
    public void setTitleText(String titleText){
        this.titleText = titleText;
    }
    // Getter for plotText
    public String getPlotText(){
        return plotText;
    }
    // Setter for plotText
    public void setPlotText(String plotText){
        this.plotText = plotText;
    }
    // Getter for ratingText
    public String getRatingText(){
        return ratingText;
    }
    // Setter for ratingText
    public void setRatingText(String ratingText){
        this.ratingText = ratingText;
    }
    // Getter for releaseDateText
    public String getReleaseDateText(){
        return releaseDateText;
    }
    // Setter for releaseDateText
    public void setReleaseDateText(String releaseDateText){
        this.releaseDateText = releaseDateText;
    }
}
