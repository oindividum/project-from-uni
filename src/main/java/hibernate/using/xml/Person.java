package hibernate.using.xml;
import jakarta.persistence.Entity;

@Entity
public class Person {
    int id;
    String firstName;
    String surname;
    String address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    //@Override
    //public String toString() {
       // return STR."Person{id=\{id}, firstName='\{firstName}\{'\''}, surname='\{surname}\{'\''}, address='\{address}\{'\''}\{'}'}";
    }

