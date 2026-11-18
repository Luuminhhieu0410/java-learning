package org.example.repositories;

import org.example.entites.UnitCount;

public class UnitCountRepository extends BaseRepository<UnitCount> {

    public UnitCountRepository() {
        super(UnitCount::getId);
    }
}
