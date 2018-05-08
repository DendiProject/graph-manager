/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author eliza
 */
@Entity
@Table
public class Receipe implements Serializable {    
    public Receipe()
    {
        this.tagList=new HashSet();
    }

    @Id   
    @Column(name = "receipe_id") 
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String receipeId;
    
    @ManyToOne
    @JoinColumn(name = "catalog_id")
    private Catalog catalog;
    
    @Column(name = "name") 
    private String name;
    
    @Column(name = "description_id") 
    private String descriptionId;
    
    @Column(name = "is_public") 
    private boolean isPublic;
    
    @Column(name = "is_completed") 
    private boolean isCompleted;
    
    @Column(name = "is_deleted") 
    private boolean isDeleted;
    
    @Column(name = "picture_id") 
    private String pictureId;

    @OneToMany
    @Column(name="tags", unique=false)
    Set <Tags> tagList; 
    
    public String getReceipeId() {
        return receipeId;
    }

    public void setReceipeId(String receipeId) {
        this.receipeId = receipeId;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptionId() {
        return descriptionId;
    }

    public void setDescriptionId(String descriptionId) {
        this.descriptionId = descriptionId;
    }

    public boolean isIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public boolean isIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    public Set<Tags> getTagList() {
        return tagList;
    }

    public void setTagList(Set<Tags> tagList) {
        this.tagList = tagList;
    }
    
    
    
}
