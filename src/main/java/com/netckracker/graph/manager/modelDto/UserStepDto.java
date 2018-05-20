/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.modelDto;

import java.util.List;

/**
 *
 * @author eliza
 */
public class UserStepDto {
    private List<ResourceDto> resources;
    private List<ResourceDto> indredients;    
    private String nodeId;
    private String description;
    private String pictureId;
    private String inviterId;
    private boolean isStarted;

    public String getInviterId() {
        return inviterId;
    }

    public void setInviterId(String inviterId) {
        this.inviterId = inviterId;
    }

    public boolean isIsStarted() {
        return isStarted;
    }

    public void setIsStarted(boolean isStarted) {
        this.isStarted = isStarted;
    }    
    

    public List<ResourceDto> getResources() {
        return resources;
    }

    public void setResources(List<ResourceDto> resources) {
        this.resources = resources;
    }

    public List<ResourceDto> getIndredients() {
        return indredients;
    }

    public void setIndredients(List<ResourceDto> indredients) {
        this.indredients = indredients;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
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
    
    
    
}
