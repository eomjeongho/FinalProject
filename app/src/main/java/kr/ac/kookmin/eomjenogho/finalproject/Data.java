package kr.ac.kookmin.eomjenogho.finalproject;

import java.io.Serializable;

public class Data implements Serializable {
    private String tag;
    private String story;
    private double lat;
    private double lng;

    public Data(String in_tag, String in_story, double in_lat, double in_lng){
        super();
        tag = in_tag;
        story = in_story;
        lat = in_lat;
        lng = in_lng;
    }

    public String getTag(){
        return tag;
    }

    public String getStory(){
        return story;
    }

    public double getLat(){
        return lat;
    }

    public double getLng(){
        return lng;
    }
}
