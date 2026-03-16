package app.services.reference;

import java.util.List;

public interface IReferenceDataService<T, R>
{
    List<R> persistAll(List<T> dtos);
    R getById(Long id);
    R getByName(String name);
    List<R> getAll();
    R update(T dto);
    List<R> updateAll(List<T> dtos);
    Long delete(Long id);
}
