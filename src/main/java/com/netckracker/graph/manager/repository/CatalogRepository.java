/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.repository;

import com.netckracker.graph.manager.model.Catalog;
import com.netckracker.graph.manager.model.Tags;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author eliza
 */
@Repository
public interface CatalogRepository extends JpaRepository <Catalog, String>{
    Catalog findByCatalogId(String catalogId);
    Catalog findByName(String name);
    Page <Catalog> findFirst10ByNameStartingWith(String letters, Pageable pageable);
    
}
