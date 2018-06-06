package com.personal_project.minami.whatstheweatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init
        final EditText input = findViewById(R.id.input_city);
        Property property = new Property(this);
        final String API_KEY = property.getApiKey();
        Button button = findViewById(R.id.btn);
        final TextView tv = findViewById(R.id.tv);
        final TextView descript = findViewById(R.id.description);


        // check if input is valid when button is clicked
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String regex = "^[a-zA-Z]+$";
                String input_city = input.getText().toString();
                Log.i(TAG, "onClick: -------------> " + input_city);
                if (input_city.matches(regex)){
                    try {
                        String cityName = URLEncoder.encode(input_city, "UTF-8");
                        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&APPID=" + API_KEY;
                        DownloadTask task = new DownloadTask(tv, descript);
                        task.execute(url);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(view.getContext(), "Input valid city name.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}
