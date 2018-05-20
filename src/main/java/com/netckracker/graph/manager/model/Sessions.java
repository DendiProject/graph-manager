/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author eliza
 */
@Entity
@Table
public class Sessions implements Serializable{
    @Id   
    @Column(name = "session_id")     
    private String sessionId;
      
    @Column(name = "inviter_id")     
    private String inviterId;
    
    @Column(name = "receipe_id")     
    private String receipeId;

    public String getReceipeId() {
        return receipeId;
    }

    public void setReceipeId(String receipeId) {
        this.receipeId = receipeId;
    }
    

    public String getInviterId() {
        return inviterId;
    }

    public void setInviterId(String inviterId) {
        this.inviterId = inviterId;
    }   
    

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }    
    
}
