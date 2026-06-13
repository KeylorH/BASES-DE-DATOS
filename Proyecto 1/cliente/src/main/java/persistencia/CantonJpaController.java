package persistencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import logica.Distrito;
import java.util.ArrayList;
import java.util.List;
import logica.Canton;
import persistencia.exceptions.NonexistentEntityException;

public class CantonJpaController implements Serializable {

    public CantonJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public CantonJpaController() {
        emf = Persistence.createEntityManagerFactory("Proyecto_PU");
    }

    public void create(Canton canton) {
        if (canton.getDistritos() == null) {
            canton.setDistritos(new ArrayList<Distrito>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Distrito> attachedDistritos = new ArrayList<Distrito>();
            for (Distrito distritosDistritoToAttach : canton.getDistritos()) {
                distritosDistritoToAttach = em.getReference(distritosDistritoToAttach.getClass(), distritosDistritoToAttach.getIdDistrito());
                attachedDistritos.add(distritosDistritoToAttach);
            }
            canton.setDistritos(attachedDistritos);
            em.persist(canton);
            for (Distrito distritosDistrito : canton.getDistritos()) {
                Canton oldCantonOfDistritosDistrito = distritosDistrito.getCanton();
                distritosDistrito.setCanton(canton);
                distritosDistrito = em.merge(distritosDistrito);
                if (oldCantonOfDistritosDistrito != null) {
                    oldCantonOfDistritosDistrito.getDistritos().remove(distritosDistrito);
                    oldCantonOfDistritosDistrito = em.merge(oldCantonOfDistritosDistrito);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Canton canton) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Canton persistentCanton = em.find(Canton.class, canton.getIdCanton());
            List<Distrito> distritosOld = persistentCanton.getDistritos();
            List<Distrito> distritosNew = canton.getDistritos();
            List<Distrito> attachedDistritosNew = new ArrayList<Distrito>();
            for (Distrito distritosNewDistritoToAttach : distritosNew) {
                distritosNewDistritoToAttach = em.getReference(distritosNewDistritoToAttach.getClass(), distritosNewDistritoToAttach.getIdDistrito());
                attachedDistritosNew.add(distritosNewDistritoToAttach);
            }
            distritosNew = attachedDistritosNew;
            canton.setDistritos(distritosNew);
            canton = em.merge(canton);
            for (Distrito distritosOldDistrito : distritosOld) {
                if (!distritosNew.contains(distritosOldDistrito)) {
                    distritosOldDistrito.setCanton(null);
                    distritosOldDistrito = em.merge(distritosOldDistrito);
                }
            }
            for (Distrito distritosNewDistrito : distritosNew) {
                if (!distritosOld.contains(distritosNewDistrito)) {
                    Canton oldCantonOfDistritosNewDistrito = distritosNewDistrito.getCanton();
                    distritosNewDistrito.setCanton(canton);
                    distritosNewDistrito = em.merge(distritosNewDistrito);
                    if (oldCantonOfDistritosNewDistrito != null && !oldCantonOfDistritosNewDistrito.equals(canton)) {
                        oldCantonOfDistritosNewDistrito.getDistritos().remove(distritosNewDistrito);
                        oldCantonOfDistritosNewDistrito = em.merge(oldCantonOfDistritosNewDistrito);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = canton.getIdCanton();
                if (findCanton(id) == null) {
                    throw new NonexistentEntityException("The canton with id " + id + " no longer exists.");
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
            Canton canton;
            try {
                canton = em.getReference(Canton.class, id);
                canton.getIdCanton();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The canton with id " + id + " no longer exists.", enfe);
            }
            List<Distrito> distritos = canton.getDistritos();
            for (Distrito distritosDistrito : distritos) {
                distritosDistrito.setCanton(null);
                distritosDistrito = em.merge(distritosDistrito);
            }
            em.remove(canton);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Canton> findCantonEntities() {
        return findCantonEntities(true, -1, -1);
    }

    public List<Canton> findCantonEntities(int maxResults, int firstResult) {
        return findCantonEntities(false, maxResults, firstResult);
    }

    private List<Canton> findCantonEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Canton.class));
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

    public Canton findCanton(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Canton.class, id);
        } finally {
            em.close();
        }
    }

    public int getCantonCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Canton> rt = cq.from(Canton.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
