package com.chlorophilia.ui.fragmentSearch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chlorophilia.R;
import com.chlorophilia.ui.entities.Plant;
import com.chlorophilia.ui.fragmentMyPlants.MyPlantsShowDetails;
import com.chlorophilia.ui.model.PlantDataHandler;


/**
 * Name your plant !
 */
public class NicknameActivity extends AppCompatActivity {

    private EditText nicknameInput = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nickname_plant);

        Plant plant = (Plant) getIntent().getSerializableExtra("plant");
        PlantDataHandler db = new PlantDataHandler(this);

        nicknameInput = findViewById(R.id.nicknameInput);
        Button validate = findViewById(R.id.done);
        Button cancel = findViewById(R.id.no_thanks);

        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickname = nicknameInput.getText().toString();
                if (!nickname.equals("")) {
                    plant.setNickname(nickname);
                    long insertedId = db.insertInto(plant);
                    plant.setId(insertedId);
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.nicknameAdded), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), MyPlantsShowDetails.class);
                    intent.putExtra("plant", plant);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.nicknameInfo), Toast.LENGTH_LONG).show();
                }
            }
        });

        //Cancel
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = plant.getScientific_name();
                plant.setNickname(name);
                long insertedId = db.insertInto(plant);
                plant.setId(insertedId);
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.nicknameAdded), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), MyPlantsShowDetails.class);
                intent.putExtra("plant", plant);
                startActivity(intent);
                finish();
            }
        });
    }

}

