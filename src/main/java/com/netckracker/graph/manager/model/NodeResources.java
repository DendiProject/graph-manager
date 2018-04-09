/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author eliza
 */
@Entity
@Table
public class NodeResources implements Serializable {   
    
    @Id
    @ManyToOne
    @JoinColumn(name = "resource_id")
    private Resources resource;

    @ManyToOne
    @JoinColumn(name = "node_id")
    private Node node;
    
    @ManyToOne
    @JoinColumn(name = "version_id")
    private ReceipeVersion version;
    
    @Column (name="number_of_resource")
    private String numberOfResource;
    
    @Column (name="input_or_output")
    private String inputOrOutput;
    
}
