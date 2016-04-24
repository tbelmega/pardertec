package de.pardertec.smartmeal.recipes.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import de.pardertec.datamodel.Recipe;
import de.pardertec.datamodel.RecipeCollection;
import de.pardertec.smartmeal.R;
import de.pardertec.smartmeal.SmartMealApplication;

import static de.pardertec.smartmeal.SmartMealApplication.EXTRA_RECIPE_ID;

public class RecipeDetailActivity extends AppCompatActivity {

    Recipe currentRecipe;
    private TextView titleTextView;
    private TextView textTextView;
    private TextView durationTextView;
    private TextView difficultyTextView;
    private TextView servingsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        titleTextView = (TextView) findViewById(R.id.recipe_title);
        textTextView = (TextView) findViewById(R.id.recipe_text);
        durationTextView = (TextView) findViewById(R.id.duration);
        difficultyTextView = (TextView) findViewById(R.id.difficulty);
        servingsTextView = (TextView) findViewById(R.id.servings);
    }


    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        currentRecipe = load(intent.getStringExtra(EXTRA_RECIPE_ID));

        titleTextView.setText(currentRecipe.getName());
        textTextView.setText(currentRecipe.getText());
        durationTextView.setText(currentRecipe.getDuration() + " " +
                getResources().getString(R.string.minutes));
        servingsTextView.setText(
                getResources().getString(R.string.servings_for_persons).replace(
                        getResources().getString(R.string.placeholder),
                        currentRecipe.getServings().toString()));
        difficultyTextView.setText(currentRecipe.getDifficulty().toString());

        //show ingrdients

        //show steps

        //show image
    }

    private Recipe load(String id) {
        RecipeCollection collection = ((SmartMealApplication)getApplication()).getRecipeCollection();
        return collection.getRecipe(id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recipe_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
