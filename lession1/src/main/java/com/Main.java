package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

public class Main {

    public static void main(String[] args) {

        var context = new AnnotationConfigApplicationContext(Person.class);
        Vehicle vehicle = context.getBean(Vehicle.class);
        Person person = context.getBean(Person.class);
        System.out.println(">>>>" + vehicle.getName() + person.getVehicle().getName());

    }
}

@Component

class Person{
    private int age;
    private String name;
    private Vehicle vehicle;

    @Autowired
    public Person(Vehicle vehicle){
        vehicle.setName("abc");
        this.vehicle = vehicle;
    }
    public Person(int age, String name, Vehicle vehicle) {
        this.age = age;
        this.name = name;
        this.vehicle = vehicle;
    }
    public Person(){}


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }


}

@Component
class Vehicle{
    private String name;

    public Vehicle(String name) {
        this.name = name;
    }
    public Vehicle(){}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}