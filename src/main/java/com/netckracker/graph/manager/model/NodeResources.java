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
public class NodeResources implements Serializable {   
    
    @Id   
    @Column(name = "node_resource_id") 
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String nodeResourceId;
    
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

    public String getNodeResourceId() {
        return nodeResourceId;
    }

    public void setNodeResourceId(String nodeResourceId) {
        this.nodeResourceId = nodeResourceId;
    }

    public Resources getResource() {
        return resource;
    }

    public void setResource(Resources resource) {
        this.resource = resource;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public ReceipeVersion getVersion() {
        return version;
    }

    public void setVersion(ReceipeVersion version) {
        this.version = version;
    }

    public String getNumberOfResource() {
        return numberOfResource;
    }

    public void setNumberOfResource(String numberOfResource) {
        this.numberOfResource = numberOfResource;
    }

    public String getInputOrOutput() {
        return inputOrOutput;
    }

    public void setInputOrOutput(String inputOrOutput) {
        this.inputOrOutput = inputOrOutput;
    }
    
    
}
