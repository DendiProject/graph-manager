/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.service;

import com.netckracker.graph.manager.convertor.Convertor;
import com.netckracker.graph.manager.model.Catalog;
import com.netckracker.graph.manager.model.Node;
import com.netckracker.graph.manager.model.NodeResources;
import com.netckracker.graph.manager.model.Receipe;
import com.netckracker.graph.manager.model.ReceipeVersion;
import com.netckracker.graph.manager.model.Resources;
import com.netckracker.graph.manager.modelDto.ReceipeDto;
import com.netckracker.graph.manager.modelDto.ReceipeInformationDto;
import com.netckracker.graph.manager.repository.CatalogRepository;
import com.netckracker.graph.manager.repository.NodeResourcesRepository;
import com.netckracker.graph.manager.repository.ReceipeRepository;
import com.netckracker.graph.manager.repository.ReceipeVersionRepository;
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
public class ReceipeServiceImpl implements ReceipeService{
    @Autowired
    private CatalogRepository catalogRepository;
    @Autowired
    private ReceipeVersionRepository versionRepository;
    @Autowired
    private ReceipeRepository receipeRepository;
    @Autowired
    private NodeResourcesRepository nodeResourcesRepository;
    @Autowired
    private ResourcesRepository resourcesRepository;
    @Autowired
    private Convertor convertor;

    @Override
    @Transactional
    public void deleteReceipe(String receipeId, String userId) {
        Receipe receipe=receipeRepository.findByReceipeId(receipeId);
        ReceipeVersion find=versionRepository.findByReceipeAndUserId(receipe, userId);
        if (find!=null)
        {
           if (find.isIsMainVersion()==true)
           {
               receipe.setIsDeleted(true);
               receipeRepository.save(receipe);
           }
           else 
           {
               versionRepository.delete(find);
           }
        }        
    }

    @Override
    @Transactional
    public ReceipeDto createReceipe(String name, String description, String catalog_id, 
            String userId, boolean is_public) {
        Receipe receipe=new Receipe();
        receipe.setName(name);
        receipe.setDescription(description);
        receipe.setIsCompleted(false);
        receipe.setIsPublic(false);
        receipe.setIsDeleted(false);
        Catalog find=catalogRepository.findByCatalogId(catalog_id);
        receipe.setCatalog(find);
        Receipe saved=receipeRepository.save(receipe);
        
        ReceipeVersion version=new ReceipeVersion();        
        version.setIsMainVersion(true);
        
        version.setUserId(userId);
        version.setReceipe(saved);
        versionRepository.save(version);
                
        return convertor.convertReceipeToDto(saved);
        
    }

    @Override
    @Transactional
    public String addReceipeResources(String receipeId,String userId, String resourceId, double resourceNumber) {
        
        Receipe receipe=receipeRepository.findByReceipeId(receipeId);
        ReceipeVersion version=versionRepository.findByReceipeAndUserId(receipe, userId);
        Resources resource=resourcesRepository.findByResourceId(resourceId);        
        NodeResources nodeResource=new NodeResources();
        nodeResource.setResource(resource);
        nodeResource.setVersion(version);
        nodeResource.setNumberOfResource(resourceNumber);        
        NodeResources saved=nodeResourcesRepository.save(nodeResource);
        return saved.getNodeResourceId();
    }

    @Override
    public List<ReceipeDto> getPublicCompletedReceipes(int page, int size) {
        
        List<Receipe> receipes=receipeRepository.findByIsPublicAndIsCompleted(true, true,new PageRequest(page, size)).getContent();
            return receipes.stream()
               .map(receipe->convertor.convertReceipeToDto(receipe))
               .collect(Collectors.toList());
    }

    @Override
    public ReceipeInformationDto getReceipeInformation(String receipeId) {
        Receipe receipe=receipeRepository.findByReceipeId(receipeId);
        return convertor.convertReceipeToReceipeInformationDto(receipe);
    }

    @Override
    public List<ReceipeDto> getReceipesByCatalog(String catalogId, int page, int size) {
        Catalog catalog=catalogRepository.findByCatalogId(catalogId);
        List<Receipe> receipes=receipeRepository.findByIsPublicAndIsCompletedAndCatalog(true, true, catalog,new PageRequest(page, size)).getContent();
            return receipes.stream()
               .map(receipe->convertor.convertReceipeToDto(receipe))
               .collect(Collectors.toList());
    }
    
}
