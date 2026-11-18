
package org.example.repositories;

import org.example.entites.AdjustProductStock;

public class AdjustProductStockRepository
        extends BaseRepository<AdjustProductStock> {

    public AdjustProductStockRepository() {
        super(AdjustProductStock::getId);
    }

}
