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
 * */
@Entity
@Table
public class Catalog implements Serializable {    

    @Id   
    @Column(name = "catalog_id") 
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String catalogId;
    
    @Column(name = "name") 
    private String name;
    
    @Column(name = "description") 
    private String description;
    
    
}