package de.pardertec.smartmeal.recipes.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import de.pardertec.datamodel.Ingredient;
import de.pardertec.datamodel.Recipe;
import de.pardertec.datamodel.RecipeCollection;
import de.pardertec.datamodel.RecipeStep;
import de.pardertec.smartmeal.R;
import de.pardertec.smartmeal.SmartMealApplication;

import static de.pardertec.smartmeal.SmartMealApplication.EXTRA_RECIPE_ID;

public class RecipeDetailActivity extends AppCompatActivity {

    Recipe currentRecipe;
    private RecipeDetailActivityFragment ingredientsDetailFragment;

    private TextView titleTextView;
    private TextView textTextView;
    private TextView durationTextView;
    private TextView difficultyTextView;
    private TextView servingsTextView;

    private ListView ingredientsList;
    private ListView stepList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ingredientsDetailFragment = (RecipeDetailActivityFragment) getFragmentManager().
                findFragmentById(R.id.recipe_detail_fragment);

        titleTextView = (TextView) findViewById(R.id.recipe_title);
        textTextView = (TextView) findViewById(R.id.recipe_text);
        durationTextView = (TextView) findViewById(R.id.duration);
        difficultyTextView = (TextView) findViewById(R.id.difficulty);
        servingsTextView = (TextView) findViewById(R.id.servings);

        ingredientsList = (ListView) findViewById(R.id.ingredients_list);
        stepList= (ListView) findViewById(R.id.steps_list);
    }


    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        currentRecipe = loadRecipe(intent.getStringExtra(EXTRA_RECIPE_ID));

        titleTextView.setText(currentRecipe.getName());
        textTextView.setText(currentRecipe.getText());
        durationTextView.setText(currentRecipe.getDuration() + " " +
                getResources().getString(R.string.minutes));
        servingsTextView.setText(createServingsText(currentRecipe));
        difficultyTextView.setText(currentRecipe.getDifficulty().toString());

        //ingredients list
        List<IngredientEntry> ingredients = getIngredientEntryList(currentRecipe);
        ArrayAdapter<IngredientEntry> ingredientEntryArrayAdapter = new ArrayAdapter<>(this,
                R.layout.simple_list_item, ingredients);
        ingredientsList.setAdapter(ingredientEntryArrayAdapter);
        ViewGroup layout = (ViewGroup) findViewById(R.id.scrollview_content);
        layout.requestLayout();

        /*
         * Horrible hack: in order to calculate the needed height for the list views,
         * I take naively 80% of the screen size as width
         */
        int width = (int) (getWindowManager().getDefaultDisplay().getWidth() * 0.8);
        fixListViewHeightBasedOnChildren(ingredientsList, width);

        //steps list
        List<RecipeStep> steps = currentRecipe.getStepsCopy();
        ArrayAdapter<RecipeStep> stepArrayAdapter = new ArrayAdapter<>(this,
                R.layout.simple_list_item, steps);
        stepList.setAdapter(stepArrayAdapter);
        fixListViewHeightBasedOnChildren(stepList, width);
        RelativeLayout.LayoutParams stepListParams = (RelativeLayout.LayoutParams) stepList.getLayoutParams();
        stepListParams.addRule(RelativeLayout.BELOW, R.id.steps_header);
        stepList.setLayoutParams(stepListParams);

        //show image
    }


    private Recipe loadRecipe(String id) {
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

    /**
     * Replace the PLACEHOLDER within the textSkeleton by the actual number of servings.
     */
    private String createServingsText(Recipe currentRecipe) {
        String textSkeleton = getResources().getString(R.string.servings_for_persons);
        String placeholder = getResources().getString(R.string.placeholder);
        String numberOfServings = currentRecipe.getServings().toString();
        return textSkeleton.replace(placeholder, numberOfServings);
    }


    private List<IngredientEntry> getIngredientEntryList(Recipe currentRecipe) {
        List<IngredientEntry> ingredients = new LinkedList<>();

        for (Ingredient ingredient: currentRecipe.getIngredients().keySet()) {
            Integer amount = currentRecipe.getIngredients().get(ingredient);
            ingredients.add(new IngredientEntry(ingredient, amount));
        }
        return ingredients;
    }


    private static void fixListViewHeightBasedOnChildren(ListView listView, int width) {
        listView.setEnabled(false);
        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = calcListViewHeightBasedOnChildren(listView,  width) + (listView.getDividerHeight() * (listView.getAdapter().getCount() - 1));
        listView.setLayoutParams(par);
        listView.requestLayout();
    }

    private static int calcListViewHeightBasedOnChildren(ListView listView, int width) {
        ListAdapter adapter = listView.getAdapter();
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.AT_MOST);

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }
        return totalHeight;
    }


    private class IngredientEntry {
        private final Ingredient ingredient;
        private final Integer amount;

        public IngredientEntry(Ingredient ingredient, Integer amount) {
            this.ingredient = ingredient;
            this.amount = amount;
        }

        @Override
        public String toString() {
            return  ingredient.getName()+ ": " + amount + " " + ingredient.getMeasure();
        }
    }
}
