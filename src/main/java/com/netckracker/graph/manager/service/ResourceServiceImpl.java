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
    public String createResource(String resourceName, String userId, String measuring, String resourceOrIngredient, String pictureId) {
        Resources resource=new Resources();
        resource.setName(resourceName);
        resource.setIngredientOrRsource(resourceOrIngredient);
        resource.setMeasuring(measuring);
        resource.setUserId(userId);
        Resources saved=resourcesRepository.save(resource);
        return saved.getResourceId();
    }

    @Override
    public List<ResourceNameDto> findByFirstLetters(String letters,  int page, int size) {
        List<Resources> resources=resourcesRepository.findFirst10ByNameStartingWith(letters,new PageRequest(page, size)).getContent();
        return resources.stream()
               .map(resource->convertor.convertResourceToResourceNameDto(resource))
               .collect(Collectors.toList());
    }

    @Override
    public String createNodeResource(String resourceName, String nodeId, String measuring, String resourceOrIngredient) {
        Resources resource=new Resources();
        resource.setName(resourceName);
        resource.setMeasuring(measuring);       
        resource.setIngredientOrRsource(resourceOrIngredient);
        Resources saved=resourcesRepository.save(resource);
        return saved.getResourceId();
    }

    @Override
    public String getResourceIdByName(String resourceName) {
        Resources resource=resourcesRepository.findByName(resourceName);
        return resource.getResourceId();
    }
    
}
