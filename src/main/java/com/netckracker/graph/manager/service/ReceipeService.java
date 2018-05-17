/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.service;

import com.netckracker.graph.manager.model.Receipe;
import com.netckracker.graph.manager.modelDto.ReceipeDto;
import com.netckracker.graph.manager.modelDto.ReceipeInformationDto;
import java.util.List;

/**
 *
 * @author eliza
 */
public interface ReceipeService {
    public void deleteReceipe( String receipeId, String userId);
    public ReceipeDto createReceipe (String name, String description, String catalogId, 
            String userId, boolean isPublic);    
    public String addReceipeResources(String receipeId,String userId, String resourceId, double resourceNumber);
    public List<ReceipeDto> getPublicCompletedReceipes(int page, int size);
    public ReceipeInformationDto  getReceipeInformation(String receipeId);
    public List<ReceipeDto> getReceipesByCatalog(String catalogId, int page, int size);
    public void setCompleted(String receipeId);
    public void createReceipeVersion(String receipeId,String userId);
    public boolean isReceipeExcist(String receipeId);
    public boolean isVersionCompleted(String receipeId);
    
    
    
}
