/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.model;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;


/**
 *
 * @author eliza
 */
@Entity
@Table
public class UserStep {
    @Id   
    @Column(name = "step_id") 
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String stepId;
   
    @ManyToOne
    @JoinColumn(name = "node_id")
    private Node node;        
    
    @ManyToOne
    @JoinColumn(name = "version_id")
    private ReceipeVersion version;        
    
    @Column(name = "user_id")
    private String userId;
    
    @Column(name = "is_completed") 
    private boolean isCompleted;

    public String getStepId() {
        return stepId;
    }

    public void setStepId(String stepId) {
        this.stepId = stepId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
    
}
