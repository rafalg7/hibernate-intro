package pl.itcrowd.tutorial.hibernate.domain;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "DEPARTMENT")
public class Department implements Serializable {

    @Id
    @SequenceGenerator(name = "DEPARTMENT_ID_SEQUENCE", sequenceName = "DEPARTMENT_ID_SEQUENCE", initialValue = 1, allocationSize = 2)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEPARTMENT_ID_SEQUENCE")
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "COMPANY_ID", nullable = false)
    private Company company;

    @Embedded
    private Address address;

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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Department{" +
                "ID=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                '}';
    }
}
