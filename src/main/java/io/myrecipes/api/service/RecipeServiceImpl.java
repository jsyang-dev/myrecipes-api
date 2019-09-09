package io.myrecipes.api.service;

import io.myrecipes.api.domain.Recipe;
import io.myrecipes.api.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Recipe readRecipe(int id) {
        return recipeRepository.getOne(id);
    }

    @Override
    public List<Recipe> readRecipeList() {
        return recipeRepository.findAll();
    }

    @Override
    public Recipe createRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @Override
    public Recipe updateRecipe(int id, Recipe recipe) {
        Optional<Recipe> recipeOptional = Optional.ofNullable(recipeRepository.getOne(id));

        if (!recipeOptional.isPresent()) {
            return null;
        }

        Recipe selectedRecipe = recipeOptional.get();
        selectedRecipe.update(recipe);
        return recipeRepository.save(selectedRecipe);
    }

    @Override
    public void deleteRecipe(int id) {
        recipeRepository.deleteById(id);
    }
}
