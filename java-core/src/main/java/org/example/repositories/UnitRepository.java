
package org.example.repositories;

import org.example.entites.Unit;

import java.util.ArrayList;
import java.util.List;

public class UnitRepository extends BaseRepository<Unit> {

    public UnitRepository() {
        super(Unit::getId);
    }
}
