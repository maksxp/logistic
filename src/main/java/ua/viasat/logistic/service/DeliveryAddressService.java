package ua.viasat.logistic.service;

import ua.viasat.logistic.model.DeliveryAddress;

import java.util.List;

public interface DeliveryAddressService {
    DeliveryAddress findById(int id) ;
    void saveDeliveryAddress(DeliveryAddress deliveryAddress);
    List<DeliveryAddress> listAllDeliveryAddresses();
    List <DeliveryAddress> listCompanyDeliveryAddresses(String companyName);
    DeliveryAddress findByAddress (String address);
    }

