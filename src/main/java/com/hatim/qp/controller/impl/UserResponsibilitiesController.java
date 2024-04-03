package com.hatim.qp.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.hatim.qp.controller.ApiController;
import com.hatim.qp.dto.GroceryCrateUserOrderSubmitRequest;
import com.hatim.qp.dto.GroceryCrateUserOrderSubmitResponse;
import com.hatim.qp.dto.GroceryCrateUserView;
import com.hatim.qp.service.IGroceryService;

@RestController
public class UserResponsibilitiesController extends ApiController {

    @Autowired
    IGroceryService groceryService;

    @GetMapping("/view_list_of_groceries")
    public ResponseEntity<List<GroceryCrateUserView>> ViewListOfGroceries() {
	return ResponseEntity.status(HttpStatus.OK).body(groceryService.viewListOfGroceries());
    }
    
    @PostMapping("/book_multiple_groceries")
    public ResponseEntity<GroceryCrateUserOrderSubmitResponse> bookMultipleGroceries(@RequestHeader("api_key") String api_key, @RequestBody List<GroceryCrateUserOrderSubmitRequest> req ) {
	
	return ResponseEntity.status(HttpStatus.OK).body(groceryService.bookMultipleGroceries(api_key, req));
    }

}
