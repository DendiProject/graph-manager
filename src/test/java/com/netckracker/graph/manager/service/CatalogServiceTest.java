/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.service;

import com.netckracker.graph.manager.model.Catalog;
import com.netckracker.graph.manager.repository.CatalogRepository;
import javax.transaction.Transactional;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author eliza
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class CatalogServiceTest {
    @Autowired
    private CatalogService catalogService;
    @Autowired
    private CatalogRepository catalogRepository;
    
    @Test
    public void createCatalogTest()
    {
        String catalogId=catalogService.createCatalog("catalog", "description");
        assertEquals("catalog incorrect", catalogRepository.findByCatalogId(catalogId).getCatalogId(), catalogId); 
    }
    
    @Test
    public void findCatalogTest()
    {
        String catalogId=catalogService.createCatalog("catalog", "description");
        Catalog finded=catalogService.findCatalog("catalog");
        assertEquals("catalog incorrect", catalogId, finded.getCatalogId());      
    }
    

}
