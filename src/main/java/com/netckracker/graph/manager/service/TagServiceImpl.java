/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.service;

import com.netckracker.graph.manager.convertor.Convertor;
import com.netckracker.graph.manager.model.Receipe;
import com.netckracker.graph.manager.model.Tags;
import com.netckracker.graph.manager.modelDto.ReceipeDto;
import com.netckracker.graph.manager.modelDto.TagsDto;
import com.netckracker.graph.manager.repository.ReceipeRepository;
import com.netckracker.graph.manager.repository.TagsRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 *
 * @author eliza
 */
@Service
public class TagServiceImpl implements TagService{
    @Autowired
    private ReceipeRepository receipeRepository;
    @Autowired
    private TagsRepository tagRepository;
    @Autowired
    private Convertor convertor;

    @Override
    @Transactional
    public void addTag(String receipeId, String tagName) {
        Receipe receipe=receipeRepository.findByReceipeId(receipeId);
        Tags tag=tagRepository.findByName(tagName);
        if (tag==null)
        {
            Tags newTag=new Tags();
            newTag.setName(tagName);
            Tags saved=tagRepository.save(newTag);
            receipe.getTagList().add(saved);            
        }
        else 
        {
             receipe.getTagList().add(tag); 
        }
        receipeRepository.save(receipe);
    }

    @Override
    public List<ReceipeDto> findByTag(String tagName, int page, int size) {
        Tags tag=tagRepository.findByName(tagName);
        if (tag!=null)
        {
            List<Receipe> receipes=receipeRepository.findByTag(tag.getTagId(), new PageRequest(page, size)).getContent();
            return receipes.stream()
               .map(receipe->convertor.convertReceipeToDto(receipe))
               .collect(Collectors.toList());
        }
        else return null;
    }

    @Override
    public List<TagsDto> findByFirstLetters(String letters) {
        List<Tags> tags=tagRepository.findFirst10ByNameStartingWith(letters);
        return tags.stream()
               .map(tag->convertor.convertTagsToDto(tag))
               .collect(Collectors.toList());
    }
    
}
