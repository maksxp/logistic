package ua.viasat.logistic.model;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "equipment")
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "equipment_id")
    private int id;
    @Column(name = "type")
    private String type;
    @Column(name = "warehouse_date")
    private Date warehouseDate;
    @Column(name = "warehouse_appearance")
    private int warehouseAppearance;
    @Column(name = "dealer_date")
    private Date dealerDate;
    @Column(name = "user_date")
    private Date userDate;
    @Column(name = "location")
    private String location;
    @Column(name = "state")
    private String state;
    @Column(name = "model")
    private String modelName;
    @Column(name = "serial_number")
    private String serialNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Date getWarehouseDate() {
        return warehouseDate;
    }

    public void setWarehouseDate(Date warehouseDate) {
        this.warehouseDate = warehouseDate;
    }

    public Date getDealerDate() {
        return dealerDate;
    }

    public void setDealerDate(Date dealerDate) {
        this.dealerDate = dealerDate;
    }

    public Date getUserDate() {
        return userDate;
    }

    public void setUserDate(Date userDate) {
        this.userDate = userDate;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Equipment equipment = (Equipment) o;

        return id == equipment.id;

    }
    @Override
    public int hashCode() {
        return id;
    }

    public int getWarehouseAppearance() {
        return warehouseAppearance;
    }

    public void setWarehouseAppearance(int warehouseAppearance) {
        this.warehouseAppearance = warehouseAppearance;
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", warehouseDate=" + warehouseDate +
                ", dealerDate=" + dealerDate +
                ", userDate=" + userDate +
                ", location='" + location + '\'' +
                ", state='" + state + '\'' +
                ", modelName='" + modelName + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                '}';
    }
}
