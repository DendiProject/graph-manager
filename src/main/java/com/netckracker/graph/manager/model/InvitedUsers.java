/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author eliza
 */
@Entity
@Table
public class InvitedUsers {    
    @Id   
    @Column(name = "invited_id") 
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String invitedId;
    
    @ManyToOne
    @JoinColumn(name = "session_id")
    private Sessions session;
    
    @Column(name="user_id")
    private String userId;

    public String getInvitedId() {
        return invitedId;
    }

    public void setInvitedId(String invitedId) {
        this.invitedId = invitedId;
    }

    public Sessions getSession() {
        return session;
    }

    public void setSession(Sessions session) {
        this.session = session;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    
}
