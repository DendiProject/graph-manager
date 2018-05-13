/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.repository;

import com.netckracker.graph.manager.model.Sessions;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author eliza
 */
public interface SessionsRepository extends JpaRepository <Sessions, String> {
    Sessions findBySessionId(String sessionId);
    
}
