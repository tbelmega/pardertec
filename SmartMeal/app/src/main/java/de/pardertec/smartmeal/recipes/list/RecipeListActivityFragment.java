package de.pardertec.smartmeal.recipes.list;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

import de.pardertec.datamodel.Recipe;
import de.pardertec.smartmeal.R;
import de.pardertec.smartmeal.SmartMealApplication;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecipeListActivityFragment extends Fragment {


    private RecyclerView recipeList;
    private RecipeListActivity activityCommander;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.activityCommander = (RecipeListActivity) activity;
        } catch (ClassCastException e) {
            Log.e(SmartMealApplication.TAG, "The attached activity is supposed to implement RecipeCardListener.");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recipeList = (RecyclerView) getActivity().findViewById(R.id.recipeList);
        recipeList.setHasFixedSize(false);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recipeList.setLayoutManager(llm);
        recipeList.setAdapter(new RecipeListAdapter(activityCommander, activityCommander.getRecipes()));
    }

}
