package org.example.repositories;

import org.example.entites.Stock;

public class StockRepository extends BaseRepository<Stock> {

    public StockRepository() {
        super(Stock::getId);
    }
}
