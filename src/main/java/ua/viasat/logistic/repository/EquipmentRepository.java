package ua.viasat.logistic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.viasat.logistic.model.Equipment;

import java.util.List;

@Repository("equipmentRepository")
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
	Equipment findById(int id);
	Equipment findBySerialNumber(String serialNumber);
	List <Equipment> findByLocation(String location);
}
