/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.service;

import com.netckracker.graph.manager.convertor.Convertor;
import com.netckracker.graph.manager.model.Receipe;
import com.netckracker.graph.manager.model.TopOfReceipes;
import com.netckracker.graph.manager.modelDto.ReceipeInformationDto;
import com.netckracker.graph.manager.repository.ReceipeRepository;
import com.netckracker.graph.manager.repository.TopOfReceipesRepository;
import java.math.BigInteger;
import java.util.ArrayList;
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
public class TopOfReceipesServiceImpl implements  TopOfReceipesService{
    @Autowired
    private ReceipeRepository receipeRepository;
    @Autowired
    private TopOfReceipesRepository topRepository;
    @Autowired
    private Convertor convertor;

    @Override
    public List<ReceipeInformationDto> getTopOfReceipes(int page, int size) {
        List<Receipe> receipes=new ArrayList<>();
        List <Object[]> list = topRepository.getTop(new PageRequest(page,size)).getContent();
        for (Object[] row : list) {
            String receipeId = (String) row[0];
             BigInteger sums= (BigInteger) row[1];
             Receipe receipe=receipeRepository.findByReceipeId(receipeId);
             receipes.add(receipe);
        }
        return receipes.stream()
               .map(receipe->convertor.convertReceipeToReceipeInformationDto(receipe))
               .collect(Collectors.toList());       
    }

    @Override
    public List<ReceipeInformationDto> getTopReceipesOfUser(int page, int size, String userId) {
        List<Receipe> receipes=new ArrayList<>();
        List <Object[]> list = topRepository.getTopByUser(userId, new PageRequest(page,size)).getContent();
        for (Object[] row : list) {
            String receipeId = (String) row[0];
             BigInteger sums= (BigInteger) row[1];
             Receipe receipe=receipeRepository.findByReceipeId(receipeId);
             receipes.add(receipe);
        }
        return receipes.stream()
               .map(receipe->convertor.convertReceipeToReceipeInformationDto(receipe))
               .collect(Collectors.toList());
    }

    @Override
    public List<ReceipeInformationDto> getTopOfReceipesByCatalog(int page, int size, String catalogId) {
        List<Receipe> receipes=new ArrayList<>();
        List <Object[]> list = topRepository.getTopByCatalog(catalogId, new PageRequest(page,size)).getContent();
        for (Object[] row : list) {
            String receipeId = (String) row[0];
             BigInteger sums= (BigInteger) row[1];
             Receipe receipe=receipeRepository.findByReceipeId(receipeId);
             receipes.add(receipe);
        }
        return receipes.stream()
               .map(receipe->convertor.convertReceipeToReceipeInformationDto(receipe))
               .collect(Collectors.toList());
    }

    @Override
    public List<ReceipeInformationDto> getTopOfReceipesByCatalogAndUser(int page, int size, String userId, String catalogId) {
        List<Receipe> receipes=new ArrayList<>();
        List <Object[]> list = topRepository.getTopByUserAndCatalog(userId, catalogId, new PageRequest(page,size)).getContent();
        for (Object[] row : list) {
            String receipeId = (String) row[0];
             BigInteger sums= (BigInteger) row[1];
             Receipe receipe=receipeRepository.findByReceipeId(receipeId);
             receipes.add(receipe);
        }
        return receipes.stream()
               .map(receipe->convertor.convertReceipeToReceipeInformationDto(receipe))
               .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void increaseCounter(Receipe receipe, String userId) {
        TopOfReceipes top=topRepository.findByReceipeAndUserId(receipe, userId);
        if (top!=null)
        {
            int count=top.getFrequencyOfUse();
            count++;
            top.setFrequencyOfUse(count);
            topRepository.save(top);
        }
        else 
        {
            TopOfReceipes newTop=new TopOfReceipes();
            newTop.setReceipe(receipe);
            newTop.setFrequencyOfUse(1);
            newTop.setUserId(userId);
            topRepository.save(newTop);
        }
    }
    
}
