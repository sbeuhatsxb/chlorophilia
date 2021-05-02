package com.chlorophilia.ui.dialogs;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import com.chlorophilia.R;

import java.util.ArrayList;

public class MyPlantsEditMonthsDialog  extends AppCompatActivity {

    ArrayList<String> monthsList = new ArrayList();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_months_select);

    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()) {
            case R.id.january:
                if (checked){
                    monthsList.add("jan");
                }
                break;
            case R.id.february:
                if (checked){
                    monthsList.add("feb");
                }
                break;
            case R.id.march:
                if (checked){
                    monthsList.add("mar");
                }
                break;
            case R.id.april:
                if (checked){
                    monthsList.add("apr");
                }
                break;
            case R.id.may:
                if (checked){
                    monthsList.add("may");
                }
                break;
            case R.id.june:
                if (checked){
                    monthsList.add("jun");
                }
                break;
            case R.id.july:
                if (checked){
                    monthsList.add("jul");
                }
                break;
            case R.id.august:
                if (checked){
                    monthsList.add("aug");
                }
                break;
            case R.id.september:
                if (checked){
                    monthsList.add("sep");
                }
                break;
            case R.id.october:
                if (checked){
                    monthsList.add("oct");
                }
                break;
            case R.id.november:
                if (checked){
                    monthsList.add("nov");
                }
                break;
            case R.id.december:
                if (checked){
                    monthsList.add("dec");
                }
                break;
        }
    }
}
