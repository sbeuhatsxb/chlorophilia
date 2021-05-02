package com.chlorophilia;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.chlorophilia.ui.dialogs.MyPlantsShowDialog;
import com.chlorophilia.ui.model.PlantDataHandler;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PlantDataHandler db = new PlantDataHandler(this);
        if (db.getPlants().size() == 0) {
            Intent intent = new Intent(getApplicationContext(), MyPlantsShowDialog.class);
            intent.putExtra("message", getResources().getString(R.string.home_first_time));
            startActivity(intent);
        }

        //Message sent to user from other activity when returning here
        if (getIntent().hasExtra("message")) {
            String message = (String) getIntent().getExtras().get("message");
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }

        //TODO : RECREATE DB -- HERE DEBUG START BEGIN
        //db.destroyAndRecreate();

        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

    }

}