/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.modelDto;

import java.util.Objects;

/**
 *
 * @author eliza
 */
public class EdgeDto {
    private String startNodeId;
    private String endNodeId;

    public String getStartNodeId() {
        return startNodeId;
    }

    public void setStartNodeId(String startNodeId) {
        this.startNodeId = startNodeId;
    }

    public String getEndNodeId() {
        return endNodeId;
    }

    public void setEndNodeId(String endNodeId) {
        this.endNodeId = endNodeId;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.startNodeId);
        hash = 79 * hash + Objects.hashCode(this.endNodeId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EdgeDto other = (EdgeDto) obj;
        if (!Objects.equals(this.startNodeId, other.startNodeId)) {
            return false;
        }
        if (!Objects.equals(this.endNodeId, other.endNodeId)) {
            return false;
        }
        return true;
    }    
}
