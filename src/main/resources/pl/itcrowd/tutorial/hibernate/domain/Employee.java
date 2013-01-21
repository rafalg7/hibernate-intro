package pl.itcrowd.tutorial.hibernate.domain;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "EMPLOYEE")
public class Employee implements Serializable {

    @Id
    @SequenceGenerator(name = "EMPLOYEE_ID_SEQUENCE", sequenceName = "EMPLOYEE_ID_SEQUENCE", initialValue = 1, allocationSize = 2)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMPLOYEE_ID_SEQUENCE")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @ManyToOne(optional = true)
    @JoinColumn(name = "BOSS_ID")
    private Employee boss;

    @ManyToOne(optional = false)
    @JoinColumn(name = "DEPARTMENT_ID", nullable = false)
    private Department department;

    @Embedded
    private Address address;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(name = "CITY_CORR")),
            @AttributeOverride(name = "street", column = @Column(name = "STREET_CORR")),
            @AttributeOverride(name = "zipCode", column = @Column(name = "ZIP_CODE_CORR"))
    })
    private Address addressForCorrenspondence;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Employee getBoss() {
        return boss;
    }

    public void setBoss(Employee boss) {
        this.boss = boss;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", boss=" + boss +
                ", department=" + department +
                ", address=" + address +
                ", addressForCorrenspondence=" + addressForCorrenspondence +
                '}';
    }
}
