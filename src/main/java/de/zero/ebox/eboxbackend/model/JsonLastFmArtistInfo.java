package de.zero.ebox.eboxbackend.model;

import java.util.ArrayList;
import java.util.List;

public class JsonLastFmArtistInfo {

    private String name;

    private String mbid;

    private String url;

    private List<JsonLastFmImage> image;

    private JsonSimilarDjs similar;

    private JsonDjBio bio;

    public static class JsonSimilarDjs {
        public List<JsonLastFmArtistInfo> artist = new ArrayList<>();
    }

    public static class JsonDjBio {
        public String summary;

        public String content;
    }

}
