package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Date;

@Table(name = "calisanlar",schema = "hr")
@Entity
public class Calisan {

    @Id
    @Column(name = "tcid")
    private String tcID;

    @Column(name = "isim")
    private String name;

    @Column(name = "soyisim")
    private String lastName;

    @Column(name = "maas")
    private double salary;

    @Column(name = "eklenme_tarihi")
    private Timestamp timestamp;

    @Column(name = "resim")
    private byte[] gorsel;

    @Column(name = "resim_turu")
    private String gorselTuru;

    public String getTcID() {
        return tcID;
    }

    public void setTcID(String tcID) {
        this.tcID = tcID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public byte[] getGorsel() {
        return gorsel;
    }

    public void setGorsel(byte[] gorsel) {
        this.gorsel = gorsel;
    }

    public String getGorselTuru() {
        return gorselTuru;
    }

    public void setGorselTuru(String gorselTuru) {
        this.gorselTuru = gorselTuru;
    }

    public Calisan() {

    }

    public Calisan(String tcID, String name, String lastName, double salary, byte[] gorsel, String gorselTuru) {
        this.tcID = tcID;
        this.name = name;
        this.lastName = lastName;
        this.salary = salary;
        this.timestamp=new Timestamp(new Date().getTime());
        this.gorsel = gorsel;
        this.gorselTuru = gorselTuru;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
