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
public class Receipe implements Serializable {    

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
    
    @Column(name = "description") 
    private String description;
    
    @Column(name = "is_public") 
    private boolean isPublic;
    
    @Column(name = "is_completed") 
    private boolean isCompleted;
    
    @Column(name = "is_deleted") 
    private boolean isDeleted;
    
    @Column(name = "picture_id") 
    private String pictureId;
    
}
