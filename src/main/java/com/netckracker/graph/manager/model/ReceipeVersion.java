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
public class ReceipeVersion implements Serializable {    

    @Id   
    @Column(name = "version_id") 
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String versionId;
    
    @ManyToOne
    @JoinColumn(name = "receipe_id")
    private Receipe receipe;
    
    @Column(name = "user_id") 
    private String userId;
    
    @Column(name = "is_main_version") 
    private boolean isMainVersion;
    

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public Receipe getReceipe() {
        return receipe;
    }

    public void setReceipe(Receipe receipe) {
        this.receipe = receipe;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isIsMainVersion() {
        return isMainVersion;
    }

    public void setIsMainVersion(boolean isMainVersion) {
        this.isMainVersion = isMainVersion;
    }  
    
}
