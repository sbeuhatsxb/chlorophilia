package com.chlorophilia.ui.apiProvider;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Using okhttp3 to call //deprecated trefle.io API --> MY BRAND NEW REST API !!
 */
public class ApiInstance extends AppCompatActivity {

    //Object lock dedicated to wait API response before dealing with the response
    private final Object lock = new Object();
    private final OkHttpClient client = new OkHttpClient();
    private String url = "http://trefle.osc-fr1.scalingo.io";
    private String apiVersion = "/api/v1";
    private String species = "/species";
    private String plants = "/plants/";
    private String token;
    //Param sent to query "a plant" to the api
    private String responseString;


    public void setToken(String token) {
        this.token = "/token=" + token;
    }

    /**
     * This method calls for a list from REST API. Param represents which plant-slug will be sent to the API
     *
     * @param param
     * @return
     * @throws Exception
     */
    public String getPlantListFromSlug(String param) throws Exception {
        responseString = "";
        //Building URI
        Request request = new Request.Builder()
                .url(url + apiVersion + species + token + "/q=" + param)
                .build();
        //Running api
        this.run(request);
        //Waiting for API to send a response for two seconds
        synchronized (lock) {
            lock.wait(2000);
        }
        return responseString;
    }

    /**
     * This method calls for a specie from REST API. Param represents which plant-id will be sent to the API
     *
     * @param id
     * @return
     * @throws Exception
     */
    public String getPlantFromId(int id) throws Exception {
        //Building URI
        Request request = new Request.Builder()
                .url(url + apiVersion + plants + id)
                .build();
        //Running api
        this.run(request);
        //Waiting for API to send a response for five seconds
        synchronized (lock) {
            while (responseString == null) {
                lock.wait(80000);
            }
            return responseString;
        }
    }

    /**
     * @throws Exception
     */
    public void run(Request request) {

        //Enqueuing in a thread the request to the API
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (response.code() == 503 || response.code() == 500) {
                        return;
                    }
                    if (!response.isSuccessful()) {
                        return;
                    }

                    synchronized (lock) {
                        //okhttp doesn't manage to send the body directly, had to send it as a String
                        responseString = response.body().string();
                        lock.notify();
                    }
                }
            }
        });
    }


}

