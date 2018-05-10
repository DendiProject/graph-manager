/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.service;

import com.netckracker.graph.manager.convertor.Convertor;
import com.netckracker.graph.manager.model.Node;
import com.netckracker.graph.manager.model.Resources;
import com.netckracker.graph.manager.modelDto.ResourceDto;
import com.netckracker.graph.manager.modelDto.ResourceNameDto;
import com.netckracker.graph.manager.repository.NodeRepository;
import com.netckracker.graph.manager.repository.ResourcesRepository;
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
public class ResourceServiceImpl implements ResourceService{
    @Autowired
    private ResourcesRepository resourcesRepository;
    @Autowired
    private NodeRepository nodeRepository;
    @Autowired
    private Convertor convertor;

    @Override
    @Transactional
    public String createResource(String resourceName, String userId, String measuring, String ingredientOrResource, String pictureId) {
        Resources resource=new Resources();
        resource.setName(resourceName);
        resource.setIngredientOrResource(ingredientOrResource);
        resource.setMeasuring(measuring);
        resource.setUserId(userId);
        resource.setPictureId(pictureId);
        Resources saved=resourcesRepository.save(resource);
        return saved.getResourceId();
    }

    @Override
    public List<ResourceNameDto> findByFirstLetters(String letters, String ingredientOrResource, int page, int size) {
        List<Resources> resources=resourcesRepository.findFirst10ByIngredientResourceAndNameStartingWith(ingredientOrResource,letters,new PageRequest(page, size)).getContent();
        return resources.stream()
               .map(resource->convertor.convertResourceToResourceNameDto(resource))
               .collect(Collectors.toList());
    }

    @Override
    public String createNodeResource(String resourceName, String nodeId, String resourceOrIngredient) {
        Node node=nodeRepository.findByNodeId(nodeId);
        if (node!=null)
        {
            Resources resource=new Resources();
            resource.setName(resourceName);
            resource.setIngredientOrResource(resourceOrIngredient);
            resource.setNode(node);
            Resources saved=resourcesRepository.save(resource);
            return saved.getResourceId();
        }
        else return null;
    }

    
    
}
