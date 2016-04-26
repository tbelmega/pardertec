package de.pardertec.smartmeal.main;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import de.pardertec.smartmeal.R;
import de.pardertec.smartmeal.recipes.filter.EditFilterActivity;
import de.pardertec.smartmeal.recipes.list.RecipeListActivity;

public class SmartMealActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_meal);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeSearchView();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_smartmeal, menu);
        return true;
    }

    public void showSearchFilterActivity(MenuItem item) {
        Intent intent = new Intent(this, EditFilterActivity.class);
        startActivity(intent);
    }


    private void initializeSearchView() {
        SearchView searchView = (SearchView) findViewById(R.id.search_view);
        searchView.setIconifiedByDefault(false);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        ComponentName searchResultActivityName = new ComponentName(this, RecipeListActivity.class);
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(searchResultActivityName);
        searchView.setSearchableInfo(
                searchableInfo);
    }
}
