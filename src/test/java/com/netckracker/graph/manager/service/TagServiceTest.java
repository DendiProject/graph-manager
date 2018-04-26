/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.service;

import com.netckracker.graph.manager.model.Receipe;
import com.netckracker.graph.manager.modelDto.ReceipeDto;
import com.netckracker.graph.manager.repository.CatalogRepository;
import com.netckracker.graph.manager.repository.ReceipeRepository;
import com.netckracker.graph.manager.repository.TagsRepository;
import java.util.List;
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
public class TagServiceTest {
    @Autowired
    private TagService tagService;
    @Autowired
    private TagsRepository tagRepository;
    @Autowired
    private ReceipeRepository receipeRepository;
    
    @Test
    public void findByTagTest()
    {
        Receipe receipe=new Receipe();
        receipe.setName("receipe");
         Receipe saved=receipeRepository.save(receipe);
         tagService.addTag(saved.getReceipeId(), "tag");
         List<ReceipeDto> receipes=tagService.findByTag("tag", 0, 5);
         assertEquals("receipe incorrect", saved.getReceipeId(), receipes.get(0).getReceipeId()); 
    }
    
    
    
}
