package app.persistence.daos.interfaces;

import java.util.Collection;
import java.util.List;

public interface IReferenceDAO<T> extends IDAO<T>
{
    T getByName(String name);

    List<T> getByNames(Collection<String> names);

    List<T> syncAll(List<T> entities);
}
