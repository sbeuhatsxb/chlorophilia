package com.chlorophilia.ui.dialogs;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;

import com.chlorophilia.R;

public class MyPlantsShowDialog extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Has to be set before the setContentLayout
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(this, R.drawable.stat_notify_more_custom);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, Color.parseColor("#FF669900"));

        setContentView(R.layout.activity_init);

        String message = (String) getIntent().getExtras().get("message");
        TextView messageBox = findViewById(R.id.dialogMessage);
        messageBox.setText(message);

        Button button = findViewById(R.id.dialogButtonOk);
        button.setText(getResources().getString(R.string.gotit));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}