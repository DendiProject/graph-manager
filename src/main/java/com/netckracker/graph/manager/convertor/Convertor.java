/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.convertor;

import com.netckracker.graph.manager.model.Receipe;
import com.netckracker.graph.manager.model.Resources;
import com.netckracker.graph.manager.model.Tags;
import com.netckracker.graph.manager.modelDto.ReceipeDto;
import com.netckracker.graph.manager.modelDto.ResourceDto;
import com.netckracker.graph.manager.modelDto.TagsDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author eliza
 */
@Component
public class Convertor {
    @Autowired
private ModelMapper modelMapper;
    
    public ReceipeDto convertReceipeToDto(Receipe receipe)
    {
        ReceipeDto receipeDto=modelMapper.map(receipe, ReceipeDto.class);
        return receipeDto;
    }
    
    public ResourceDto convertResourceToDto(Resources resource)
    {
        ResourceDto resourceDto=modelMapper.map(resource, ResourceDto.class);
        return resourceDto;
    }
    public TagsDto convertTagsToDto(Tags tag)
    {
        TagsDto tagsDto=modelMapper.map(tag, TagsDto.class);
        return tagsDto;
    }
    public Resources convertDtoToEntity(ResourceDto resourceDto)
    {
        return modelMapper.map(resourceDto, Resources.class);
    }
}
