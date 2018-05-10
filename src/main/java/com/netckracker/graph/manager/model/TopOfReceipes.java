/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author eliza
 */
@Entity
@Table
public class TopOfReceipes implements Serializable {
    @Id   
    @Column(name = "top_id") 
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String topId;
    
    @Column(name = "user_id") 
    private String userId;
    
    @Column(name = "frequency_of_use") 
    private int frequencyOfUse;
    
    @ManyToOne
    @JoinColumn(name = "receipe_id")
    private Receipe receipe;

    public String getTopId() {
        return topId;
    }

    public void setTopId(String topId) {
        this.topId = topId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getFrequencyOfUse() {
        return frequencyOfUse;
    }

    public void setFrequencyOfUse(int frequencyOfUse) {
        this.frequencyOfUse = frequencyOfUse;
    }

    public Receipe getReceipe() {
        return receipe;
    }

    public void setReceipe(Receipe receipe) {
        this.receipe = receipe;
    }  
}
