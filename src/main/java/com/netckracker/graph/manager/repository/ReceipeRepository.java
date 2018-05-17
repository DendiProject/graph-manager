/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.repository;

import com.netckracker.graph.manager.model.Catalog;
import com.netckracker.graph.manager.model.Receipe;
import com.netckracker.graph.manager.model.ReceipeVersion;
import java.util.List;
import java.util.Set;
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
    Page<Receipe> findByIsPublicAndIsCompletedAndCatalog(boolean isPublic, boolean isCompleted, Catalog catalog, Pageable pageable);
    
    @Query (value="SELECT r.* FROM RECEIPE r WHERE r.receipe_id in "
            + "(SELECT t.receipe_id FROM TopOfReceipes t JOIN  " 
            + "(Select receipe_id, sum(frequency_of_use) as sums FROM TopOfReceipes  GROUP BY receipe_id" 
            + " ORDER BY sums DESC) t1  ON t.receipe_id=t1.receipe_id ORDER BY t1.sums DESC )"
            + "AND r.is_public=true AND r.is_completed=true AND r.is_deleted=false "
            + "ORDER BY  r.receipe_id --#pageable\n",
            countQuery="SELECT count(*) FROM RECEIPE r WHERE r.receipe_id in "
            + "(SELECT t.receipe_id FROM TopOfReceipes t JOIN  " 
            + "(Select receipe_id, sum(frequency_of_use) as sums FROM TopOfReceipes  GROUP BY receipe_id" 
            + " ORDER BY sums DESC) t1  ON t.receipe_id=t1.receipe_id )"
            + "AND r.is_public=true AND r.is_completed=true AND r.is_deleted=false "
            + "GROUP BY r.receipe_id "
            + "ORDER BY r.receipe_id --#pageable\n",
     nativeQuery = true)
    Page <Receipe> getTop(Pageable pageable);

    
    
    @Query (value="SELECT r.* FROM RECEIPE r WHERE r.receipe_id in "
            + "(SELECT t.receipe_id FROM TopOfReceipes t JOIN  " 
            + "(Select receipe_id, user_id, sum(frequency_of_use) as sums FROM TopOfReceipes  "
            + "WHERE user_id=:userId "
            + "GROUP BY receipe_id, user_id" 
            + " ORDER BY sums DESC) t1  ON t.receipe_id=t1.receipe_id )"
            + "AND is_public=true AND is_completed=true AND is_deleted=false "
            + "ORDER BY r.receipe_id --#pageable\n",
            countQuery="SELECT count(*) FROM RECEIPE r WHERE r.receipe_id in "
            + "(Select receipe_id, user_id, sum(frequency_of_use) as sums FROM TopOfReceipes  "
            + "WHERE user_id=:userId "
            + "GROUP BY receipe_id, user_id" 
            + " ORDER BY sums DESC) t1  ON t.receipe_id=t1.receipe_id )"
            + "AND is_public=true AND is_completed=true AND is_deleted=false "
            + "ORDER BY r.receipe_id --#pageable\n",
     nativeQuery = true)
    Page<Receipe> getTopByUser(@Param("userId") String userId, Pageable pageable);
    
    @Query (value="SELECT r.* FROM RECEIPE r WHERE r.receipe_id in "
            + "(SELECT t.receipe_id FROM TopOfReceipes t JOIN  " 
            + "(Select receipe_id, sum(frequency_of_use) as sums FROM TopOfReceipes  GROUP BY receipe_id" 
            + " ORDER BY sums DESC) t1  ON t.receipe_id=t1.receipe_id )"
            + "AND r.is_public=true AND r.is_completed=true AND r.is_deleted=false AND r.catalog_id=:catalogId "
            + "ORDER BY r.receipe_id --#pageable\n",
            countQuery="SELECT count(*) FROM RECEIPE r WHERE r.receipe_id in "
            + "(SELECT t.receipe_id FROM TopOfReceipes t JOIN  " 
            + "(Select receipe_id, sum(frequency_of_use) as sums FROM TopOfReceipes  GROUP BY receipe_id" 
            + " ORDER BY sums DESC) t1  ON t.receipe_id=t1.receipe_id )"
            + "AND r.is_public=true AND r.is_completed=true AND r.is_deleted=false AND r.catalog_id=:catalogId "
            + "ORDER BY r.receipe_id --#pageable\n",
     nativeQuery = true)
    Page <Receipe> getTopByCatalog(@Param("catalogId") String catalogId, Pageable pageable);
    
    @Query (value="SELECT r.* FROM RECEIPE r WHERE r.receipe_id in "
            + "(SELECT t.receipe_id FROM TopOfReceipes t JOIN  " 
            + "(Select receipe_id, user_id, sum(frequency_of_use) as sums FROM TopOfReceipes  "
            + "WHERE user_id=:userId "
            + "GROUP BY receipe_id, user_id" 
            + " ORDER BY sums DESC) t1  ON t.receipe_id=t1.receipe_id )"
            + "AND r.is_public=true AND r.is_completed=true AND r.is_deleted=false AND r.catalog_id=:catalogId "
            + "ORDER BY r.receipe_id --#pageable\n",
            countQuery="SELECT count(*) FROM RECEIPE r WHERE r.receipe_id in "
            + "(Select receipe_id, user_id, sum(frequency_of_use) as sums FROM TopOfReceipes  "
            + "WHERE user_id=:userId "
            + "GROUP BY receipe_id, user_id" 
            + " ORDER BY sums DESC) t1  ON t.receipe_id=t1.receipe_id )"
            + "AND r.is_public=true AND r.is_completed=true AND r.is_deleted=false AND r.catalog_id=:catalogId "
            + "ORDER BY r.receipe_id --#pageable\n",
     nativeQuery = true)
    Page<Receipe> getTopByUserAndCatalog(@Param("userId") String userId,@Param("catalogId") String catalogId,
            Pageable pageable);
    
    @Query(value="SELECT  r.* FROM  Receipe r JOIN ReceipeVersion v ON v.receipe_id=r.receipe_id "
            + "WHERE v.user_id=:userId AND r.is_completed=false AND r.is_deleted=false",
            nativeQuery=true)
    Receipe findNotCompletedReceipe(@Param("userId") String userId);

}
