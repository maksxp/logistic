package ua.viasat.logistic.service;

import ua.viasat.logistic.model.Equipment;

import java.util.List;

public interface EquipmentService {
    Equipment findById(int id) ;
    void saveEquipmentToWarehouse(Equipment equipment);
    List<Equipment> listAllEquipments();
   Equipment findBySerialNumber(String serialNumber);
   List <Equipment> findByLocation(String location);
    void editEquipmentLocation(String serialNumber);
}
