package com.example.mathtest;

public class Items {
    private String name;
    private String status;
    private int price;
    private String price_string;

    public String getName() {return name;}
    public String getStatus() {return status;}
    public int getPrice_Int() {return price;}
    public String getPrice_String() {return price_string;}
    public void setName(String name) {this.name = name;}
    public void setStatus(String status) {this.status =status;}
    public void setPrice(int price) {this.price = price;price_string=String.valueOf(price);}
}
