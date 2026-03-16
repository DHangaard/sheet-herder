package app.persistence;

public interface IReferenceDAO<T> extends IDAO<T>
{
    T getByName(String name);
}
