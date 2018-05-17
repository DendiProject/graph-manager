/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.service;

import com.netckracker.graph.manager.modelDto.ResourceDto;
import com.netckracker.graph.manager.modelDto.ResourceNameDto;
import java.util.List;

/**
 *
 * @author eliza
 */
public interface ResourceService {
    public String createResource(String resourceName, String userId,
            String measuring, String ingredientOrResource,String pictureId);
    public List<ResourceNameDto> findByFirstLetters(String letters,String ingredientOrResource,  int page, int size);
    public String createNodeResource(String resourceName, String nodeId,
           String ingredientOrResource);    
    public List<ResourceNameDto> findAll();
}

