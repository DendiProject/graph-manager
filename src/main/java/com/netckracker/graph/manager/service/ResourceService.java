/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.service;

import com.netckracker.graph.manager.modelDto.ResourceDto;
import java.util.List;

/**
 *
 * @author eliza
 */
public interface ResourceService {
    public String createResource(String resourceName, String userId,
            String measuring, String resourceOrIngredient,String pictureId);
    public List<ResourceDto> findByFirstLetters(String letters);
}

