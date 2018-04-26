/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.repository;

import com.netckracker.graph.manager.model.Node;
import com.netckracker.graph.manager.model.NodeResources;
import com.netckracker.graph.manager.model.ReceipeVersion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author eliza
 */
@Repository
public interface NodeResourcesRepository extends JpaRepository <NodeResources, String> {
    List<NodeResources> findByVersion(ReceipeVersion version);
    
    @Query(value="Select n.* from NodeResources n where n.node_id=:nodeId and n.resource_id in"
            + "(Select r.resource_id from Resources r where r.ingredient_or_resource=:ingredientResource)"
            + "and n.input_output=:inputOutput",
            nativeQuery = true)
    List<NodeResources> findByInputOutputAndNode(@Param("inputOutput") String inputOutput,
            @Param("ingredientResource") String ingredientResource, @Param ("nodeId")String nodeId);
}
