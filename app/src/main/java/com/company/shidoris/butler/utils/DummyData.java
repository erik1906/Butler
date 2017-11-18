package com.company.shidoris.butler.utils;

import com.company.shidoris.butler.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Erik on 17/11/2017.
 */

public class DummyData {

    public static List<Product> getProductsDummy(){
        List<Product> products = new ArrayList<>();
        products.add(new Product("1"));
        products.add(new Product("2"));
        products.add(new Product("3"));
        products.add(new Product("4"));
        products.add(new Product("5"));
        return products;
    }

}
