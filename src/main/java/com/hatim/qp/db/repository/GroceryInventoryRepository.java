package com.hatim.qp.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hatim.qp.db.model.GroceryInventory;

@Repository
public interface GroceryInventoryRepository extends JpaRepository<GroceryInventory, Integer> {
    GroceryInventory findByGroceryId(long id);

    @SuppressWarnings("unchecked")
    GroceryInventory save(GroceryInventory groceryInventory);

    void deleteByGroceryId(long id);

    @Modifying
    @Transactional
    @Query("DELETE FROM GroceryInventory gi WHERE gi.groceryId IN :groceryIds")
    void deleteByGroceryIds(@Param("groceryIds") List<Integer> groceryIds);

    @Query("SELECT gi FROM GroceryInventory gi WHERE gi.groceryId IN :groceryIds")
    List<GroceryInventory> findByGroceryIds(@Param("groceryIds") List<Integer> groceryIds);

    GroceryInventory findByGroceryName(String groceryName);

    @Query("SELECT gi FROM GroceryInventory gi WHERE gi.deleted = false")
    List<GroceryInventory> findAllViewableGroceries();
}
