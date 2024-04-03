package com.hatim.qp.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hatim.qp.controller.AdminController;
import com.hatim.qp.controller.advice.exception.GroceryException;
import com.hatim.qp.dto.AddNewGroceryItemsResponse;
import com.hatim.qp.dto.DeleteGroceriesRequest;
import com.hatim.qp.dto.DeleteGroceriesResponse;
import com.hatim.qp.dto.GroceryCrate;
import com.hatim.qp.dto.GroceryPanel;
import com.hatim.qp.service.IGroceryManagementService;

@RestController
public class AdminResponsibilitiesController extends AdminController {

    @Autowired
    IGroceryManagementService groceryManagementService;

    @PostMapping("/add_new_grocery_items")
    public ResponseEntity<AddNewGroceryItemsResponse> addNewGroceryItems(@RequestBody List<GroceryCrate> groceryCrates)
	    throws GroceryException {
	AddNewGroceryItemsResponse res = groceryManagementService.addNewGroceryItems(groceryCrates);
	HttpStatus code;
	if (!res.getErrors().isEmpty()) {
	    code = HttpStatus.PARTIAL_CONTENT;
	} else {
	    code = HttpStatus.CREATED;
	}
	return ResponseEntity.status(code).body(res);
    }

    @GetMapping("/view_existing_grocery_items")
    public ResponseEntity<List<GroceryPanel>> viewExistingGroceryItems() {
	List<GroceryPanel> res = groceryManagementService.viewExistingGroceryItems();
	return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @DeleteMapping("/remove_grocery_items")
    public ResponseEntity<DeleteGroceriesResponse> removeGroceryItems(
	    @RequestParam("grocery_ids") List<Integer> groceryIds) throws GroceryException {
	DeleteGroceriesRequest req = DeleteGroceriesRequest.builder().groceryIds(groceryIds).build();
	DeleteGroceriesResponse res = groceryManagementService.removeGroceryItems(req);
	HttpStatus code;
	if (!res.getErrors().isEmpty()) {
	    code = HttpStatus.PARTIAL_CONTENT;
	} else {
	    code = HttpStatus.OK;
	}
	return ResponseEntity.status(code).body(res);
    }

    @PutMapping("update_details_of_grocery_items")
    public ResponseEntity<AddNewGroceryItemsResponse> updateDetailsOfGroceryItems(
	    @RequestBody List<GroceryCrate> groceryCrates) throws GroceryException {
	AddNewGroceryItemsResponse res = groceryManagementService.updateDetailsOfGroceryItems(groceryCrates, false);
	HttpStatus code;
	if (!res.getErrors().isEmpty()) {
	    code = HttpStatus.PARTIAL_CONTENT;
	} else {
	    code = HttpStatus.OK;
	}
	return ResponseEntity.status(code).body(res);
    }

    @PutMapping("manage_inventory_of_grocery_items")
    public ResponseEntity<AddNewGroceryItemsResponse> manageInventoryOfGroceryItems(
	    @RequestBody List<GroceryCrate> groceryCrates) throws GroceryException {
	AddNewGroceryItemsResponse res = groceryManagementService.updateDetailsOfGroceryItems(groceryCrates, true);
	HttpStatus code;
	if (!res.getErrors().isEmpty()) {
	    code = HttpStatus.PARTIAL_CONTENT;
	} else {
	    code = HttpStatus.OK;
	}
	return ResponseEntity.status(code).body(res);
    }

}
