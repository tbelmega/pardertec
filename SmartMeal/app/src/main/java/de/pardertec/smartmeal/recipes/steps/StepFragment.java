package de.pardertec.smartmeal.recipes.steps;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.pardertec.datamodel.RecipeStep;
import de.pardertec.smartmeal.R;

/**
 * Created by Thiemo on 24.04.2016.
 */
public class StepFragment extends Fragment {
    private RecipeStep step;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_detail, container, false);
        TextView stepText = (TextView) view.findViewById(R.id.step_text);
        stepText.setText(step.getText());
        return view;
    }

    public static StepFragment bind(RecipeStep step) {
        StepFragment frag = new StepFragment();
        frag.step = step;
        return frag;
    }
}
