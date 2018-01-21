package com.olprog.movies.movies;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class MoviesController {

    private static final String URL_GET_MOVIES_DB = "https://api.themoviedb.org/3/movie/top_rated?api_key=71b5b7777a42d6d1f3cc478ca687c0e0&language=en-US&page=1";

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String getJSONMoviesDb() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(URL_GET_MOVIES_DB)
                .build();

        Response response = client.newCall(request).execute();
        return jsonToObject(response.body().string());
    }

    public String jsonToObject(String jsonData){
        JSONObject json = (JSONObject) JSONValue.parse(jsonData);
        JSONArray movies = (JSONArray) json.get("results");
        for (Object movie:movies) {
            JSONObject j = (JSONObject) JSONValue.parse(movie.toString());
            return j.get("original_title").toString();
        }
        return null;
    }

}
