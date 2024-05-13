package vn.hoidanit.laptopshop.domain.learn;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @OneToOne(mappedBy = "address")
    private NguoiDung user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NguoiDung getUser() {
        return user;
    }

    public void setUser(NguoiDung user) {
        this.user = user;
    }

    public Address(String name) {
        this.name = name;
    }

    public Address() {

    }

    @Override
    public String toString() {
        return "Address [id=" + id + ", name=" + name + ", user=" + user + "]";
    }

}
