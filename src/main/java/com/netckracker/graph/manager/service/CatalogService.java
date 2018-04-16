/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.service;

/**
 *
 * @author eliza
 */
public interface CatalogService {
    public String createCatalog (String name, String description);
    public String findCatalog(String name);
}
