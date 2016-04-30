package de.pardertec.smartmeal.recipes.list;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

import de.pardertec.datamodel.Recipe;
import de.pardertec.smartmeal.R;
import de.pardertec.smartmeal.SmartMealApplication;
import de.pardertec.smartmeal.recipes.detail.RecipeDetailActivity;

import static de.pardertec.smartmeal.SmartMealApplication.EXTRA_RECIPE_ID;
import static de.pardertec.smartmeal.SmartMealApplication.TAG;

public class RecipeListActivity extends AppCompatActivity implements RecipeListAdapter.RecipeCardListener {

    List<Recipe> recipes = new LinkedList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();

        recipes.clear();
        String query = getQueryString();
        recipes.addAll(SmartMealApplication.getFilteredRecipes(query));

        showToastIfNoResults();
    }

    private void showToastIfNoResults() {
        if (recipes.isEmpty()) {
            String string = getString(R.string.no_search_results);
            Toast.makeText(this, string, Toast.LENGTH_LONG).show();
        }
    }

    @Nullable
    private String getQueryString() {
        String query = null;
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);
            Log.i(TAG, "Search query: " + query);
        }
        return query;
    }

    @Override
    public void cardClicked(Recipe r) {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra(EXTRA_RECIPE_ID, r.getId());
        startActivity(intent);
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }
}
