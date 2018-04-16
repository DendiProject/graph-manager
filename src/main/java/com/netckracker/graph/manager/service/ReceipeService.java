/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.service;

import com.netckracker.graph.manager.modelDto.ReceipeDto;
import java.util.List;

/**
 *
 * @author eliza
 */
public interface ReceipeService {
    public void deleteReceipe( String receipeId, String userId);
    public String createReceipe (String name, String description, String catalog_id, 
            String userId, boolean is_public, int numberOfPeople);
    
    public String addReceipeResources(String receipeId,String userId, String resourceId, String resourceNumber);
    public List<ReceipeDto> getPublicCompletedReceipes(int page, int size);
    
}
