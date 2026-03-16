package app.persistence;

import app.entities.reference.Race;
import app.exceptions.DatabaseException;
import jakarta.persistence.*;

import java.util.List;

public class RaceDAO implements IReferenceDAO<Race>
{
    private final EntityManagerFactory emf;

    public RaceDAO(EntityManagerFactory emf)
    {
        this.emf = emf;
    }

    @Override
    public Race create(Race race)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            try
            {
                em.getTransaction().begin();
                em.persist(race);
                em.getTransaction().commit();
                return race;
            }
            catch (PersistenceException e)
            {
                if (em.getTransaction().isActive())
                {
                    em.getTransaction().rollback();
                }
                throw new DatabaseException("Failed to save race: " + e.getMessage());
            }
        }
    }

    @Override
    public Race getById(Long id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            Race foundRace = em.createQuery("""
                            SELECT r
                            FROM Race r
                            LEFT JOIN FETCH r.languages
                            LEFT JOIN FETCH r.traits
                            WHERE r.id = :id
                            """, Race.class)
                    .setParameter("id", id)
                    .getResultStream()
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Race with id " + id + " was not found"));
            return foundRace;
        }
        catch (PersistenceException e)
        {
            throw new DatabaseException("Failed to find race with name: " + id + e.getMessage());
        }
    }

    @Override
    public List<Race> getAll()
    {
        try (EntityManager em = emf.createEntityManager())
        {
            try
            {
                TypedQuery<Race> query = em.createQuery("""
                        SELECT DISTINCT r
                        FROM Race r
                        LEFT JOIN FETCH r.languages
                        LEFT JOIN FETCH r.traits
                        """, Race.class);
                return query.getResultList();
            }
            catch (PersistenceException e)
            {
                throw new DatabaseException("Failed to fetch races: " + e.getMessage());
            }
        }
    }

    @Override
    public Race update(Race race)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            try
            {
                em.getTransaction().begin();
                Race updatedRace = em.merge(race);
                em.getTransaction().commit();
                return updatedRace;
            }
            catch (PersistenceException e)
            {
                if (em.getTransaction().isActive())
                {
                    em.getTransaction().rollback();
                }
                throw new DatabaseException("Failed to update race: " + e.getMessage());
            }
        }
    }

    @Override
    public Long delete(Long id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            Race foundRace = em.find(Race.class, id);
            if (foundRace == null)
            {
                throw new DatabaseException("Race not found - id: " + id);
            }
            try
            {
                em.remove(foundRace);
                em.getTransaction().commit();
                return foundRace.getId();
            }
            catch (PersistenceException e)
            {
                if (em.getTransaction().isActive())
                {
                    em.getTransaction().rollback();
                }
                throw new DatabaseException("Failed to delete race with id: " + id + " " + e.getMessage());
            }
        }
    }

    @Override
    public Race getByName(String name)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            Race foundRace = em.createQuery("""
                            SELECT r
                            FROM Race r
                            LEFT JOIN FETCH r.languages
                            LEFT JOIN FETCH r.traits
                            WHERE LOWER(r.name) = LOWER(:name)""", Race.class)
                    .setParameter("name", name)
                    .getResultStream()
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Race with name \"" + name + "\" was not found"));
            return foundRace;
        }
        catch (PersistenceException e)
        {
            throw new DatabaseException("Failed to find race with name: \"" + name + "\"" + e.getMessage());
        }
    }
}
