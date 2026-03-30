package app.persistence.daos.implementations;

import app.exceptions.NotFoundException;
import app.persistence.entities.domain.User;
import app.exceptions.DatabaseException;
import app.persistence.daos.interfaces.IDAO;
import jakarta.persistence.*;

import java.util.List;

public class UserDAO implements IDAO<User>
{
    private final EntityManagerFactory emf;

    public UserDAO(EntityManagerFactory emf)
    {
        this.emf = emf;
    }

    @Override
    public User create(User user)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            try
            {
                em.getTransaction().begin();
                em.persist(user);
                em.getTransaction().commit();
                return user;
            }
            catch (PersistenceException e)
            {
                if (em.getTransaction().isActive())
                {
                    em.getTransaction().rollback();
                }
                throw new DatabaseException("Failed to persist user", e);
            }
        }
    }

    @Override
    public User getById(Long id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            User foundUser = em.find(User.class, id);
            if (foundUser == null)
            {
                throw new NotFoundException("User not found - id: " + id);
            }
            return foundUser;
        }
        catch (PersistenceException e)
        {
            throw new DatabaseException("Failed to find user with id: " + id, e);
        }
    }

    @Override
    public List<User> getAll()
    {
        try (EntityManager em = emf.createEntityManager())
        {
            try
            {
                TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
                return query.getResultList();
            }
            catch (PersistenceException e)
            {
                throw new DatabaseException("Failed to fetch users", e);
            }
        }
    }

    @Override
    public User update(User user)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            try
            {
                em.getTransaction().begin();
                User updatedUser = em.merge(user);
                em.getTransaction().commit();
                return updatedUser;
            }
            catch (PersistenceException e)
            {
                if (em.getTransaction().isActive())
                {
                    em.getTransaction().rollback();
                }
                throw new DatabaseException("Failed to update user", e);
            }
        }
    }

    @Override
    public Long delete(Long id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            User foundUser = em.find(User.class, id);
            if (foundUser == null)
            {
                throw new NotFoundException("User not found - id: " + id);
            }
            try
            {
                em.remove(foundUser);
                em.getTransaction().commit();
                return foundUser.getId();
            }
            catch (PersistenceException e)
            {
                if (em.getTransaction().isActive())
                {
                    em.getTransaction().rollback();
                }
                throw new DatabaseException("Failed to delete user with id: " + id, e);
            }
        }
    }
}
