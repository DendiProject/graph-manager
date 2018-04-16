/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.repository;

import com.netckracker.graph.manager.model.Receipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author eliza
 */
@Repository
public interface ReceipeRepository extends JpaRepository <Receipe, String>{
    Receipe findByReceipeId(String ReceipeId);
    
    @Query(value = "select n.* from Receipe n Where n.receipe_id in"
        + "(Select t.Receipe_receipe_id from Receipe_tagList t where n.receipe_id=t.Receipe_receipe_id and t.tagList_tag_id=:tagid) "
        + "ORDER BY n.receipe_id --#pageable\n",            
         countQuery = "select count(*) from Receipe Where n.receipe_id in"
        + "(Select t.Node_node_id from Node_tagList t where n.node_id=t.Node_node_id and t.tagList_tag_id=:tagid)"
        + "(Select t.Receipe_receipe_id from Receipe_tagList t where n.receipe_id=t.Receipe_receipe_id and t.tagList_tag_id=:tagid) "
        + "ORDER BY n.receipe_id --#pageable\n",
        nativeQuery = true)
    Page<Receipe> findByTag(@Param("tagid") String tagId, Pageable pageable);
    
    Page<Receipe> findByIsPublicAndIsCompleted(boolean isPublic, boolean isCompleted, Pageable pageable);
}
