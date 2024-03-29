package webapp4.main.model;

import javax.persistence.*;

import java.sql.Blob;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long loan_id = null;
    @Column(columnDefinition = "TEXT")
    private String NIP;
    @Column(columnDefinition = "TEXT")
    private String IBAN;
    @Column(columnDefinition = "TEXT")
    private String name;
    @Column(columnDefinition = "TEXT")
    private String surname;

    @Lob
    private Blob imageFile;

    public Account(){
        super();
    }

    public String getNIP() {
        return NIP;
    }

    public void setNIP(String NIP) {
        this.NIP = NIP;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Blob getImageFile() {
        return imageFile;
    }

    public void setImageFile(Blob imageFile) {
        this.imageFile = imageFile;
    }

    @Override
    public String toString(){
        return NIP + "has IBAN: " + IBAN;
    }

    public Long getLoan_id() {
        return loan_id;
    }
}
