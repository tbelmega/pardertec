package de.pardertec.smartmeal.recipes.detail;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.pardertec.smartmeal.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class IngredientsListFragment extends Fragment {

    public IngredientsListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ingredients_list, container, false);
    }
}
