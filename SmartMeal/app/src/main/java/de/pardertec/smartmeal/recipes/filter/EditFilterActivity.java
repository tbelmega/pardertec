package de.pardertec.smartmeal.recipes.filter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.HashSet;
import java.util.List;

import de.pardertec.datamodel.Allergen;
import de.pardertec.datamodel.RecipeFilterBundle;
import de.pardertec.datamodel.VeganismStatus;
import de.pardertec.smartmeal.R;
import de.pardertec.smartmeal.SmartMealApplication;
import de.pardertec.smartmeal.main.SmartMealActivity;

public class EditFilterActivity extends AppCompatActivity
        implements RadioGroup.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {

    private RadioGroup veganStatusRadioGroup;
    private VeganismStatus selecedStatus = VeganismStatus.CONTAINS_MEAT;
    private ListView allergensList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_filter);

        veganStatusRadioGroup = (RadioGroup) findViewById(R.id.rg_vegan_status);
        veganStatusRadioGroup.setOnCheckedChangeListener(this);

        allergensList = (ListView) findViewById(R.id.allergens_list);
    }



    @Override
    protected void onResume() {
        super.onResume();
        VeganismStatus status = SmartMealApplication.getFilterBundle().getStatus();
        switch (status) {
            case CONTAINS_MEAT: findViewById(R.id.rb_none).setSelected(true); break;
            case VEGETARIAN: findViewById(R.id.rb_vegetarian).setSelected(true); break;
            case VEGAN: findViewById(R.id.rb_vegan).setSelected(true); break;
            default: throw new UnsupportedOperationException("Status option not implemented");
        }

        //TODO: move adapter to application
        SmartMealApplication.getFilterBundle().getAllergens().add(new Allergen("Muh"));
        List<Allergen> allergens = SmartMealApplication.getFilterBundle().getAllergens();
        if (!allergens.isEmpty()) {
            ArrayAdapter<Allergen> allergensListAdapter = new ArrayAdapter<>(this,
                    R.layout.simple_list_item, allergens);
            allergensList.setAdapter(allergensListAdapter);
            findViewById(R.id.no_allergens_excluded).setVisibility(View.GONE);
            allergensList.setVisibility(View.VISIBLE);
        } else {
            allergensList.setVisibility(View.GONE);
            findViewById(R.id.no_allergens_excluded).setVisibility(View.VISIBLE);
        }

        //TODO: No spinner, use button + new activity
        Spinner spinner = (Spinner) findViewById(R.id.allergens_spinner);
        List<Allergen> allAllergens = SmartMealApplication.getAllergens();
        ArrayAdapter<Allergen> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, allAllergens);
        spinner.setAdapter(adapter);
        spinner.setSelection(-1);

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
        //TODO reset allergens
        ((RadioButton)findViewById(R.id.rb_none)).setChecked(true);
    }

    public void saveFilters(View view) {
        //TODO read selected allergens
        SmartMealApplication.setFilterBundle(new RecipeFilterBundle(selecedStatus, new HashSet<Allergen>()));
        abort(view);
    }

    public void abort(View view) {
        Intent intent = new Intent(this, SmartMealActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Allergen a = (Allergen) parent.getItemAtPosition(position);
        SmartMealApplication.getFilterBundle().getAllergens().add(a);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
