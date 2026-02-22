package app.daos;

import java.util.List;

public interface IRetrieveDAO <T>
{
    List<T> getAllById(Long id);
}
