/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
/**
 *
 * @author Dave
 */
public class Invoice implements Serializable {

  
    private String details;
    private String tax;
    private String recipient;
    private double price;

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        //check if details are less than 200 characters
        this.details = details;

    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "<u>Invoice</u>: " + "\n   Details: " + details + "\n   Tax: " + tax + "\n   Recipient: " + recipient + "\n   Price: " + price;
    }
}
