/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.repository;

import com.netckracker.graph.manager.model.InvitedUsers;
import com.netckracker.graph.manager.model.Sessions;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author eliza
 */
@Repository
public interface InviteUsersRepository  extends JpaRepository<InvitedUsers, String>{
    List<InvitedUsers> findByUserId (String userId);
    List<InvitedUsers> findBySession (Sessions session);
    List<InvitedUsers> findByUserIdAndSession(String userId, Sessions session);
    
}
