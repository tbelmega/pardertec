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
public class StepsListFragment extends Fragment {

    public StepsListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_steps_list, container, false);
    }
}
