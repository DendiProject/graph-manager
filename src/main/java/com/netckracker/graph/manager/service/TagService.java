/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.service;

import com.netckracker.graph.manager.modelDto.ReceipeDto;
import com.netckracker.graph.manager.modelDto.TagsDto;
import java.util.List;

/**
 *
 * @author eliza
 */
public interface TagService {
    public void addTag(String receipeId, String tagName);
    public List<ReceipeDto> findByTag(String tagName, int page, int size);
    public List<TagsDto> findByFirstLetters(String letters, int page, int size);
    
}
