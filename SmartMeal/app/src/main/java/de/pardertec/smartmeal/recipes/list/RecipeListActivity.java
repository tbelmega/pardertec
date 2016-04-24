package de.pardertec.smartmeal.recipes.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import de.pardertec.datamodel.Recipe;
import de.pardertec.smartmeal.R;
import de.pardertec.smartmeal.recipes.detail.RecipeDetailActivity;

import static de.pardertec.smartmeal.SmartMealApplication.*;

public class RecipeListActivity extends AppCompatActivity implements RecipeListAdapter.RecipeCardListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public void cardClicked(Recipe r) {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra(EXTRA_RECIPE_ID, r.getId());
        startActivity(intent);
    }
}
