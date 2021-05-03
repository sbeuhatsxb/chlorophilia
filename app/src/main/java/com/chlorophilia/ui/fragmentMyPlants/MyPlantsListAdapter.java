package com.chlorophilia.ui.fragmentMyPlants;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chlorophilia.R;
import com.chlorophilia.ui.entities.Plant;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Custom adapter dedicated to display a list of plant with some info into the home fragment scrolling view.
 */
public class MyPlantsListAdapter extends ArrayAdapter<Plant> implements View.OnClickListener {
    private ArrayList<Plant> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView nickname;
        TextView commonName;
        ImageView img;
    }

    public MyPlantsListAdapter(ArrayList<Plant> data, Context context) {
        super(context, R.layout.activity_main_row_item, data);
        this.dataSet = data;
        this.mContext = context;
    }

    @Override
    public void onClick(View v) {

        int position = (Integer) v.getTag();
        Object object = getItem(position);
        Plant plant = (Plant) object;
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, MMM d, ''yy");

        switch (v.getId()) {
            case R.id.item_img:
                Snackbar.make(v, "Plant added the " + formatter.format(plant.getCreatedAt()), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;
        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Plant plant = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.activity_main_row_item, parent, false);
            viewHolder.nickname = (TextView) convertView.findViewById(R.id.nickname);
            viewHolder.commonName = (TextView) convertView.findViewById(R.id.common_name);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.item_img);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.nickname.setText(plant.getNickname());
        viewHolder.commonName.setText(plant.getCommon_name());

        if (plant.getFilenameCustomPicture() != null) {
            try {
                File f = new File(plant.getFilenameCustomPicture());
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                viewHolder.img.setImageBitmap(b);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            if (plant.getImg() != 0) {
                viewHolder.img.setImageResource(plant.getImg());
            }
        }
        viewHolder.img.setOnClickListener(this);
        viewHolder.img.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }

}
