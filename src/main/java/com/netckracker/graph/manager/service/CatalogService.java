/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.service;

import com.netckracker.graph.manager.model.Catalog;
import java.util.List;

/**
 *
 * @author eliza
 */
public interface CatalogService {
    public String createCatalog (String name, String descriptionId);
    public Catalog findCatalog(String name);
    List<Catalog> findByLetters(String letters, Integer page, Integer size);
}
