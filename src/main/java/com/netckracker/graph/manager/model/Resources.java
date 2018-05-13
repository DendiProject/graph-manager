/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.model;

import java.io.Serializable;
import java.util.Objects;
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
    
    @ManyToOne
    @JoinColumn(name = "node_id")
    private Node node;
    
    @Column(name = "picture_id") 
    private String pictureId;
    
    @Column(name = "ingredient_or_resource") 
    private String ingredientResource;

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    
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

    public String getIngredientOrResource() {
        return ingredientResource;
    }

    public void setIngredientOrResource(String ingredientOrResource) {
        this.ingredientResource = ingredientOrResource;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.resourceId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Resources other = (Resources) obj;
        if (!Objects.equals(this.resourceId, other.resourceId)) {
            return false;
        }
        return true;
    }
    
    
}
