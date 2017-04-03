package ua.viasat.logistic.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.viasat.logistic.model.DeliveryAddress;

import java.util.List;

@Repository("deliveryAddressRepository")
public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Long> {
    List <DeliveryAddress> findByCompanyName(String companyName);
    DeliveryAddress findById(int id);
    DeliveryAddress findByAddress (String address);
}
