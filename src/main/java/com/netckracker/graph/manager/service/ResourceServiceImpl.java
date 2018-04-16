/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.service;

import com.netckracker.graph.manager.convertor.Convertor;
import com.netckracker.graph.manager.model.Resources;
import com.netckracker.graph.manager.modelDto.ResourceDto;
import com.netckracker.graph.manager.repository.ResourcesRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<ResourceDto> findByFirstLetters(String letters) {
        List<Resources> resources=resourcesRepository.findFirst10ByNameStartingWith(letters);
        return resources.stream()
               .map(resource->convertor.convertResourceToDto(resource))
               .collect(Collectors.toList());
    }
    
}
