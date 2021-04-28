package com.chlorophilia.ui.fragmentMyPlants;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.chlorophilia.MainActivity;
import com.chlorophilia.R;
import com.chlorophilia.ui.entities.Plant;
import com.chlorophilia.ui.model.PlantDataHandler;

public class MyPlantsRemove extends AppCompatActivity {

    Plant plant;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_plant);
        PlantDataHandler db = new PlantDataHandler(getApplicationContext());

        plant = (Plant) getIntent().getSerializableExtra("plant");

        Button yes = findViewById(R.id.yesRemove);
        Button no = findViewById(R.id.noRemove);
        TextView removeText = findViewById(R.id.removeText);
        removeText.setText("Are you sure you want to remove " + plant.getNickname() + " from your list ?");

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long id = plant.getId();
                db.deletePlant(id);

                new MyPlantsViewListAdapter(db.getPlants(), getApplicationContext());
                Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                startIntent.putExtra("message", plant.getNickname() + " is no more !");
                startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startIntent);
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
