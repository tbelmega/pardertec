package de.pardertec.smartmeal.recipes.steps;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import de.pardertec.datamodel.RecipeStep;

/**
 * Created by Thiemo on 24.04.2016.
 */
public class StepPageAdapter extends FragmentPagerAdapter {
    private final List<RecipeStep> steps;

    public StepPageAdapter(FragmentManager fm, List<RecipeStep> steps) {
        super(fm);
        this.steps = steps;
    }

    @Override
    public Fragment getItem(int position) {
        return StepFragment.bind(steps.get(position));
    }

    @Override
    public int getCount() {
        return steps.size();
    }
}
