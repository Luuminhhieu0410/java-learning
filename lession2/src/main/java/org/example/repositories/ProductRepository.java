package org.example.repositories;

import org.example.entites.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductRepository extends BaseRepository<Product> {

    public ProductRepository(){
        super(Product::getId);
    }

}
