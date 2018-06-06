package com.personal_project.minami.whatstheweatherapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import static android.content.ContentValues.TAG;

/**
 * Created by Minami on 2018-06-05.
 */

public class DownloadTask extends AsyncTask<String, Void, String> {

    private TextView tv;
    private TextView descript;

    public DownloadTask(TextView tv, TextView description) {
        this.tv = tv;
        this.descript = description;
    }

    @Override
    protected String doInBackground(String... urls) {
        String result = "";
        URL url;
        HttpURLConnection connection;
        String data = "";

        try {
            url = new URL(urls[0]);
            connection = (HttpURLConnection) url.openConnection();
            InputStream in = connection.getInputStream();
            Scanner scanner = new Scanner(in);
            while (scanner.hasNextLine()){
                data += scanner.nextLine();
            }
            Log.i(TAG, "doInBackground: " + data);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    protected void onPostExecute(String data) {
        super.onPostExecute(data);
        JSONObject jsonObject = null;
        String city = null;
        String country = null;
        String main = null;
        String description = null;

        try {
            jsonObject = new JSONObject(data);
            String weatherInfo = jsonObject.getString("weather");
            city = jsonObject.getString("name");
            String sysInfo = jsonObject.getString("sys");
            JSONObject jsonObject1_sys = new JSONObject(sysInfo);
            country = jsonObject1_sys.getString("country");
            JSONArray weatherArray = new JSONArray(weatherInfo);
            JSONObject weather = weatherArray.getJSONObject(0);
            main = weather.getString("main");
            description = weather.getString("description");
            tv.setText("Current weather in\n" + city + ", " + country);
            descript.setText(main + ": " + description);

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }
}
