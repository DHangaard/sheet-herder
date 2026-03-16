package app.persistence;

import app.entities.reference.Language;
import app.exceptions.DatabaseException;
import jakarta.persistence.*;

import java.util.List;

public class LanguageDAO implements IReferenceDAO<Language>
{
    private final EntityManagerFactory emf;

    public LanguageDAO(EntityManagerFactory emf)
    {
        this.emf = emf;
    }

    @Override
    public Language create(Language language)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            try
            {
                em.getTransaction().begin();
                em.persist(language);
                em.getTransaction().commit();
                return language;
            }
            catch (PersistenceException e)
            {
                if (em.getTransaction().isActive())
                {
                    em.getTransaction().rollback();
                }
                throw new DatabaseException("Failed to save language: " + e.getMessage());
            }
        }
    }

    @Override
    public Language getById(Long id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            Language foundLanguage = em.find(Language.class, id);
            if (foundLanguage == null)
            {
                throw new DatabaseException("Language not found - id: " + id);
            }
            return foundLanguage;
        }
        catch (PersistenceException e)
        {
            throw new DatabaseException("Failed to find Language with id: " + id + " " + e.getMessage());
        }
    }

    @Override
    public List<Language> getAll()
    {
        try (EntityManager em = emf.createEntityManager())
        {
            try
            {
                TypedQuery<Language> query = em.createQuery("SELECT l FROM Language l", Language.class);
                return query.getResultList();
            }
            catch (PersistenceException e)
            {
                throw new DatabaseException("Failed to fetch languages: " + e.getMessage());
            }
        }
    }

    @Override
    public Language update(Language language)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            try
            {
                em.getTransaction().begin();
                Language updatedLanguage = em.merge(language);
                em.getTransaction().commit();
                return updatedLanguage;
            }
            catch (PersistenceException e)
            {
                if (em.getTransaction().isActive())
                {
                    em.getTransaction().rollback();
                }
                throw new DatabaseException("Failed to update language: " + e.getMessage());
            }
        }
    }

    @Override
    public Long delete(Long id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            Language foundLanguage = em.find(Language.class, id);
            if (foundLanguage == null)
            {
                throw new DatabaseException("Language not found - id: " + id);
            }
            try
            {
                em.remove(foundLanguage);
                em.getTransaction().commit();
                return foundLanguage.getId();
            }
            catch (PersistenceException e)
            {
                if (em.getTransaction().isActive())
                {
                    em.getTransaction().rollback();
                }
                throw new DatabaseException("Failed to delete language with id: " + id + " " + e.getMessage());
            }
        }
    }

    @Override
    public Language getByName(String name)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            Language foundLanguage = em.createQuery("SELECT l FROM Language l WHERE LOWER(l.name) = LOWER(:name)", Language.class)
                    .setParameter("name", name)
                    .getResultStream()
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Language with name \"" + name + "\" was not found"));
            return foundLanguage;
        }
        catch (PersistenceException e)
        {
            throw new DatabaseException("Failed to find language with name: \"" + name + "\"" + e.getMessage());
        }
    }
}
