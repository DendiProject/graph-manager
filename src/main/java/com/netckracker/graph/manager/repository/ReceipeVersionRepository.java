/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.repository;

import com.netckracker.graph.manager.model.Receipe;
import com.netckracker.graph.manager.model.ReceipeVersion;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author eliza
 */
@Repository
public interface ReceipeVersionRepository extends JpaRepository <ReceipeVersion, String> {
    ReceipeVersion findByVersionId(String versionId);    
    ReceipeVersion findByReceipeAndUserId(Receipe receipe, String userId);
    ReceipeVersion findByReceipeAndIsMainVersion(Receipe receipe, boolean isMainVersion);
    
    @Query(value="SELECT DISTINCT v.* FROM Receipeversion v JOIN Receipe r ON v.receipe_id=r.receipe_id "
            + "WHERE v.user_id=:userId AND r.is_completed=false AND r.is_deleted=false",
            nativeQuery=true)
    Set<ReceipeVersion> findNotCompletedReceipe(@Param("userId") String userId);
}
