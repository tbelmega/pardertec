package de.pardertec.smartmeal.recipes.steps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import de.pardertec.datamodel.Recipe;
import de.pardertec.smartmeal.R;
import static de.pardertec.smartmeal.SmartMealApplication.*;
import static de.pardertec.smartmeal.utils.RecipeUtil.loadRecipe;

public class StepSwipeActivity extends FragmentActivity {

    private Recipe selectedRecipe;

    private ViewPager stepPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_swipe);

        stepPager = (ViewPager) findViewById(R.id.step_pager);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        selectedRecipe = loadRecipe(intent.getStringExtra(EXTRA_RECIPE_ID), getApplication());

        PagerAdapter adapter = new StepPageAdapter(getSupportFragmentManager(), selectedRecipe.getStepsCopy());
        stepPager.setAdapter(adapter);
    }
}
