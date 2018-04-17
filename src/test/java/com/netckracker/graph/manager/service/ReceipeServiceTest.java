/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.service;

import com.netckracker.graph.manager.model.Receipe;
import com.netckracker.graph.manager.modelDto.ReceipeDto;
import com.netckracker.graph.manager.repository.ReceipeRepository;
import java.util.List;
import javax.transaction.Transactional;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 *
 * @author eliza
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class ReceipeServiceTest {
    @Autowired
    private ReceipeServiceImpl receipeService;
    @Autowired
    private ReceipeRepository receipeRepository;
    
    @Test
    public void getPublicAndCompletedReceipes()
    {
        Receipe receipe=new Receipe();
        receipe.setName("newReceipe");
        receipe.setIsCompleted(true);
        receipe.setIsPublic(true);
        Receipe saved=receipeRepository.save(receipe);
        
       List<ReceipeDto> receipes=receipeService.getPublicCompletedReceipes(0, 5);
       assertEquals("receipe incorrect", saved.getReceipeId(), receipes.get(0).getReceipeId());    
        
    }
    

    
}
