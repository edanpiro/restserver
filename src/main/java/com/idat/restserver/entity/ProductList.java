package com.idat.restserver.entity;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "products")
public class ProductList {
    private List<Product> products;

    public ProductList() {}

    public ProductList(List<Product> products) {
        this.products = products;
    }

    @XmlElement(name = "product")
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
