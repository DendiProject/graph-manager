/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.repository;

import com.netckracker.graph.manager.model.Node;
import com.netckracker.graph.manager.model.Sessions;
import com.netckracker.graph.manager.model.UserStep;
import java.util.List;
import javax.websocket.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author eliza
 */
@Repository
public interface UserStepRepository extends JpaRepository <UserStep, String>{
    
    UserStep findByNodeAndSession(Node node, Sessions session);
    List<UserStep> findBySession(Sessions session);
    List<UserStep> findBySessionAndIsCompletedAndIsStarted(Sessions session, boolean isStarted, boolean isCompleted);
    UserStep findByNodeAndSessionAndIsCompletedAndIsStarted(Node node, Sessions session, boolean isStarted, boolean isCompleted);
    UserStep findByUserIdAndSessionAndIsCompletedAndIsStarted(String userId, Sessions session, boolean isStarted, boolean isCompleted);
    
}
