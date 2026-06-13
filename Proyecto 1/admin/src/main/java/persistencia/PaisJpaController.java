package persistencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import logica.Provincia;
import java.util.ArrayList;
import java.util.List;
import logica.Pais;
import persistencia.exceptions.NonexistentEntityException;

public class PaisJpaController implements Serializable {

    public PaisJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public PaisJpaController() {
        emf = Persistence.createEntityManagerFactory("Proyecto_PU");
    }

    public void create(Pais pais) {
        if (pais.getProvincias() == null) {
            pais.setProvincias(new ArrayList<Provincia>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Provincia> attachedProvincias = new ArrayList<Provincia>();
            for (Provincia provinciasProvinciaToAttach : pais.getProvincias()) {
                provinciasProvinciaToAttach = em.getReference(provinciasProvinciaToAttach.getClass(), provinciasProvinciaToAttach.getIdProvincia());
                attachedProvincias.add(provinciasProvinciaToAttach);
            }
            pais.setProvincias(attachedProvincias);
            em.persist(pais);
            for (Provincia provinciasProvincia : pais.getProvincias()) {
                Pais oldPaisOfProvinciasProvincia = provinciasProvincia.getPais();
                provinciasProvincia.setPais(pais);
                provinciasProvincia = em.merge(provinciasProvincia);
                if (oldPaisOfProvinciasProvincia != null) {
                    oldPaisOfProvinciasProvincia.getProvincias().remove(provinciasProvincia);
                    oldPaisOfProvinciasProvincia = em.merge(oldPaisOfProvinciasProvincia);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pais pais) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pais persistentPais = em.find(Pais.class, pais.getIdPais());
            List<Provincia> provinciasOld = persistentPais.getProvincias();
            List<Provincia> provinciasNew = pais.getProvincias();
            List<Provincia> attachedProvinciasNew = new ArrayList<Provincia>();
            for (Provincia provinciasNewProvinciaToAttach : provinciasNew) {
                provinciasNewProvinciaToAttach = em.getReference(provinciasNewProvinciaToAttach.getClass(), provinciasNewProvinciaToAttach.getIdProvincia());
                attachedProvinciasNew.add(provinciasNewProvinciaToAttach);
            }
            provinciasNew = attachedProvinciasNew;
            pais.setProvincias(provinciasNew);
            pais = em.merge(pais);
            for (Provincia provinciasOldProvincia : provinciasOld) {
                if (!provinciasNew.contains(provinciasOldProvincia)) {
                    provinciasOldProvincia.setPais(null);
                    provinciasOldProvincia = em.merge(provinciasOldProvincia);
                }
            }
            for (Provincia provinciasNewProvincia : provinciasNew) {
                if (!provinciasOld.contains(provinciasNewProvincia)) {
                    Pais oldPaisOfProvinciasNewProvincia = provinciasNewProvincia.getPais();
                    provinciasNewProvincia.setPais(pais);
                    provinciasNewProvincia = em.merge(provinciasNewProvincia);
                    if (oldPaisOfProvinciasNewProvincia != null && !oldPaisOfProvinciasNewProvincia.equals(pais)) {
                        oldPaisOfProvinciasNewProvincia.getProvincias().remove(provinciasNewProvincia);
                        oldPaisOfProvinciasNewProvincia = em.merge(oldPaisOfProvinciasNewProvincia);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = pais.getIdPais();
                if (findPais(id) == null) {
                    throw new NonexistentEntityException("The pais with id " + id + " no longer exists.");
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
            Pais pais;
            try {
                pais = em.getReference(Pais.class, id);
                pais.getIdPais();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pais with id " + id + " no longer exists.", enfe);
            }
            List<Provincia> provincias = pais.getProvincias();
            for (Provincia provinciasProvincia : provincias) {
                provinciasProvincia.setPais(null);
                provinciasProvincia = em.merge(provinciasProvincia);
            }
            em.remove(pais);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pais> findPaisEntities() {
        return findPaisEntities(true, -1, -1);
    }

    public List<Pais> findPaisEntities(int maxResults, int firstResult) {
        return findPaisEntities(false, maxResults, firstResult);
    }

    private List<Pais> findPaisEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pais.class));
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

    public Pais findPais(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pais.class, id);
        } finally {
            em.close();
        }
    }

    public int getPaisCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pais> rt = cq.from(Pais.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
