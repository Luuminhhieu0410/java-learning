
package org.example;

import org.example.entites.*;
import org.example.repositories.ProductRepository;
import org.example.repositories.StockRepository;
import org.example.repositories.UnitRepository;

import java.time.Instant;
import java.util.*;
import java.util.function.Function;

public class Main {
    public static int test(int x){
        return x;
    }

    public static void main(String[] args) {
        Function<Integer,Integer> plus = Main::test;
        System.out.println(plus.andThen(x -> x + 1).apply(1));

        ProductRepository productRepository = new ProductRepository();
        StockRepository stockRepository = new StockRepository();
        UnitRepository unitRepository = new UnitRepository();

        DataInitializer.initialize(
                productRepository,
                stockRepository,
                unitRepository
        );

        productRepository.getAll().forEach(product ->
                System.out.println(product.getName())
        );



    }
}
class DataInitializer {

    public static void initialize(
            ProductRepository productRepository,
            StockRepository stockRepository,
            UnitRepository unitRepository
    ) {


        productRepository.add(new Product("0", "Quan"));
        productRepository.add(new Product("1", "Ao"));
        productRepository.add(new Product("2", "Dep"));


        stockRepository.add(new Stock("0", "Kho Ha Noi", "Ha Noi", 1));
        stockRepository.add(new Stock("1", "Kho Phu Tho", "Phu Tho", 2));
        stockRepository.add(new Stock("2", "Kho Vinh Phuc", "Vinh Phuc", 3));


        unitRepository.add(new Unit("0", "Chiec", "cai", "Don vi chiec"));
        unitRepository.add(new Unit("1", "Doi", "doi", "Don vi doi"));
        unitRepository.add(new Unit("2", "Hop", "hop", "Don vi hop"));
    }
}