package org.iti.rest.service;

import org.iti.rest.dao.StaffDao;
import org.iti.rest.dto.CreateStaffRequest;
import org.iti.rest.dto.StaffResponse;
import org.iti.rest.dto.UpdateStaffRequest;
import org.iti.rest.entity.Address;
import org.iti.rest.entity.Staff;
import org.iti.rest.entity.Store;
import jakarta.persistence.EntityManager;
import org.iti.rest.config.JPAUtil;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class StaffService {

    private final StaffDao staffDao;

    public StaffService(StaffDao staffDao) {
        this.staffDao = staffDao;
    }

    public StaffResponse findById(Byte id) {
        Staff staff = staffDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Staff not found with id: " + id));
        return convertToResponse(staff);
    }

    public List<StaffResponse> findAll() {
        List<Staff> staffList = staffDao.findAll();
        return staffList.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public StaffResponse create(CreateStaffRequest request) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            Address address = em.find(Address.class, request.getAddressId());
            Store store = em.find(Store.class, request.getStoreId());

            if (address == null) {
                throw new RuntimeException("Address not found with id: " + request.getAddressId());
            }
            if (store == null) {
                throw new RuntimeException("Store not found with id: " + request.getStoreId());
            }

            Staff staff = new Staff();
            staff.setFirstName(request.getFirstName());
            staff.setLastName(request.getLastName());
            staff.setAddress(address);
            staff.setEmail(request.getEmail());
            staff.setStore(store);
            staff.setUsername(request.getUsername());
            staff.setPassword(request.getPassword());
            staff.setActive(true);
            staff.setLastUpdate(Instant.now());

            Staff savedStaff = staffDao.save(staff);
            return convertToResponse(savedStaff);
        } finally {
            em.close();
        }
    }

    public StaffResponse update(UpdateStaffRequest request) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            Staff existingStaff = staffDao.findById(request.getId())
                    .orElseThrow(() -> new RuntimeException("Staff not found with id: " + request.getId()));

            Address address = em.find(Address.class, request.getAddressId());
            Store store = em.find(Store.class, request.getStoreId());

            if (address == null) {
                throw new RuntimeException("Address not found with id: " + request.getAddressId());
            }
            if (store == null) {
                throw new RuntimeException("Store not found with id: " + request.getStoreId());
            }

            existingStaff.setFirstName(request.getFirstName());
            existingStaff.setLastName(request.getLastName());
            existingStaff.setAddress(address);
            existingStaff.setEmail(request.getEmail());
            existingStaff.setStore(store);
            existingStaff.setActive(request.getActive());
            existingStaff.setUsername(request.getUsername());
            existingStaff.setPassword(request.getPassword());
            existingStaff.setLastUpdate(Instant.now());

            Staff updatedStaff = staffDao.update(existingStaff);
            return convertToResponse(updatedStaff);
        } finally {
            em.close();
        }
    }

    public boolean deleteById(Byte id) {
        Staff staff = staffDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Staff not found with id: " + id));
        return staffDao.deleteById(id);
    }

    private StaffResponse convertToResponse(Staff staff) {
        StaffResponse response = new StaffResponse();
        response.setId(staff.getId());
        response.setFirstName(staff.getFirstName());
        response.setLastName(staff.getLastName());

        if (staff.getAddress() != null) {
            response.setAddressId(staff.getAddress().getId());
            response.setAddress(staff.getAddress().getAddress());
        }

        response.setEmail(staff.getEmail());

        if (staff.getStore() != null) {
            response.setStoreId(staff.getStore().getStoreId());
        }

        response.setActive(staff.getActive());
        response.setUsername(staff.getUsername());

        if (staff.getLastUpdate() != null) {
            response.setLastUpdate(staff.getLastUpdate().toString());
        }

        return response;
    }
}