package com.example.ipanda1.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.example.ipanda1.model.Product;


/**
 * Created by Oubowu on 2017/12/13 17:09.
 */
@Entity(tableName = "products")
public class ProductEntity implements Product {

    @PrimaryKey
    private int id;

    private String name;

    private String description;

    private int price;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getPrice() {
        return price;
    }

    public ProductEntity() {
    }

    @Ignore
    public ProductEntity(int id, String name, String description, int price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    @Ignore
    public ProductEntity(Product product){
        id = product.getId();
        name = product.getName();
        description = product.getDescription();
        price = product.getPrice();
    }

}
