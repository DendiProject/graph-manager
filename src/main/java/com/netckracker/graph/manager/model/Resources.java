/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.model;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author eliza
 */
@Entity
@Table
public class Resources implements Serializable {    

    @Id   
    @Column(name = "resource_id") 
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String resourceId;
    
    @Column(name = "name") 
    private String name;
    
    @Column(name = "measuring") 
    private String measuring;
    
    @Column(name = "user_id") 
    private String userId;
    
    @Column(name = "picture_id") 
    private String pictureId;
    
    @Column(name = "ingredient_or_resource") 
    private String ingredientOrRsource;

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeasuring() {
        return measuring;
    }

    public void setMeasuring(String measuring) {
        this.measuring = measuring;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIngredientOrRsource() {
        return ingredientOrRsource;
    }

    public void setIngredientOrRsource(String ingredientOrRsource) {
        this.ingredientOrRsource = ingredientOrRsource;
    }
    
}
