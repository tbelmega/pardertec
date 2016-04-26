package de.pardertec.smartmeal.recipes.filter;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.SearchView;

import de.pardertec.smartmeal.R;
import de.pardertec.smartmeal.main.SmartMealActivity;
import de.pardertec.smartmeal.recipes.list.RecipeListActivity;

public class SelectFilterActivity extends AppCompatActivity
        implements RadioGroup.OnCheckedChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_filter);

        RadioGroup veganStatusRadioGroup = (RadioGroup) findViewById(R.id.rg_vegan_status);
        veganStatusRadioGroup.setOnCheckedChangeListener(this);
    }



    public void startSearch(View view) {
        Intent intent = new Intent(this, RecipeListActivity.class);
        startActivity(intent);
    }

    public void abort(View view) {
        Intent intent = new Intent(this, SmartMealActivity.class);
        startActivity(intent);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

    }
}
