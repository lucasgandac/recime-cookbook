package com.recime.Cookbook.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RecipeDTO {
    private Long id;
    private String title;
    private String description;
    private String instruction;
    private boolean vegetarian;
    private int servings;
    @JsonProperty("ingredients")
    private List<String> ingredient;
}
