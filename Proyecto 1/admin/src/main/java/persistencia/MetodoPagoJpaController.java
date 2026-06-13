package persistencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import logica.Factura;
import java.util.ArrayList;
import java.util.List;
import logica.MetodoPago;
import persistencia.exceptions.NonexistentEntityException;


public class MetodoPagoJpaController implements Serializable {

    public MetodoPagoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public MetodoPagoJpaController() {
        emf = Persistence.createEntityManagerFactory("Proyecto_PU");
    }

    public void create(MetodoPago metodoPago) {
        if (metodoPago.getFacturas() == null) {
            metodoPago.setFacturas(new ArrayList<Factura>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Factura> attachedFacturas = new ArrayList<Factura>();
            for (Factura facturasFacturaToAttach : metodoPago.getFacturas()) {
                facturasFacturaToAttach = em.getReference(facturasFacturaToAttach.getClass(), facturasFacturaToAttach.getIdFactura());
                attachedFacturas.add(facturasFacturaToAttach);
            }
            metodoPago.setFacturas(attachedFacturas);
            em.persist(metodoPago);
            for (Factura facturasFactura : metodoPago.getFacturas()) {
                MetodoPago oldMetodoPagoOfFacturasFactura = facturasFactura.getMetodoPago();
                facturasFactura.setMetodoPago(metodoPago);
                facturasFactura = em.merge(facturasFactura);
                if (oldMetodoPagoOfFacturasFactura != null) {
                    oldMetodoPagoOfFacturasFactura.getFacturas().remove(facturasFactura);
                    oldMetodoPagoOfFacturasFactura = em.merge(oldMetodoPagoOfFacturasFactura);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MetodoPago metodoPago) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MetodoPago persistentMetodoPago = em.find(MetodoPago.class, metodoPago.getIdMetodo());
            List<Factura> facturasOld = persistentMetodoPago.getFacturas();
            List<Factura> facturasNew = metodoPago.getFacturas();
            List<Factura> attachedFacturasNew = new ArrayList<Factura>();
            for (Factura facturasNewFacturaToAttach : facturasNew) {
                facturasNewFacturaToAttach = em.getReference(facturasNewFacturaToAttach.getClass(), facturasNewFacturaToAttach.getIdFactura());
                attachedFacturasNew.add(facturasNewFacturaToAttach);
            }
            facturasNew = attachedFacturasNew;
            metodoPago.setFacturas(facturasNew);
            metodoPago = em.merge(metodoPago);
            for (Factura facturasOldFactura : facturasOld) {
                if (!facturasNew.contains(facturasOldFactura)) {
                    facturasOldFactura.setMetodoPago(null);
                    facturasOldFactura = em.merge(facturasOldFactura);
                }
            }
            for (Factura facturasNewFactura : facturasNew) {
                if (!facturasOld.contains(facturasNewFactura)) {
                    MetodoPago oldMetodoPagoOfFacturasNewFactura = facturasNewFactura.getMetodoPago();
                    facturasNewFactura.setMetodoPago(metodoPago);
                    facturasNewFactura = em.merge(facturasNewFactura);
                    if (oldMetodoPagoOfFacturasNewFactura != null && !oldMetodoPagoOfFacturasNewFactura.equals(metodoPago)) {
                        oldMetodoPagoOfFacturasNewFactura.getFacturas().remove(facturasNewFactura);
                        oldMetodoPagoOfFacturasNewFactura = em.merge(oldMetodoPagoOfFacturasNewFactura);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = metodoPago.getIdMetodo();
                if (findMetodoPago(id) == null) {
                    throw new NonexistentEntityException("The metodoPago with id " + id + " no longer exists.");
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
            MetodoPago metodoPago;
            try {
                metodoPago = em.getReference(MetodoPago.class, id);
                metodoPago.getIdMetodo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The metodoPago with id " + id + " no longer exists.", enfe);
            }
            List<Factura> facturas = metodoPago.getFacturas();
            for (Factura facturasFactura : facturas) {
                facturasFactura.setMetodoPago(null);
                facturasFactura = em.merge(facturasFactura);
            }
            em.remove(metodoPago);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MetodoPago> findMetodoPagoEntities() {
        return findMetodoPagoEntities(true, -1, -1);
    }

    public List<MetodoPago> findMetodoPagoEntities(int maxResults, int firstResult) {
        return findMetodoPagoEntities(false, maxResults, firstResult);
    }

    private List<MetodoPago> findMetodoPagoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MetodoPago.class));
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

    public MetodoPago findMetodoPago(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MetodoPago.class, id);
        } finally {
            em.close();
        }
    }

    public int getMetodoPagoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MetodoPago> rt = cq.from(MetodoPago.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
