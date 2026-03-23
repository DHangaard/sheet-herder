package app.persistence.daos.interfaces;

import java.util.List;

public interface IRetrieveDAO <T>
{
    List<T> getAllById(Long id);
}
