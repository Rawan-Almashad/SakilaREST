package org.iti.rest.service;

import org.iti.rest.dao.CustomerDao;
import org.iti.rest.dto.CreateCustomerRequest;
import org.iti.rest.dto.CustomerResponse;
import org.iti.rest.dto.UpdateCustomerRequest;
import org.iti.rest.entity.Address;
import org.iti.rest.entity.Customer;
import org.iti.rest.entity.Store;
import jakarta.persistence.EntityManager;
import org.iti.rest.config.JPAUtil;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public CustomerResponse findById(Short id) {
        Customer customer = customerDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
        return convertToResponse(customer);
    }

    public List<CustomerResponse> findAll() {
        List<Customer> customers = customerDao.findAll();
        return customers.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public CustomerResponse create(CreateCustomerRequest request) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            Store store = em.find(Store.class, request.getStoreId());
            Address address = em.find(Address.class, request.getAddressId());

            if (store == null) {
                throw new RuntimeException("Store not found with id: " + request.getStoreId());
            }
            if (address == null) {
                throw new RuntimeException("Address not found with id: " + request.getAddressId());
            }

            Customer customer = new Customer();
            customer.setStore(store);
            customer.setFirstName(request.getFirstName());
            customer.setLastName(request.getLastName());
            customer.setEmail(request.getEmail());
            customer.setAddress(address);
            customer.setActive(request.getActive() != null ? request.getActive() : true);
            customer.setCreateDate(Instant.now());
            customer.setLastUpdate(Instant.now());

            Customer savedCustomer = customerDao.save(customer);
            return convertToResponse(savedCustomer);
        } finally {
            em.close();
        }
    }

    public CustomerResponse update(UpdateCustomerRequest request) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            Customer existingCustomer = customerDao.findById(request.getId())
                    .orElseThrow(() -> new RuntimeException("Customer not found with id: " + request.getId()));

            Store store = em.find(Store.class, request.getStoreId());
            Address address = em.find(Address.class, request.getAddressId());

            if (store == null) {
                throw new RuntimeException("Store not found with id: " + request.getStoreId());
            }
            if (address == null) {
                throw new RuntimeException("Address not found with id: " + request.getAddressId());
            }

            existingCustomer.setStore(store);
            existingCustomer.setFirstName(request.getFirstName());
            existingCustomer.setLastName(request.getLastName());
            existingCustomer.setEmail(request.getEmail());
            existingCustomer.setAddress(address);
            existingCustomer.setActive(request.getActive());
            existingCustomer.setLastUpdate(Instant.now());

            Customer updatedCustomer = customerDao.update(existingCustomer);
            return convertToResponse(updatedCustomer);
        } finally {
            em.close();
        }
    }

    public boolean deleteById(Short id) {
        Customer customer = customerDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
        return customerDao.deleteById(id);
    }

    private CustomerResponse convertToResponse(Customer customer) {
        CustomerResponse response = new CustomerResponse();
        response.setId(customer.getId());

        if (customer.getStore() != null) {
            response.setStoreId(customer.getStore().getStoreId());
            if (customer.getStore().getAddress() != null) {
                response.setStoreAddress(customer.getStore().getAddress().getAddress());
            }
        }

        response.setFirstName(customer.getFirstName());
        response.setLastName(customer.getLastName());
        response.setEmail(customer.getEmail());

        if (customer.getAddress() != null) {
            response.setAddressId(customer.getAddress().getId());
            response.setAddress(customer.getAddress().getAddress());
            response.setAddressDistrict(customer.getAddress().getDistrict());
            response.setPhone(customer.getAddress().getPhone());

            if (customer.getAddress().getCity() != null) {
                response.setCityId(customer.getAddress().getCity().getId());
                response.setCity(customer.getAddress().getCity().getCity());

                if (customer.getAddress().getCity().getCountry() != null) {
                    response.setCountryId(customer.getAddress().getCity().getCountry().getId());
                    response.setCountry(customer.getAddress().getCity().getCountry().getCountry());
                }
            }
        }

        response.setActive(customer.getActive());

        if (customer.getCreateDate() != null) {
            response.setCreateDate(customer.getCreateDate().toString());
        }

        if (customer.getLastUpdate() != null) {
            response.setLastUpdate(customer.getLastUpdate().toString());
        }

        return response;
    }
}