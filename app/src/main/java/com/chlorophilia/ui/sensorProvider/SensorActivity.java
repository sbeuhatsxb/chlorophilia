package com.chlorophilia.ui.sensorProvider;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chlorophilia.R;

import static java.lang.Math.abs;

public class SensorActivity extends Activity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor light;
    int plantNeededLight;
    TextView textSunStatus;
    TextView noLightInfo;
    LinearLayout background;

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_measure);

        plantNeededLight = (int) getIntent().getExtras().get("plantLight");
        // Get an instance of the sensor service, and use that to get an instance of
        // a particular sensor.
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        noLightInfo = findViewById(R.id.noLightInfoText);

    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    /*
    Here we compare the recorded light value of the plant to a "grade" given by the light measurement.
    If the light measure cursor moves too far from the ideal light for this plant, an info is sent to the user.
    The ideal enlightment also makes the phone vibrating.
    The background color instead always represent the amount of light perceived by the light sensor with a specific color
     */
    @Override
    public final void onSensorChanged(SensorEvent event) {
        int measureOfLight = (int) abs(event.values[0]);
        // Do something with this sensor data.
        System.out.println(measureOfLight + " / " + plantNeededLight);
        textSunStatus = findViewById(R.id.textSunStatus);
        background = findViewById(R.id.sunBackground);

        //Color mode
        //Decrease from blue to red
        //This array represents a HSL scale for a HUE from blue (240) to red (0)
        //The background color is set according to  240  minus (since it's decreasing from blue to red in HSL) the given light
        //Not sure its working : third parameter is a %age of brightness...
        float[] arrayBg = {(240 - abs(measureOfLight / 1000 * 2)), 100, (100*abs(measureOfLight))/120000};

        background.setBackgroundColor(Color.HSVToColor(arrayBg));
        textSunStatus.setTextColor(Color.parseColor("white"));

        if (plantNeededLight != -1) {
            //We've got a light measure for this plant
            noLightInfo.setVisibility(View.INVISIBLE);
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            //Almost no light
            if (isBetween(measureOfLight, 0, 39)) {
                textSunStatus.setText(getResources().getString(R.string.lightNull));
            } else if (measureOfLight > 120001) {
                //More light than the sun is capable of
                textSunStatus.setText(getResources().getString(R.string.lightOverload));
            } else {
                //In between
                int grade = getGrade(measureOfLight);
                int value = plantNeededLight - grade;
                if (value == 0) {
                    textSunStatus.setText(getResources().getString(R.string.lightIdeal));
                    v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                }
                if (value == -1) {
                    //a bit too much sun
                    textSunStatus.setText(getResources().getString(R.string.lightMinusOne));
                }
                if (value == -2) {
                    //too much sun
                    textSunStatus.setText(getResources().getString(R.string.lightMinusTwo));
                }
                if (value == -3) {
                    //definitely too much sun
                    textSunStatus.setText(getResources().getString(R.string.lightMinusThree));
                }
                if (value < -3) {
                    //overeating
                    textSunStatus.setText(getResources().getString(R.string.lightMinusFour));
                }
                if (value == 1) {
                    //a bit too shady
                    textSunStatus.setText(getResources().getString(R.string.lightPlusOne));
                }
                if (value == 2) {
                    //not enough sunlight
                    textSunStatus.setText(getResources().getString(R.string.lightPlusTwo));
                }
                if (value == 3) {
                    //definitely not enough light
                    textSunStatus.setText(getResources().getString(R.string.lightPlusThree));
                }
                if (value > 3) {
                    //total darkness
                    textSunStatus.setText(getResources().getString(R.string.lightPlusFour));
                }
            }

        } else {
            //we don't have light indication for this plant
            noLightInfo.setVisibility(View.VISIBLE);
            noLightInfo.setTextColor(Color.parseColor("black"));
            int value = getGrade(measureOfLight);
            if (value == 0) {
                //total darkness
                textSunStatus.setText(getResources().getString(R.string.noiInfoLightZero));
            }
            if (value == 1) {
                //a bit too shady
                textSunStatus.setText(getResources().getString(R.string.noiInfoLightOne));
            }
            if (value == 2) {
                //not enough sunlight
                textSunStatus.setText(getResources().getString(R.string.noiInfoLightTwo));
            }
            if (value == 3) {
                //definitely not enough light
                textSunStatus.setText(getResources().getString(R.string.noiInfoLightThree));
            }
            if (value == 4) {
                //total darkness
                textSunStatus.setText(getResources().getString(R.string.noiInfoLightFour));
            }
            if (value >= 5) {
                //total darkness
                textSunStatus.setText(getResources().getString(R.string.noiInfoLightFive));
            }
        }
    }

    @Override
    protected void onResume() {
        // Register a listener for the sensor.
        super.onResume();
        sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    private static boolean isBetween(int x, int lower, int upper) {
        return lower <= x && x <= upper;
    }

    /*
    Return a grade according to light measure
     */
    private static int getGrade(int measureOfLight) {
        int grade = 0;

        if (isBetween(measureOfLight, 40, 375)) {
            grade = 1;
        } else if (isBetween(measureOfLight, 376, 1000)) {
            grade = 2;
        } else if (isBetween(measureOfLight, 1001, 2500)) {
            grade = 3;
        } else if (isBetween(measureOfLight, 2501, 10000)) {
            grade = 4;
        } else if (isBetween(measureOfLight, 10001, 25000)) {
            grade = 5;
        } else if (isBetween(measureOfLight, 25001, 50000)) {
            grade = 6;
        } else if (isBetween(measureOfLight, 50001, 80000)) {
            grade = 7;
        } else if (isBetween(measureOfLight, 80001, 100000)) {
            grade = 8;
        } else if (isBetween(measureOfLight, 100001, 110000)) {
            grade = 9;
        } else if (isBetween(measureOfLight, 110001, 120000)) {
            grade = 10;
        }
        return grade;
    }

}
