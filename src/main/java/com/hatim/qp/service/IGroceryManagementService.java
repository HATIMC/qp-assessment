package com.hatim.qp.service;

import java.util.List;

import com.hatim.qp.controller.advice.exception.GroceryException;
import com.hatim.qp.dto.AddNewGroceryItemsResponse;
import com.hatim.qp.dto.DeleteGroceriesRequest;
import com.hatim.qp.dto.DeleteGroceriesResponse;
import com.hatim.qp.dto.GroceryCrate;
import com.hatim.qp.dto.GroceryPanel;

public interface IGroceryManagementService {
    AddNewGroceryItemsResponse addNewGroceryItems(List<GroceryCrate> groceryCrates) throws GroceryException;

    List<GroceryPanel> viewExistingGroceryItems();

    DeleteGroceriesResponse removeGroceryItems(DeleteGroceriesRequest groceryIds) throws GroceryException;

    AddNewGroceryItemsResponse updateDetailsOfGroceryItems(List<GroceryCrate> groceryCrates, Boolean updateLevelOnly)
	    throws GroceryException;
}
