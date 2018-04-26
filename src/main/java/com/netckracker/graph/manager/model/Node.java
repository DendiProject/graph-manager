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
public class Node implements Serializable {    

    @Id   
    @Column(name = "node_id") 
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String nodeId;
    
    @ManyToOne
    @JoinColumn(name = "version_id")
    private ReceipeVersion version;
    
    @Column(name = "description") 
    private String description;
    
    @Column(name = "picture_id") 
    private String pictureId;
    
    @Column(name = "person_number") 
    private int personNumber;
        
    @Column(name = "node_number") 
    private int nodeNumber;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public ReceipeVersion getVersion() {
        return version;
    }

    public void setVersion(ReceipeVersion version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    public int getPersonNumber() {
        return personNumber;
    }

    public void setPersonNumber(int personNumber) {
        this.personNumber = personNumber;
    }

    public int getNodeNumber() {
        return nodeNumber;
    }

    public void setNodeNumber(int nodeNumber) {
        this.nodeNumber = nodeNumber;
    }
    
}
