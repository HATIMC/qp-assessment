package com.hatim.qp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hatim.qp.constant.QpConstants;
import com.hatim.qp.db.model.GroceryInventory;
import com.hatim.qp.db.model.GroceryLevel;
import com.hatim.qp.db.model.UserOrder;
import com.hatim.qp.db.repository.GroceryInventoryRepository;
import com.hatim.qp.db.repository.GroceryLevelRepository;
import com.hatim.qp.db.repository.UserOrderRepository;
import com.hatim.qp.dto.GroceryCrateUserOrderSubmitRequest;
import com.hatim.qp.dto.GroceryCrateUserOrderSubmitResponse;
import com.hatim.qp.dto.GroceryCrateUserView;
import com.hatim.qp.service.IApiKeyService;
import com.hatim.qp.service.IGroceryService;
import com.hatim.qp.utils.QpUtils;

@Service
public class GroceryServiceImpl implements IGroceryService {

    @Autowired
    GroceryInventoryRepository groceryInventoryRepository;
    @Autowired
    GroceryLevelRepository groceryLevelRepository;
    @Autowired
    IApiKeyService apiKeyService;
    @Autowired
    UserOrderRepository orderRepository;

    @Override
    public List<GroceryCrateUserView> viewListOfGroceries() {
	List<GroceryInventory> groceries = groceryInventoryRepository.findAllViewableGroceries();
	List<GroceryCrateUserView> view = buildUserView(groceries);
	return view;

    }

    private List<GroceryCrateUserView> buildUserView(List<GroceryInventory> inventory) {
	List<GroceryCrateUserView> view = new ArrayList<>();
	for (GroceryInventory groceryInventory : inventory) {
	    GroceryCrateUserView v = GroceryCrateUserView.builder().groceryId(groceryInventory.getGroceryId())
		    .groceryName(groceryInventory.getGroceryName()).groceryPrice(groceryInventory.getGroceryPrice())
		    .build();
	    view.add(v);
	}
	return view;
    }

    @Override
    public synchronized GroceryCrateUserOrderSubmitResponse bookMultipleGroceries(String apiKey,
	    List<GroceryCrateUserOrderSubmitRequest> reqs) {
	List<GroceryInventory> gI = groceryInventoryRepository.findAllViewableGroceries();
	List<GroceryLevel> gL = groceryLevelRepository
		.findAllById(gI.stream().map(i -> i.getGroceryId()).collect(Collectors.toList()));
	Map<String, List<GroceryCrateUserOrderSubmitRequest>> map = validateUserOrder(gL, reqs);

	List<GroceryLevel> gLTemp = new ArrayList<>(gL);
	List<UserOrder> uOs = new ArrayList<>();
	Double price = 0d;

	for (GroceryCrateUserOrderSubmitRequest req : map.get(QpConstants.SUCCESS)) {
	    GroceryLevel gLev = gLTemp.stream()
		    .filter(i -> i.getGroceryInventory().getGroceryId() == req.getGroceryId()).findFirst().get();
	    GroceryInventory gInv = gI.stream().filter(i -> i.getGroceryId() == req.getGroceryId()).findFirst().get();
	    gLev.setLevel(gLev.getLevel() - req.getLevel());
	    price = price + (gInv.getGroceryPrice() * req.getLevel().doubleValue());

	    UserOrder uO = new UserOrder();
	    uO.setCreatedDate(QpUtils.getCurrentTimestamp());
	    uO.setUpdatedDate(QpUtils.getCurrentTimestamp());
	    uO.setGroceryId(req.getGroceryId());
	    uO.setLevel(req.getLevel());
	    Integer userId = apiKeyService.getApiKeysMap().values().stream().filter(i -> i.getApiKey().equals(apiKey))
		    .map(i -> i.getUserId()).findFirst().get();
	    uO.setUserId(userId);
	    uOs.add(uO);
	}

	groceryLevelRepository.saveAll(gLTemp);
	orderRepository.saveAll(uOs);

	return GroceryCrateUserOrderSubmitResponse.builder().error(map.get(QpConstants.ERROR))
		.success(map.get(QpConstants.SUCCESS)).price(price).build();
    }

    private Map<String, List<GroceryCrateUserOrderSubmitRequest>> validateUserOrder(List<GroceryLevel> gI,
	    List<GroceryCrateUserOrderSubmitRequest> reqs) {
	List<GroceryCrateUserOrderSubmitRequest> succ = new ArrayList<>();
	List<GroceryCrateUserOrderSubmitRequest> erro = new ArrayList<>();
	for (GroceryCrateUserOrderSubmitRequest req : reqs) {
	    if (req.getLevel() <= gI.stream().filter(i -> i.getGroceryInventory().getGroceryId() == req.getGroceryId())
		    .findFirst().get().getLevel()) {
		succ.add(req);
	    } else {
		erro.add(req);
	    }
	}
	return new HashMap<>() {
	    {
		put(QpConstants.SUCCESS, succ);
		put(QpConstants.ERROR, erro);
	    }
	};
    }

}
