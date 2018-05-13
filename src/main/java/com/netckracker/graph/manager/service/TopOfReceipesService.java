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
public interface TopOfReceipesService {
    List<ReceipeInformationDto> getTopOfReceipes(int page, int size);
    List<ReceipeInformationDto> getTopReceipesOfUser(int page, int size, String userId);
    List<ReceipeInformationDto> getTopOfReceipesByCatalog(int page, int size, String catalogId);
    List<ReceipeInformationDto> getTopOfReceipesByCatalogAndUser(int page, int size,String userId, String catalogId);
    void increaseCounter (Receipe receipe, String userId);
    
}
