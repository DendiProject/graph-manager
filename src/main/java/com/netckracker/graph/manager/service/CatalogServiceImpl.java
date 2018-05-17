/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.service;

import com.netckracker.graph.manager.model.Catalog;
import com.netckracker.graph.manager.model.Tags;
import com.netckracker.graph.manager.repository.CatalogRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 *
 * @author eliza
 */
@Service
public class CatalogServiceImpl implements CatalogService{
    @Autowired
    private CatalogRepository catalogRepository;

    @Override
    @Transactional
    public String createCatalog(String name, String description) {
        Catalog find=catalogRepository.findByName(name);
        if (find==null)
        {
            Catalog catalog=new Catalog();
            catalog.setName(name);
            catalog.setDescription(description);
            Catalog saved=catalogRepository.save(catalog);
            return saved.getCatalogId();
        }
        else return find.getCatalogId();
    }

    @Override
    public Catalog findCatalog(String name) {
        Catalog find=catalogRepository.findByName(name);
        if (find!=null)
        {
            return find;
        }
        else return null;
    }   

    @Override
    public List<Catalog> findByLetters(String letters, Integer page, Integer size) {
        List<Catalog> catalog=catalogRepository.findFirst10ByNameStartingWith(letters, new PageRequest(page.intValue(), size.intValue())).getContent();
        return catalog;
    }

    @Override
    public List<Catalog> getAll() {
        List<Catalog> allList=catalogRepository.findAll();
        return allList;
    }
}
