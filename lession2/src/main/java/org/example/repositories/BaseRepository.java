
package org.example.repositories;

import java.util.*;
import java.util.function.Function;

public abstract class BaseRepository<T> {

    protected final Map<String, T> data = new HashMap<>();

    private final Function<T, String> IdExtractorFunc;

    protected BaseRepository(Function<T, String> IdExtractorFunc) {
        this.IdExtractorFunc = IdExtractorFunc;
    }

    public T getById(String id) {
        T entity = data.get(id);

        if (entity == null) {
            throw new Error("Id not found");
        }

        return entity;
    }

    public List<T> getAll() {
        return new ArrayList<>(data.values());
    }

    public T add(T entity) {

        String id = IdExtractorFunc.apply(entity);
        data.put(id, entity);
        return entity;
    }

    public T editById(String id, T entity) {

        String entityId = IdExtractorFunc.apply(entity);
        data.put(id, entity);

        return entity;
    }

    public T deleteById(String id) {
        T entity = getById(id);

        data.remove(id);

        return entity;
    }
}
