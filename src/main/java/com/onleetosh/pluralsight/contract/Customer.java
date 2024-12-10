package com.onleetosh.pluralsight.contract;

import com.onleetosh.pluralsight.datamanager.DataManager;
import com.onleetosh.pluralsight.dealership.Vehicle;

public class Customer {

    private int customerId;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;

    private String name;
    private DataManager dataManager;

    /**
     * Constructor
     */

    public Customer(int customerId, String name, String phone, String email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
    }

    public Customer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Customer( String name, String email) {

        this.name = name;
        this.email = email;
    }


    /**
     * Getters
     * @return
     */

    public int getCustomerId() {
        return customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }


    /**
     * Setters
     * @param customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    /**
     *  add vehicle to database
     */
    public void addCustomer(Customer customer){
        //dataManager.insertCustomer(customer);
    }

    @Override
    public String toString() {

        return "Customer{" +
                "customerId='" + customerId + '\'' +
                ", name ='" + name + '\'' +
                ", phone=" + phone +
                ", email='" + email + '\'' +
                '}';
    }
}
