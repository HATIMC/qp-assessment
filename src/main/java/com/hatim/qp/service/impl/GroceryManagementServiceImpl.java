package com.hatim.qp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hatim.qp.constant.QpConstants;
import com.hatim.qp.controller.advice.exception.GroceryException;
import com.hatim.qp.db.model.GroceryInventory;
import com.hatim.qp.db.model.GroceryLevel;
import com.hatim.qp.db.repository.GroceryInventoryRepository;
import com.hatim.qp.db.repository.GroceryLevelRepository;
import com.hatim.qp.dto.AddNewGroceryItemsResponse;
import com.hatim.qp.dto.DeleteGroceriesRequest;
import com.hatim.qp.dto.DeleteGroceriesResponse;
import com.hatim.qp.dto.GroceryCrate;
import com.hatim.qp.dto.GroceryPanel;
import com.hatim.qp.service.IGroceryManagementService;
import com.hatim.qp.utils.QpUtils;

@Service
public class GroceryManagementServiceImpl implements IGroceryManagementService {
    @Autowired
    GroceryInventoryRepository groceryInventoryRepository;
    @Autowired
    GroceryLevelRepository groceryLevelRepository;

    @Override
    public AddNewGroceryItemsResponse addNewGroceryItems(List<GroceryCrate> groceryCrates) throws GroceryException {
	try {
	    return saveNewGroceryItems(groceryCrates);
	} catch (Exception e) {
	    throw new GroceryException(2, "Error Occured while saving in DB", e);
	}
    }

    @Override
    public List<GroceryPanel> viewExistingGroceryItems() {
	var groceryCrates = groceryInventoryRepository.findAll();
	var groceryLevels = groceryLevelRepository.findAll();

	var panels = buildSuccessGroceryPanels(groceryCrates, groceryLevels);
	return panels;
    }

    @Override
    public DeleteGroceriesResponse removeGroceryItems(DeleteGroceriesRequest groceryIds) throws GroceryException {
	Map<String, List<Integer>> gIds = validateGroceryIdsForDeletion(groceryIds);
	List<Integer> validGids = gIds.get(QpConstants.SUCCESS);
	List<Integer> invalidGids = gIds.get(QpConstants.ERROR);
	List<GroceryPanel> panels = new ArrayList<>();
	if (!validGids.isEmpty()) {
	    var groceryInventories = groceryInventoryRepository.findByGroceryIds(validGids);
	    var groceryLevels = groceryLevelRepository.findByGroceryInventory_GroceryIdIn(validGids);
	    groceryInventories = deleteGroceryInventoryByGids(validGids);
	    // groceryLevelRepository.deleteByGroceryIds(groceryInventories.stream().map(i
	    // -> i.getGroceryId()).toList());
	    panels = buildSuccessGroceryPanels(groceryInventories, groceryLevels);
	}
	return DeleteGroceriesResponse.builder().success(panels).errors(invalidGids).build();
    }

    private List<GroceryInventory> deleteGroceryInventoryByGids(List<Integer> groceryIds) {
	var groceries = groceryInventoryRepository.findByGroceryIds(groceryIds);
	groceries = groceries.stream().peek(i -> i.setDeleted(true))
		.peek(i -> i.setUpdatedDate(QpUtils.getCurrentTimestamp())).collect(Collectors.toList());
	return groceryInventoryRepository.saveAll(groceries);

    }

    @Override
    public AddNewGroceryItemsResponse updateDetailsOfGroceryItems(List<GroceryCrate> groceryCrates,
	    Boolean updateLevelOnly) throws GroceryException {
	HashMap<String, List<GroceryCrate>> crates = validateAndFilterGroceryCratesToBeUpdated(groceryCrates);
	List<GroceryLevel> trueLevel;
	List<GroceryInventory> trueCrates;
	if (updateLevelOnly) {
	    trueLevel = updateAndSaveLevels(crates.get(QpConstants.SUCCESS));
	    trueCrates = groceryInventoryRepository
		    .findByGroceryIds(crates.get(QpConstants.SUCCESS).stream().map(i -> i.getGroceryId()).toList());
	} else {
	    trueCrates = updateAndSaveCrates(crates.get(QpConstants.SUCCESS));
	    trueLevel = groceryLevelRepository.findByGroceryInventory_GroceryIdIn(
		    crates.get(QpConstants.SUCCESS).stream().map(i -> i.getGroceryId()).toList());
	}
	List<GroceryPanel> errorPanel = crates.get(QpConstants.ERROR).stream()
		.map(i -> createAddNewGroceryItemsResponse(i)).collect(Collectors.toList());
	return AddNewGroceryItemsResponse.builder().success(buildSuccessGroceryPanels(trueCrates, trueLevel))
		.errors(errorPanel).build();
    }

    private AddNewGroceryItemsResponse saveNewGroceryItems(List<GroceryCrate> groceryCrates) throws GroceryException {
	HashMap<String, List<GroceryCrate>> crates = validateAndFilterGroceryCratesToBeAdded(groceryCrates);
	List<GroceryCrate> successCrates = crates.get(QpConstants.SUCCESS);
	List<GroceryCrate> errorCrates = crates.get(QpConstants.ERROR);
	List<GroceryInventory> deltaInventory = new ArrayList<>();
	List<GroceryLevel> level = new ArrayList<>();
	List<GroceryPanel> errorPanel = errorCrates.stream().map(i -> createAddNewGroceryItemsResponse(i))
		.collect(Collectors.toList());
	for (GroceryCrate crate : successCrates) {
	    List data = saveGroceryAndLevel(createGroceryInventoryCrate(crate), createGroceryLevel(crate));
	    deltaInventory.add((GroceryInventory) data.get(0));
	    level.add((GroceryLevel) data.get(1));
	}
	return AddNewGroceryItemsResponse.builder().success(buildSuccessGroceryPanels(deltaInventory, level))
		.errors(errorPanel).build();
    }

    @Transactional
    private List saveGroceryAndLevel(GroceryInventory inventory, GroceryLevel level) {
	inventory = groceryInventoryRepository.save(inventory);
	level.setGroceryInventory(inventory);
	groceryLevelRepository.save(level);
	return List.of(inventory, level);

    }

    private List<GroceryPanel> buildSuccessGroceryPanels(List<GroceryInventory> groceryCrates,
	    List<GroceryLevel> groceryLevels) {
	List<GroceryPanel> successPanel = new ArrayList<>();
	for (GroceryInventory crate : groceryCrates) {
	    GroceryPanel pane = new GroceryPanel();
	    pane.setCreatedDate(crate.getCreatedDate());
	    pane.setUpdatedDate(crate.getUpdatedDate());
	    pane.setGroceryId(crate.getGroceryId());
	    pane.setGroceryName(crate.getGroceryName());
	    pane.setGroceryPrice(crate.getGroceryPrice());
	    pane.setLevel(
		    groceryLevels.stream().filter(i -> i.getGroceryInventory().getGroceryId() == crate.getGroceryId())
			    .findFirst().get().getLevel());
	    pane.setDeleted(crate.getDeleted());
	    successPanel.add(pane);
	}
	return successPanel;
    }

    private HashMap<String, List<GroceryCrate>> validateAndFilterGroceryCratesToBeAdded(
	    List<GroceryCrate> groceryCrates) throws GroceryException {
	List<GroceryInventory> groceryInventory = groceryInventoryRepository.findAll();
	Map<String, List<GroceryCrate>> crates = getValidatedCratesToBeAdded(groceryInventory, groceryCrates);
	var successCrates = crates.get(QpConstants.SUCCESS);
	var errorCrates = crates.get(QpConstants.ERROR);
	return new HashMap<String, List<GroceryCrate>>() {
	    {
		put(QpConstants.SUCCESS, successCrates);
		put(QpConstants.ERROR, errorCrates);
	    }
	};
    }

    private HashMap<String, List<GroceryCrate>> validateAndFilterGroceryCratesToBeUpdated(
	    List<GroceryCrate> groceryCrates) throws GroceryException {
	List<GroceryInventory> groceryInventory = groceryInventoryRepository.findAll();
	Map<String, List<GroceryCrate>> crates = getValidatedCratesToBeUpdated(groceryInventory, groceryCrates);
	var successCrates = crates.get(QpConstants.SUCCESS);
	var errorCrates = crates.get(QpConstants.ERROR);
	return new HashMap<String, List<GroceryCrate>>() {
	    {
		put(QpConstants.SUCCESS, successCrates);
		put(QpConstants.ERROR, errorCrates);
	    }
	};
    }

    private Map<String, List<GroceryCrate>> getValidatedCratesToBeAdded(List<GroceryInventory> groceryInventory,
	    List<GroceryCrate> groceryCrates) {
	List<GroceryCrate> success = new ArrayList<>();
	List<GroceryCrate> error = new ArrayList<>();
	HashSet<GroceryCrate> distinctCrates = new HashSet<>(groceryCrates);
	for (GroceryCrate crate : distinctCrates) {
	    if (isNewCrateValid(groceryInventory, crate)) {
		success.add(crate);
	    } else {
		error.add(crate);
	    }

	}
	return new HashMap<>() {
	    {
		put(QpConstants.SUCCESS, success);
		put(QpConstants.ERROR, error);
	    }
	};
    }

    private Map<String, List<GroceryCrate>> getValidatedCratesToBeUpdated(List<GroceryInventory> groceryInventory,
	    List<GroceryCrate> groceryCrates) {
	List<GroceryCrate> success = new ArrayList<>();
	List<GroceryCrate> error = new ArrayList<>();
	HashSet<GroceryCrate> distinctCrates = new HashSet<>(groceryCrates);
	for (GroceryCrate crate : distinctCrates) {
	    if (isOldCrateValid(groceryInventory, crate)) {
		success.add(crate);
	    } else {
		error.add(crate);
	    }

	}
	return new HashMap<>() {
	    {
		put(QpConstants.SUCCESS, success);
		put(QpConstants.ERROR, error);
	    }
	};
    }

    private boolean isNewCrateValid(List<GroceryInventory> inventory, GroceryCrate crate) {
	if (null == crate.getGroceryName()) {
	    return false;
	}
	if (!inventory.isEmpty()) {
	    return !inventory.stream().map(i -> i.getGroceryName())
		    .anyMatch(name -> name.equals(StringUtils.capitalize(crate.getGroceryName())));
	}

	return true;
    }

    private boolean isOldCrateValid(List<GroceryInventory> inventory, GroceryCrate crate) {
	if (null == crate.getGroceryId() || crate.getGroceryId() == 0) {
	    return false;
	}
	if (!inventory.isEmpty()) {
	    return inventory.stream().anyMatch(inv -> inv.getGroceryId() == crate.getGroceryId());
	}

	return true;
    }

    private GroceryInventory createGroceryInventoryCrate(GroceryCrate crate) {
	GroceryInventory tempGroceryInventory = new GroceryInventory();
	tempGroceryInventory.setCreatedDate(QpUtils.getCurrentTimestamp());
	tempGroceryInventory.setUpdatedDate(QpUtils.getCurrentTimestamp());
	tempGroceryInventory.setGroceryName(StringUtils.capitalize(crate.getGroceryName()));
	tempGroceryInventory.setGroceryPrice(crate.getGroceryPrice());
	return tempGroceryInventory;
    }

    private GroceryLevel createGroceryLevel(GroceryCrate crate) {
	GroceryLevel tempGroceryLevel = new GroceryLevel();
	tempGroceryLevel.setCreatedDate(QpUtils.getCurrentTimestamp());
	tempGroceryLevel.setUpdatedDate(QpUtils.getCurrentTimestamp());
	tempGroceryLevel.setLevel(crate.getLevel());
	return tempGroceryLevel;
    }

    private GroceryPanel createAddNewGroceryItemsResponse(GroceryCrate crate) {
	GroceryPanel pane = new GroceryPanel();
	pane.setGroceryId(crate.getGroceryId());
	pane.setGroceryName(StringUtils.capitalize(crate.getGroceryName()));
	pane.setGroceryPrice(crate.getGroceryPrice());
	pane.setLevel(crate.getLevel());
	pane.setCreatedDate(QpUtils.getCurrentTimestamp());
	pane.setUpdatedDate(QpUtils.getCurrentTimestamp());
	return pane;
    }

    private Map<String, List<Integer>> validateGroceryIdsForDeletion(DeleteGroceriesRequest groceryIds)
	    throws GroceryException {
	List<Integer> gIds = new HashSet<>(groceryIds.getGroceryIds()).stream().collect(Collectors.toList());
	if (Objects.isNull(gIds) || gIds.isEmpty()) {
	    throw new GroceryException(3, "No grocery_ids passed for deletion");
	}
	List<GroceryInventory> dbRes = groceryInventoryRepository.findAllById(gIds);
	List<Integer> validGIds = new ArrayList<>();
	List<Integer> invalidGids = new ArrayList<>();
	if (dbRes.isEmpty()) {
	    invalidGids.addAll(groceryIds.getGroceryIds());
	} else {
	    var dbGids = dbRes.stream().map(i -> i.getGroceryId()).collect(Collectors.toList());
	    for (Integer g : gIds) {
		if (dbGids.contains(g)) {
		    validGIds.add(g);
		} else {
		    invalidGids.add(g);
		}
	    }
	}
	return new HashMap<>() {
	    {
		put(QpConstants.SUCCESS, validGIds);
		put(QpConstants.ERROR, invalidGids);
	    }
	};
    }

    private List<GroceryLevel> updateAndSaveLevels(List<GroceryCrate> successCrates) {
	if (successCrates != null && !successCrates.isEmpty()) {
	    List<GroceryLevel> trueLevel = groceryLevelRepository
		    .findByGroceryInventory_GroceryIdIn(successCrates.stream().map(i -> i.getGroceryId()).toList());
	    for (GroceryLevel lv : trueLevel) {
		var inLevel = successCrates.stream()
			.filter(i -> i.getGroceryId() == lv.getGroceryInventory().getGroceryId()).findFirst().get();
		if (inLevel.getLevel() != lv.getLevel()) {
		    lv.setLevel(inLevel.getLevel());
		    lv.setUpdatedDate(QpUtils.getCurrentTimestamp());
		}
	    }
	    return groceryLevelRepository.saveAll(trueLevel);
	}
	return new ArrayList<>();
    }

    private List<GroceryInventory> updateAndSaveCrates(List<GroceryCrate> successCrates) {
	if (successCrates != null && !successCrates.isEmpty()) {
	    List<GroceryInventory> trueCrates = groceryInventoryRepository
		    .findByGroceryIds(successCrates.stream().map(i -> i.getGroceryId()).toList());
	    for (GroceryInventory cr : trueCrates) {
		var inCrate = successCrates.stream().filter(i -> i.getGroceryId() == cr.getGroceryId()).findFirst()
			.get();
		if (inCrate.getGroceryName() != null && !inCrate.getGroceryName().isBlank()
			&& !inCrate.getGroceryName().equalsIgnoreCase(cr.getGroceryName())) {
		    cr.setGroceryName(inCrate.getGroceryName());
		    cr.setUpdatedDate(QpUtils.getCurrentTimestamp());
		}
		if (inCrate.getGroceryPrice() != null && inCrate.getGroceryPrice() != cr.getGroceryPrice()) {
		    cr.setGroceryPrice(inCrate.getGroceryPrice());
		    cr.setUpdatedDate(QpUtils.getCurrentTimestamp());
		}
	    }
	    return groceryInventoryRepository.saveAll(trueCrates);
	}
	return new ArrayList<>();
    }

}
