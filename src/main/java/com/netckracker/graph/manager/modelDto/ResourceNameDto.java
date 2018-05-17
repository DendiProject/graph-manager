/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.modelDto;

/**
 *
 * @author eliza
 */
public class ResourceNameDto {
    private String resourceId;
    private String name;
    private String ingredientOrResource;
    private String measuring;
    private String pictureId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getIngredientOrResource() {
        return ingredientOrResource;
    }

    public void setIngredientOrResource(String ingredientOrResource) {
        this.ingredientOrResource = ingredientOrResource;
    }

    public String getMeasuring() {
        return measuring;
    }

    public void setMeasuring(String measuring) {
        this.measuring = measuring;
    }

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }
    
    
}
