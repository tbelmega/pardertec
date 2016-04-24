package de.pardertec.smartmeal.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import de.pardertec.smartmeal.R;
import de.pardertec.smartmeal.recipes.filter.SelectFilterActivity;

public class SmartMealActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_meal);
    }

    public void openFilterSelection(View view) {
        Intent intent = new Intent(this, SelectFilterActivity.class);
        startActivity(intent);
    }
}
