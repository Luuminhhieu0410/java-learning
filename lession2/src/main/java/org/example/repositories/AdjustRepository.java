
package org.example.repositories;

import org.example.entites.Adjust;

public class AdjustRepository extends BaseRepository<Adjust> {

    public AdjustRepository() {
        super(Adjust::getId);
    }

}
