package de.pardertec.smartmeal.recipes.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.pardertec.datamodel.Recipe;
import de.pardertec.smartmeal.R;

/**
 * Adapter Pattern.
 * Binds a java.util.List of Recipe to a RecyclerView.
 *
 * Created by Thiemo on 23.04.2016.
 */
public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {

    private final RecipeCardListener cardListener;
    private final List<Recipe> recipeList;
    private View recipeCardView;

    public RecipeListAdapter(RecipeCardListener cardListener, List<Recipe> recipeList) {
        this.cardListener = cardListener;
        this.recipeList = recipeList;
    }

    /**
     * Interface for the instance that reacts on clicked cards.
     * (That will probably be the containing activity.)
     */
    public interface RecipeCardListener {
        void cardClicked(Recipe r);
    }


    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        recipeCardView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_recipe, parent, false);
        return new RecipeViewHolder(recipeCardView);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        Recipe r = recipeList.get(position);
        holder.bindRecipe(r);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    /**
     * The RecipeViewHolder is the Card representation of a single Recipe.
     */
    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        private final TextView recipeTitleView;
        private final TextView recipeTextView;
        private Recipe recipe;


        public RecipeViewHolder(View view) {
            super(view);
            recipeTitleView =  (TextView) view.findViewById(R.id.recipe_title);
            recipeTextView = (TextView)  view.findViewById(R.id.recipe_text);
            view.setOnClickListener(createCardClicklistener());
        }

        /**
         * Notifies the CardListener object that this Recipe was clicked.
         * @return
         */
        @NonNull
        private View.OnClickListener createCardClicklistener() {
            return new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    cardListener.cardClicked(recipe);
                }
            };
        }

        /**
         * Place Recipe data on the Card.
         * @param recipe
         */
        public void bindRecipe(Recipe recipe) {
            this.recipe = recipe;
            recipeTitleView.setText(stringify(recipe.getName()));
            recipeTextView.setText(stringify(recipe.getText()));
        }

        /**
         * Util method to prevent a NullPointerException
         * @param string
         * @return
         */
        private String stringify(String string) {
            if (string == null) return "";
            return string;
        }
    }
}
