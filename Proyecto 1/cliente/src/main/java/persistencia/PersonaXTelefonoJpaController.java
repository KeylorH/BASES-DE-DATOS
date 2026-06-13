package persistencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import logica.PersonaXTelefono;
import persistencia.exceptions.NonexistentEntityException;
import persistencia.exceptions.PreexistingEntityException;

public class PersonaXTelefonoJpaController implements Serializable {

    public PersonaXTelefonoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public PersonaXTelefonoJpaController() {
        emf = Persistence.createEntityManagerFactory("Proyecto_PU");
    }

    public void create(PersonaXTelefono personaXTelefono) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(personaXTelefono);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPersonaXTelefono(personaXTelefono.getIdPersona()) != null) {
                throw new PreexistingEntityException("PersonaXTelefono " + personaXTelefono + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PersonaXTelefono personaXTelefono) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            personaXTelefono = em.merge(personaXTelefono);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = personaXTelefono.getIdPersona();
                if (findPersonaXTelefono(id) == null) {
                    throw new NonexistentEntityException("The personaXTelefono with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PersonaXTelefono personaXTelefono;
            try {
                personaXTelefono = em.getReference(PersonaXTelefono.class, id);
                personaXTelefono.getIdPersona();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The personaXTelefono with id " + id + " no longer exists.", enfe);
            }
            em.remove(personaXTelefono);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PersonaXTelefono> findPersonaXTelefonoEntities() {
        return findPersonaXTelefonoEntities(true, -1, -1);
    }

    public List<PersonaXTelefono> findPersonaXTelefonoEntities(int maxResults, int firstResult) {
        return findPersonaXTelefonoEntities(false, maxResults, firstResult);
    }

    private List<PersonaXTelefono> findPersonaXTelefonoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PersonaXTelefono.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public PersonaXTelefono findPersonaXTelefono(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PersonaXTelefono.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonaXTelefonoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PersonaXTelefono> rt = cq.from(PersonaXTelefono.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
