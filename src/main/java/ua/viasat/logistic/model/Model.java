package ua.viasat.logistic.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@Table(name = "model")
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="model_id")
    private int id;

    @Column(name="name")
    @NotEmpty(message = "*Будь ласка вкажіть назву моделі")
    private String name;
    @Column(name="type")
    @NotEmpty(message = "*Будь ласка вкажіть тип обладнання")
    private String type;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Model model = (Model) o;

        return id == model.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
