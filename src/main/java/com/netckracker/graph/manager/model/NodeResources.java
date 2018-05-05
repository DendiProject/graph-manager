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

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "node_id")
    private Node node;
    
    @ManyToOne
    @JoinColumn(name = "previous_node_id")
    private Node previousNode;
    
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "version_id")
    private ReceipeVersion version;
    
    @Column (name="number_of_resource")
    private double numberOfResource;
    
    @Column (name="input_output")
    private String inputOutput;

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

    public double getNumberOfResource() {
        return numberOfResource;
    }

    public void setNumberOfResource(double numberOfResource) {
        this.numberOfResource = numberOfResource;
    }

    public String getInputOrOutput() {
        return inputOutput;
    }

    public void setInputOrOutput(String inputOrOutput) {
        this.inputOutput = inputOrOutput;
    }

    public Node getPreviousNode() {
        return previousNode;
    }

    public void setPreviousNode(Node previousNode) {
        this.previousNode = previousNode;
    }
    
    
}
