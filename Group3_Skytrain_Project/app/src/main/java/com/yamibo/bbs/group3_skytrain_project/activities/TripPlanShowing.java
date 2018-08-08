package com.yamibo.bbs.group3_skytrain_project.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import com.yamibo.bbs.group3_skytrain_project.R;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;

public class TripPlanShowing extends LocationActivity {
    TextView cityField, detailsField, currentTemperatureField,
            humidity_field, pressure_field, weatherIcon, updatedField;
    Typeface weatherFont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_plan_showing);
        TextView  text8 = findViewById(R.id.textView8);
        TextView  text9 = findViewById(R.id.textView9);
TextView quest = findViewById(R.id.question);
        weatherFont = Typeface.createFromAsset(getAssets(), "fonts/weathericons-regular-webfont.ttf");
        cityField = (TextView)findViewById(R.id.city_field);
        updatedField = (TextView)findViewById(R.id.updated_field);
        detailsField = (TextView)findViewById(R.id.details_field);
        currentTemperatureField = (TextView)findViewById(R.id.current_temperature_field);
        humidity_field = (TextView)findViewById(R.id.humidity_field);
        pressure_field = (TextView)findViewById(R.id.pressure_field);
        weatherIcon = (TextView)findViewById(R.id.weather_icon);
        weatherIcon.setTypeface(weatherFont);

        WeatherFunction.placeIdTask asyncTask =new WeatherFunction.placeIdTask(new WeatherFunction.AsyncResponse() {
            public void processFinish(String weather_city, String weather_description, String weather_temperature, String weather_humidity, String weather_pressure, String weather_updatedOn, String weather_iconText, String sun_rise) {
                Bundle extras = getIntent().getExtras();
                if(extras != null){
                    String value = extras.getString("Message1");
                    String valuee = extras.getString("Message2");
                    text8.setText(value);
                    text9.setText(valuee);
                  weather_city = valuee;
                }
                cityField.setText(weather_city);
                updatedField.setText(weather_updatedOn);
                detailsField.setText(weather_description);
                currentTemperatureField.setText(weather_temperature);
                humidity_field.setText("Humidity: "+weather_humidity);
                pressure_field.setText("Pressure: "+weather_pressure);
                weatherIcon.setText(Html.fromHtml(weather_iconText));
                         quest.setText("Time For a good trip, Book a ticket ???" );
            }
        });


        asyncTask.execute("49.255518", "-123.120136");








    }
}
