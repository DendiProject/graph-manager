/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.convertor;

import com.netckracker.graph.manager.model.Edges;
import com.netckracker.graph.manager.model.Node;
import com.netckracker.graph.manager.model.NodeResources;
import com.netckracker.graph.manager.model.Receipe;
import com.netckracker.graph.manager.model.Resources;
import com.netckracker.graph.manager.model.Tags;
import com.netckracker.graph.manager.modelDto.CatalogDto;
import com.netckracker.graph.manager.modelDto.EdgeDto;
import com.netckracker.graph.manager.modelDto.NodeDto;
import com.netckracker.graph.manager.modelDto.ReceipeDto;
import com.netckracker.graph.manager.modelDto.ReceipeInformationDto;
import com.netckracker.graph.manager.modelDto.ResourceDto;
import com.netckracker.graph.manager.modelDto.ResourceNameDto;
import com.netckracker.graph.manager.modelDto.TagsDto;
import com.netckracker.graph.manager.repository.ResourcesRepository;
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
    @Autowired
    private ResourcesRepository resourcesRepository;
    
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
    
    public ResourceNameDto convertResourceToResourceNameDto(Resources resource)
    {
        ResourceNameDto resourceDto=modelMapper.map(resource, ResourceNameDto.class);
        return resourceDto;
    }
    
    public TagsDto convertTagsToDto(Tags tag)
    {
        TagsDto tagsDto=modelMapper.map(tag, TagsDto.class);
        return tagsDto;
    }

    public ReceipeInformationDto convertReceipeToReceipeInformationDto(Receipe receipe)
    {
        ReceipeInformationDto receipeInformationDto=modelMapper.map(receipe, ReceipeInformationDto.class);
        return receipeInformationDto;
    }
    
    public NodeDto convertNodeToDto(Node node)
    {
        NodeDto nodeDto=modelMapper.map(node, NodeDto.class);
        return nodeDto;
    }
    
    public EdgeDto convertEgdeToDto(Edges edge)
    {
        EdgeDto edgeDto=new EdgeDto();
        edgeDto.setStartNodeId(edge.getStartNode().getNodeId());
        edgeDto.setEndNodeId(edge.getEndNode().getNodeId());
        return edgeDto;
    }
    
    public ResourceDto convertNodeResourceToDto(NodeResources nodeResource)
    {
        ResourceDto resourceDto=new ResourceDto();
        resourceDto.setName(nodeResource.getResource().getName());
        resourceDto.setResourceId(nodeResource.getResource().getResourceId());
        resourceDto.setResourceNumber(nodeResource.getNumberOfResource());
        return resourceDto;
    }
    
 /*   public CatalogDto convertCatalogToDto(Catalog catalog)
    {
        TagsDto tagsDto=modelMapper.map(tag, TagsDto.class);
        return tagsDto;
    }*/

}
