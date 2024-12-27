package hibernate.using.annotation;

import jakarta.persistence.*;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

@Entity
@Table(name = "employees")
public class Person {
    public Person() {
    }

    public Person(String firstName, String surname, String address, String birthDate, String birth_place, double salary) {
        this.firstName = firstName;
        this.surname = surname;
        this.address = address;
        this.birthDate = birthDate;
        this.birth_place = birth_place;
        this.salary = salary;
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    Long id;

    @Column(name = "firstname")
    String firstName;

    @Column(name = "surname")
    String surname;

    @Column(name = "address")
    String address;

    @Column(name = "birthdate")
    String birthDate;

    @Column(name = "birth_place")
    String birth_place;

    @Column(name = "salary")
    double salary;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirth_place() {
        return birth_place;
    }

    public void setBirth_place(String birth_place) {
        this.birth_place = birth_place;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Person{id=" + id + ", firstName='" + firstName + '\'' + ", surname='" + surname + '\'' + ", address='" + address + '\'' + ", birthDate='" + birthDate + '\'' + ", birth_place='" + birth_place + '\'' + ", salary=" + salary + '}';
    }


}
