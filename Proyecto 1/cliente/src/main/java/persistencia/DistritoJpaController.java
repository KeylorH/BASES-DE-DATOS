package persistencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import logica.Canton;
import logica.Persona;
import java.util.ArrayList;
import java.util.List;
import logica.Distrito;
import persistencia.exceptions.NonexistentEntityException;

public class DistritoJpaController implements Serializable {

    public DistritoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public DistritoJpaController() {
        emf = Persistence.createEntityManagerFactory("Proyecto_PU");
    }

    public void create(Distrito distrito) {
        if (distrito.getPersonas() == null) {
            distrito.setPersonas(new ArrayList<Persona>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Canton canton = distrito.getCanton();
            if (canton != null) {
                canton = em.getReference(canton.getClass(), canton.getIdCanton());
                distrito.setCanton(canton);
            }
            List<Persona> attachedPersonas = new ArrayList<Persona>();
            for (Persona personasPersonaToAttach : distrito.getPersonas()) {
                personasPersonaToAttach = em.getReference(personasPersonaToAttach.getClass(), personasPersonaToAttach.getIdPersona());
                attachedPersonas.add(personasPersonaToAttach);
            }
            distrito.setPersonas(attachedPersonas);
            em.persist(distrito);
            if (canton != null) {
                canton.getDistritos().add(distrito);
                canton = em.merge(canton);
            }
            for (Persona personasPersona : distrito.getPersonas()) {
                Distrito oldDistritoOfPersonasPersona = personasPersona.getDistrito();
                personasPersona.setDistrito(distrito);
                personasPersona = em.merge(personasPersona);
                if (oldDistritoOfPersonasPersona != null) {
                    oldDistritoOfPersonasPersona.getPersonas().remove(personasPersona);
                    oldDistritoOfPersonasPersona = em.merge(oldDistritoOfPersonasPersona);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Distrito distrito) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Distrito persistentDistrito = em.find(Distrito.class, distrito.getIdDistrito());
            Canton cantonOld = persistentDistrito.getCanton();
            Canton cantonNew = distrito.getCanton();
            List<Persona> personasOld = persistentDistrito.getPersonas();
            List<Persona> personasNew = distrito.getPersonas();
            if (cantonNew != null) {
                cantonNew = em.getReference(cantonNew.getClass(), cantonNew.getIdCanton());
                distrito.setCanton(cantonNew);
            }
            List<Persona> attachedPersonasNew = new ArrayList<Persona>();
            for (Persona personasNewPersonaToAttach : personasNew) {
                personasNewPersonaToAttach = em.getReference(personasNewPersonaToAttach.getClass(), personasNewPersonaToAttach.getIdPersona());
                attachedPersonasNew.add(personasNewPersonaToAttach);
            }
            personasNew = attachedPersonasNew;
            distrito.setPersonas(personasNew);
            distrito = em.merge(distrito);
            if (cantonOld != null && !cantonOld.equals(cantonNew)) {
                cantonOld.getDistritos().remove(distrito);
                cantonOld = em.merge(cantonOld);
            }
            if (cantonNew != null && !cantonNew.equals(cantonOld)) {
                cantonNew.getDistritos().add(distrito);
                cantonNew = em.merge(cantonNew);
            }
            for (Persona personasOldPersona : personasOld) {
                if (!personasNew.contains(personasOldPersona)) {
                    personasOldPersona.setDistrito(null);
                    personasOldPersona = em.merge(personasOldPersona);
                }
            }
            for (Persona personasNewPersona : personasNew) {
                if (!personasOld.contains(personasNewPersona)) {
                    Distrito oldDistritoOfPersonasNewPersona = personasNewPersona.getDistrito();
                    personasNewPersona.setDistrito(distrito);
                    personasNewPersona = em.merge(personasNewPersona);
                    if (oldDistritoOfPersonasNewPersona != null && !oldDistritoOfPersonasNewPersona.equals(distrito)) {
                        oldDistritoOfPersonasNewPersona.getPersonas().remove(personasNewPersona);
                        oldDistritoOfPersonasNewPersona = em.merge(oldDistritoOfPersonasNewPersona);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = distrito.getIdDistrito();
                if (findDistrito(id) == null) {
                    throw new NonexistentEntityException("The distrito with id " + id + " no longer exists.");
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
            Distrito distrito;
            try {
                distrito = em.getReference(Distrito.class, id);
                distrito.getIdDistrito();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The distrito with id " + id + " no longer exists.", enfe);
            }
            Canton canton = distrito.getCanton();
            if (canton != null) {
                canton.getDistritos().remove(distrito);
                canton = em.merge(canton);
            }
            List<Persona> personas = distrito.getPersonas();
            for (Persona personasPersona : personas) {
                personasPersona.setDistrito(null);
                personasPersona = em.merge(personasPersona);
            }
            em.remove(distrito);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Distrito> findDistritoEntities() {
        return findDistritoEntities(true, -1, -1);
    }

    public List<Distrito> findDistritoEntities(int maxResults, int firstResult) {
        return findDistritoEntities(false, maxResults, firstResult);
    }

    private List<Distrito> findDistritoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Distrito.class));
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

    public Distrito findDistrito(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Distrito.class, id);
        } finally {
            em.close();
        }
    }

    public int getDistritoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Distrito> rt = cq.from(Distrito.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
