package com.hatim.qp.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hatim.qp.db.model.GroceryLevel;

@Repository
public interface GroceryLevelRepository extends JpaRepository<GroceryLevel, Integer> {
    GroceryLevel findByGroceryInventory_GroceryId(long id);

    @SuppressWarnings("unchecked")
    GroceryLevel save(GroceryLevel groceryLevel);

    @Modifying
    @Transactional
    @Query("DELETE FROM GroceryLevel gl WHERE gl.groceryInventory.groceryId IN :groceryIds")
    void deleteByGroceryIds(@Param("groceryIds") List<Integer> groceryIds);
    
    @Query("SELECT gl FROM GroceryLevel gl WHERE gl.groceryInventory.groceryId IN :ids")
    List<GroceryLevel> findByGroceryInventory_GroceryIdIn(List<Integer> ids);
    
}
