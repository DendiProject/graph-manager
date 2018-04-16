/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.modelDto;

/**
 *
 * @author eliza
 */
public class ResourceDto {
    
    private String nodeResourceId;
    private String name;
    private String numberOfResource;
    private String inputOrOutput;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
