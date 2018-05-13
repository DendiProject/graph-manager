/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.repository;

import com.netckracker.graph.manager.model.Receipe;
import com.netckracker.graph.manager.model.TopOfReceipes;
import java.util.List;
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
public interface TopOfReceipesRepository extends JpaRepository <TopOfReceipes, String>{
    TopOfReceipes findByReceipeAndUserId(Receipe receipe, String userId);
    TopOfReceipes findByReceipe (Receipe receipe);
    
    @Query (value="Select t.receipe_id, sum(t.frequency_of_use) as sums FROM TopOfReceipes t JOIN Receipe r ON t.receipe_id=r.receipe_id "
            + "WHERE r.is_public=true AND r.is_completed=true AND r.is_deleted=false "            
            + "GROUP BY t.receipe_id" 
            + " ORDER BY sums DESC , t.receipe_id --#pageable\n",
        countQuery="SELECT count(*) as counted FROM "
            +"( Select t.receipe_id, sum(t.frequency_of_use) as sums FROM TopOfReceipes t JOIN Receipe r   ON t.receipe_id=r.receipe_id "
            + "WHERE r.is_public=true AND r.is_completed=true AND r.is_deleted=false "            
            + "GROUP BY t.receipe_id" 
            + " ORDER BY sums DESC , t.receipe_id --#pageable\n)",
    nativeQuery = true)
    Page <Object[]> getTop(Pageable pageable);

    @Query (value="Select t.receipe_id, sum(t.frequency_of_use) as sums FROM TopOfReceipes t JOIN Receipe r ON t.receipe_id=r.receipe_id "
            + "WHERE r.is_public=true AND r.is_completed=true AND r.is_deleted=false AND t.user_id=:userId "            
            + "GROUP BY t.receipe_id" 
            + " ORDER BY sums DESC , t.receipe_id --#pageable\n",
        countQuery="SELECT count(*) as counted FROM "
            +"( Select t.receipe_id, sum(t.frequency_of_use) as sums FROM TopOfReceipes t JOIN Receipe r   ON t.receipe_id=r.receipe_id "
            + "WHERE r.is_public=true AND r.is_completed=true AND r.is_deleted=false AND t.user_id=:userId "            
            + "GROUP BY t.receipe_id " 
            + "ORDER BY sums DESC , t.receipe_id --#pageable\n)",
    nativeQuery = true)
    Page <Object[]> getTopByUser(@Param("userId") String userId, Pageable pageable);
    
    @Query (value="Select t.receipe_id, sum(t.frequency_of_use) as sums FROM TopOfReceipes t JOIN Receipe r ON t.receipe_id=r.receipe_id "
            + "WHERE r.is_public=true AND r.is_completed=true AND r.is_deleted=false AND r.catalog_id=:catalogId "            
            + "GROUP BY t.receipe_id" 
            + " ORDER BY sums DESC , t.receipe_id --#pageable\n",
        countQuery="SELECT count(*) as counted FROM "
            +"( Select t.receipe_id, sum(t.frequency_of_use) as sums FROM TopOfReceipes t JOIN Receipe r   ON t.receipe_id=r.receipe_id "
            + "WHERE r.is_public=true AND r.is_completed=true AND r.is_deleted=false AND r.catalog_id=:catalogId "            
            + "GROUP BY t.receipe_id " 
            + "ORDER BY sums DESC , t.receipe_id --#pageable\n)",
    nativeQuery = true)
    Page <Object[]> getTopByCatalog(@Param("catalogId") String catalogId, Pageable pageable);
    
    @Query (value="Select t.receipe_id, sum(t.frequency_of_use) as sums FROM TopOfReceipes t JOIN Receipe r ON t.receipe_id=r.receipe_id "
            + "WHERE r.is_public=true AND r.is_completed=true AND r.is_deleted=false "
            + "AND r.catalog_id=:catalogId AND t.user_id=:userId "            
            + "GROUP BY t.receipe_id" 
            + " ORDER BY sums DESC , t.receipe_id --#pageable\n",
        countQuery="SELECT count(*) as counted FROM "
            +"( Select t.receipe_id, sum(t.frequency_of_use) as sums FROM TopOfReceipes t JOIN Receipe r   ON t.receipe_id=r.receipe_id "
            + "WHERE r.is_public=true AND r.is_completed=true AND r.is_deleted=false "
            + "AND r.catalog_id=:catalogId AND t.user_id=:userId"            
            + "GROUP BY t.receipe_id " 
            + "ORDER BY sums DESC , t.receipe_id --#pageable\n)",
    nativeQuery = true)
    Page <Object[]> getTopByUserAndCatalog(@Param("userId") String userId, @Param("catalogId") String catalogId, Pageable pageable);
    
    
}
