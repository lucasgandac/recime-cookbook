package com.recime.Cookbook.repository.criteria.impl;

import com.recime.Cookbook.model.Recipe;
import com.recime.Cookbook.repository.criteria.RecipeRepositoryCustom;
import com.recime.Cookbook.repository.criteria.params.RecipeParams;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;

import java.util.ArrayList;
import java.util.List;

public class RecipeRepositoryCustomImpl implements RecipeRepositoryCustom {
    private final EntityManager entityManager;

    public RecipeRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Recipe> getRecipesWithFilter(RecipeParams params) {
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Recipe> query = criteriaBuilder.createQuery(Recipe.class);
        Root<Recipe> recipe = query.from(Recipe.class);
        List<Predicate> predicates = new ArrayList<>();

        if(params.getVegetarian() != null){
            predicates.add(criteriaBuilder.isTrue(recipe.get("vegetarian")));
        }

        if (params.getServings() != null) {
            predicates.add(criteriaBuilder.equal(recipe.get("servings"), params.getServings()));
        }

        if(params.getContentSearch() != null){
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(recipe.get("instruction")), "%" +
                    params.getContentSearch().toLowerCase() + "%"));
        }

        if (params.getIncludeIngredients() != null && !params.getIncludeIngredients().isEmpty()) {
            Join<Recipe, String> ingredientJoin = recipe.join("ingredient");
            List<String> loweredIngredients = params.getIncludeIngredients().stream()
                    .map(String::toLowerCase).toList();
            predicates.add(criteriaBuilder.lower(ingredientJoin).in(loweredIngredients));
            query.groupBy(recipe.get("id"));
            query.having(criteriaBuilder.equal(criteriaBuilder.countDistinct(ingredientJoin), loweredIngredients.size()));
        }

        if (params.getExcludeIngredients() != null && !params.getExcludeIngredients().isEmpty()) {
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<Recipe> subRoot = subquery.from(Recipe.class);
            Join<Recipe, String> subJoin = subRoot.join("ingredient");
            List<String> loweredIngredients = params.getExcludeIngredients().stream()
                    .map(String::toLowerCase).toList();

            subquery.select(subRoot.get("id"))
                    .where(subJoin.in(loweredIngredients));

            predicates.add(criteriaBuilder.not(recipe.get("id").in(subquery)));
        }

        if(!predicates.isEmpty()){
            query.where(predicates.stream().toArray(Predicate[]::new));
        }

        TypedQuery<Recipe> queryResult = this.entityManager.createQuery(query);
        return queryResult.getResultList();
    }
}
