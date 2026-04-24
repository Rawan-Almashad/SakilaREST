package org.iti.rest.service;

import org.iti.rest.dao.StoreDao;
import org.iti.rest.dto.CreateStoreRequest;
import org.iti.rest.dto.StoreResponse;
import org.iti.rest.dto.UpdateStoreRequest;
import org.iti.rest.entity.Address;
import org.iti.rest.entity.Staff;
import org.iti.rest.entity.Store;
import jakarta.persistence.EntityManager;
import org.iti.rest.config.JPAUtil;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class StoreService {

    private final StoreDao storeDao;

    public StoreService(StoreDao storeDao) {
        this.storeDao = storeDao;
    }

    public StoreResponse findById(Short id) {
        Store store = storeDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Store not found with id: " + id));
        return convertToResponse(store);
    }

    public List<StoreResponse> findAll() {
        List<Store> stores = storeDao.findAll();
        return stores.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public StoreResponse create(CreateStoreRequest request) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            Staff manager = em.find(Staff.class, request.getManagerStaffId());
            Address address = em.find(Address.class, request.getAddressId());

            if (manager == null) {
                throw new RuntimeException("Manager staff not found with id: " + request.getManagerStaffId());
            }
            if (address == null) {
                throw new RuntimeException("Address not found with id: " + request.getAddressId());
            }

            Store store = new Store();
            store.setManagerStaff(manager);
            store.setAddress(address);
            store.setLastUpdate(Instant.now());

            Store savedStore = storeDao.save(store);
            return convertToResponse(savedStore);
        } finally {
            em.close();
        }
    }

    public StoreResponse update(UpdateStoreRequest request) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            Store existingStore = storeDao.findById(request.getStoreId())
                    .orElseThrow(() -> new RuntimeException("Store not found with id: " + request.getStoreId()));

            Staff manager = em.find(Staff.class, request.getManagerStaffId());
            Address address = em.find(Address.class, request.getAddressId());

            if (manager == null) {
                throw new RuntimeException("Manager staff not found with id: " + request.getManagerStaffId());
            }
            if (address == null) {
                throw new RuntimeException("Address not found with id: " + request.getAddressId());
            }

            existingStore.setManagerStaff(manager);
            existingStore.setAddress(address);
            existingStore.setLastUpdate(Instant.now());

            Store updatedStore = storeDao.update(existingStore);
            return convertToResponse(updatedStore);
        } finally {
            em.close();
        }
    }

    public boolean deleteById(Short id) {
        Store store = storeDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Store not found with id: " + id));
        return storeDao.deleteById(id);
    }

    private StoreResponse convertToResponse(Store store) {
        StoreResponse response = new StoreResponse();
        response.setStoreId(store.getStoreId());

        if (store.getManagerStaff() != null) {
            response.setManagerStaffId(store.getManagerStaff().getId());
            response.setManagerStaffName(store.getManagerStaff().getFirstName() + " " + store.getManagerStaff().getLastName());
        }

        if (store.getAddress() != null) {
            response.setAddressId(store.getAddress().getId());
            response.setAddress(store.getAddress().getAddress());
        }

        if (store.getLastUpdate() != null) {
            response.setLastUpdate(store.getLastUpdate().toString());
        }

        return response;
    }
}