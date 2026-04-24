package org.iti.rest.dto;

public class StoreResponse {
    private Short storeId;
    private Byte managerStaffId;
    private String managerStaffName;
    private Short addressId;
    private String address;
    private String lastUpdate;

    public StoreResponse() {}

    public Short getStoreId() {
        return storeId;
    }

    public void setStoreId(Short storeId) {
        this.storeId = storeId;
    }

    public Byte getManagerStaffId() {
        return managerStaffId;
    }

    public void setManagerStaffId(Byte managerStaffId) {
        this.managerStaffId = managerStaffId;
    }

    public String getManagerStaffName() {
        return managerStaffName;
    }

    public void setManagerStaffName(String managerStaffName) {
        this.managerStaffName = managerStaffName;
    }

    public Short getAddressId() {
        return addressId;
    }

    public void setAddressId(Short addressId) {
        this.addressId = addressId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}