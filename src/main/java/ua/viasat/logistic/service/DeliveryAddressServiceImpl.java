package ua.viasat.logistic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.viasat.logistic.model.DeliveryAddress;
import ua.viasat.logistic.repository.DeliveryAddressRepository;

import java.util.List;

@Service("deliveryAddressService")
public class DeliveryAddressServiceImpl implements DeliveryAddressService{
    @Autowired
    private DeliveryAddressRepository deliveryAddressRepository;
    @Override
    public List<DeliveryAddress> listAllDeliveryAddresses(){   return deliveryAddressRepository.findAll() ;   }
    @Override
    public void saveDeliveryAddress (DeliveryAddress deliveryAddress) {
        deliveryAddressRepository.save(deliveryAddress);}
    @Override
    public DeliveryAddress findById(int id) {
        return deliveryAddressRepository.findById(id);
    }
    @Override
    public List<DeliveryAddress> listCompanyDeliveryAddresses(String companyName){
        return deliveryAddressRepository.findByCompanyName(companyName) ;   }
    @Override
    public DeliveryAddress findByAddress(String address) {
        return deliveryAddressRepository.findByAddress(address);
    }
}
