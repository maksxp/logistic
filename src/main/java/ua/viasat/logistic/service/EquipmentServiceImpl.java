package ua.viasat.logistic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.viasat.logistic.model.Equipment;
import ua.viasat.logistic.repository.EquipmentRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("equipmentService")
public class EquipmentServiceImpl implements EquipmentService{

     @Autowired
    private EquipmentRepository equipmentRepository;

    @Override
    public List<Equipment> listAllEquipments(){   return equipmentRepository.findAll() ;   }
    @Override
    public void saveEquipmentToWarehouse(Equipment equipment) {
        if (equipment.getState().equalsIgnoreCase("new")){
        equipment.setLocation("mainWarehouse");
        equipment.setWarehouseAppearance(1);
    }
    else {
        equipment.setLocation("localWarehouse");
        List<Equipment> warehouseEquipments = new ArrayList<>();
        List<Equipment> mainWarehouseEquipments = equipmentRepository.findByLocation("mainWarehouse");
        List<Equipment> localWarehouseEquipments = equipmentRepository.findByLocation("localWarehouse");
        warehouseEquipments.addAll(mainWarehouseEquipments);
        warehouseEquipments.addAll(localWarehouseEquipments);
        List <String> allSerial = new ArrayList<>();
        for (Equipment equip : warehouseEquipments){
            allSerial.add(equip.getSerialNumber());
        }
        String serialNumber = equipment.getSerialNumber();
        Equipment equipmentExists;
        int warehouseAppearance=0;
        if (allSerial.contains(serialNumber)){
            equipmentExists = equipmentRepository.findBySerialNumber(serialNumber);
          warehouseAppearance = equipmentExists.getWarehouseAppearance();
        }
        equipment.setWarehouseAppearance(++warehouseAppearance);}
        equipment.setWarehouseDate(new Date());
        equipmentRepository.save(equipment);}
    @Override
    public Equipment findById(int id) { return equipmentRepository.findById(id);}

    @Override
    public List <Equipment> findByLocation(String location) { return equipmentRepository.findByLocation(location);}
    @Override
    public Equipment findBySerialNumber(String serial){return equipmentRepository.findBySerialNumber(serial);}
    @Override
    public void editEquipmentLocation (String serial){
        Equipment equipmentToEdit = equipmentRepository.findBySerialNumber(serial);
        equipmentToEdit.setLocation("DEALER");
        equipmentToEdit.setDealerDate(new Date());
        equipmentRepository.save(equipmentToEdit);}

    }
