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
    
    @Column(name = "ingredient_or_resource") 
    private String ingredientOrRsource;
}
