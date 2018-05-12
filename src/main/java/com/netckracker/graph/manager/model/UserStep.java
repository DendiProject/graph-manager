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
public class UserStep implements Serializable {
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
    
    @Column(name = "is_started") 
    private boolean isStarted;
    
    @ManyToOne
    @JoinColumn(name = "session_id", referencedColumnName = "session_id")
    private Sessions session;
    
    @Column(name = "is_completed") 
    private boolean isCompleted;
    
    @Column(name = "user_id") 
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    
    

    public Sessions getSession() {
        return session;
    }

    public void setSession(Sessions session) {
        this.session = session;
    }

    public boolean isIsStarted() {
        return isStarted;
    }

    public void setIsStarted(boolean isStarted) {
        this.isStarted = isStarted;
    } 
    

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

    public boolean isIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
    
}
