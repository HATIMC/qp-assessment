package com.hatim.qp.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.hatim.qp.dto.GroceryCrateUserOrderSubmitRequest;
import com.hatim.qp.dto.GroceryCrateUserOrderSubmitResponse;
import com.hatim.qp.dto.GroceryCrateUserView;

public interface IGroceryService {

    List<GroceryCrateUserView> viewListOfGroceries();


    GroceryCrateUserOrderSubmitResponse bookMultipleGroceries(String apiKey, List<GroceryCrateUserOrderSubmitRequest> req);

}
