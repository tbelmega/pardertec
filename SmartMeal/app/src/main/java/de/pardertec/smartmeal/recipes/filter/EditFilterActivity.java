package de.pardertec.smartmeal.recipes.filter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;

import java.util.HashSet;

import de.pardertec.datamodel.Allergen;
import de.pardertec.datamodel.RecipeFilterBundle;
import de.pardertec.datamodel.VeganismStatus;
import de.pardertec.smartmeal.R;
import de.pardertec.smartmeal.SmartMealApplication;
import de.pardertec.smartmeal.main.SmartMealActivity;

public class EditFilterActivity extends AppCompatActivity
        implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup veganStatusRadioGroup;
    private VeganismStatus selecedStatus = VeganismStatus.CONTAINS_MEAT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_filter);

        veganStatusRadioGroup = (RadioGroup) findViewById(R.id.rg_vegan_status);
        veganStatusRadioGroup.setOnCheckedChangeListener(this);
    }


    public void abort(View view) {
        Intent intent = new Intent(this, SmartMealActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        //TODO: display data from Application filter bundle
        super.onResume();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case (R.id.rb_none): selecedStatus = VeganismStatus.CONTAINS_MEAT; break;
            case (R.id.rb_vegetarian): selecedStatus = VeganismStatus.VEGETARIAN; break;
            case (R.id.rb_vegan): selecedStatus = VeganismStatus.VEGAN; break;
            default: throw new UnsupportedOperationException("Unexpected item selected");
        }
    }

    public void resetFilters(View view) {
        SmartMealApplication.setFilterBundle(new RecipeFilterBundle());
        //TODO: show message
    }

    public void saveFilters(View view) {
        //TODO read selected allergens
        SmartMealApplication.setFilterBundle(new RecipeFilterBundle(selecedStatus, new HashSet<Allergen>()));
        abort(view);
    }
}
