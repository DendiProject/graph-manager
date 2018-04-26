/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.repository;

import com.netckracker.graph.manager.model.Receipe;
import com.netckracker.graph.manager.model.ReceipeVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author eliza
 */
@Repository
public interface ReceipeVersionRepository extends JpaRepository <ReceipeVersion, String> {
    ReceipeVersion findByVersionId(String versionId);    
    ReceipeVersion findByReceipeAndUserId(Receipe receipe, String userId);
}
