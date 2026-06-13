package persistencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import logica.Persona;
import logica.MetodoPago;
import logica.ProductoXFactura;
import java.util.ArrayList;
import java.util.List;
import logica.Factura;
import persistencia.exceptions.NonexistentEntityException;

public class FacturaJpaController implements Serializable {

    public FacturaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public FacturaJpaController() {
        emf = Persistence.createEntityManagerFactory("Proyecto_PU");
    }

    public void create(Factura factura) {
        if (factura.getProductosXFactura() == null) {
            factura.setProductosXFactura(new ArrayList<ProductoXFactura>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persona = factura.getPersona();
            if (persona != null) {
                persona = em.getReference(persona.getClass(), persona.getIdPersona());
                factura.setPersona(persona);
            }
            MetodoPago metodoPago = factura.getMetodoPago();
            if (metodoPago != null) {
                metodoPago = em.getReference(metodoPago.getClass(), metodoPago.getIdMetodo());
                factura.setMetodoPago(metodoPago);
            }
            List<ProductoXFactura> attachedProductosXFactura = new ArrayList<ProductoXFactura>();
            for (ProductoXFactura productosXFacturaProductoXFacturaToAttach : factura.getProductosXFactura()) {
                productosXFacturaProductoXFacturaToAttach = em.getReference(productosXFacturaProductoXFacturaToAttach.getClass(), productosXFacturaProductoXFacturaToAttach.getIdProducto());
                attachedProductosXFactura.add(productosXFacturaProductoXFacturaToAttach);
            }
            factura.setProductosXFactura(attachedProductosXFactura);
            em.persist(factura);
            if (persona != null) {
                persona.getFacturas().add(factura);
                persona = em.merge(persona);
            }
            if (metodoPago != null) {
                metodoPago.getFacturas().add(factura);
                metodoPago = em.merge(metodoPago);
            }
            for (ProductoXFactura productosXFacturaProductoXFactura : factura.getProductosXFactura()) {
                Factura oldFacturaOfProductosXFacturaProductoXFactura = productosXFacturaProductoXFactura.getFactura();
                productosXFacturaProductoXFactura.setFactura(factura);
                productosXFacturaProductoXFactura = em.merge(productosXFacturaProductoXFactura);
                if (oldFacturaOfProductosXFacturaProductoXFactura != null) {
                    oldFacturaOfProductosXFacturaProductoXFactura.getProductosXFactura().remove(productosXFacturaProductoXFactura);
                    oldFacturaOfProductosXFacturaProductoXFactura = em.merge(oldFacturaOfProductosXFacturaProductoXFactura);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Factura factura) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Factura persistentFactura = em.find(Factura.class, factura.getIdFactura());
            Persona personaOld = persistentFactura.getPersona();
            Persona personaNew = factura.getPersona();
            MetodoPago metodoPagoOld = persistentFactura.getMetodoPago();
            MetodoPago metodoPagoNew = factura.getMetodoPago();
            List<ProductoXFactura> productosXFacturaOld = persistentFactura.getProductosXFactura();
            List<ProductoXFactura> productosXFacturaNew = factura.getProductosXFactura();
            if (personaNew != null) {
                personaNew = em.getReference(personaNew.getClass(), personaNew.getIdPersona());
                factura.setPersona(personaNew);
            }
            if (metodoPagoNew != null) {
                metodoPagoNew = em.getReference(metodoPagoNew.getClass(), metodoPagoNew.getIdMetodo());
                factura.setMetodoPago(metodoPagoNew);
            }
            List<ProductoXFactura> attachedProductosXFacturaNew = new ArrayList<ProductoXFactura>();
            for (ProductoXFactura productosXFacturaNewProductoXFacturaToAttach : productosXFacturaNew) {
                productosXFacturaNewProductoXFacturaToAttach = em.getReference(productosXFacturaNewProductoXFacturaToAttach.getClass(), productosXFacturaNewProductoXFacturaToAttach.getIdProducto());
                attachedProductosXFacturaNew.add(productosXFacturaNewProductoXFacturaToAttach);
            }
            productosXFacturaNew = attachedProductosXFacturaNew;
            factura.setProductosXFactura(productosXFacturaNew);
            factura = em.merge(factura);
            if (personaOld != null && !personaOld.equals(personaNew)) {
                personaOld.getFacturas().remove(factura);
                personaOld = em.merge(personaOld);
            }
            if (personaNew != null && !personaNew.equals(personaOld)) {
                personaNew.getFacturas().add(factura);
                personaNew = em.merge(personaNew);
            }
            if (metodoPagoOld != null && !metodoPagoOld.equals(metodoPagoNew)) {
                metodoPagoOld.getFacturas().remove(factura);
                metodoPagoOld = em.merge(metodoPagoOld);
            }
            if (metodoPagoNew != null && !metodoPagoNew.equals(metodoPagoOld)) {
                metodoPagoNew.getFacturas().add(factura);
                metodoPagoNew = em.merge(metodoPagoNew);
            }
            for (ProductoXFactura productosXFacturaOldProductoXFactura : productosXFacturaOld) {
                if (!productosXFacturaNew.contains(productosXFacturaOldProductoXFactura)) {
                    productosXFacturaOldProductoXFactura.setFactura(null);
                    productosXFacturaOldProductoXFactura = em.merge(productosXFacturaOldProductoXFactura);
                }
            }
            for (ProductoXFactura productosXFacturaNewProductoXFactura : productosXFacturaNew) {
                if (!productosXFacturaOld.contains(productosXFacturaNewProductoXFactura)) {
                    Factura oldFacturaOfProductosXFacturaNewProductoXFactura = productosXFacturaNewProductoXFactura.getFactura();
                    productosXFacturaNewProductoXFactura.setFactura(factura);
                    productosXFacturaNewProductoXFactura = em.merge(productosXFacturaNewProductoXFactura);
                    if (oldFacturaOfProductosXFacturaNewProductoXFactura != null && !oldFacturaOfProductosXFacturaNewProductoXFactura.equals(factura)) {
                        oldFacturaOfProductosXFacturaNewProductoXFactura.getProductosXFactura().remove(productosXFacturaNewProductoXFactura);
                        oldFacturaOfProductosXFacturaNewProductoXFactura = em.merge(oldFacturaOfProductosXFacturaNewProductoXFactura);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = factura.getIdFactura();
                if (findFactura(id) == null) {
                    throw new NonexistentEntityException("The factura with id " + id + " no longer exists.");
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
            Factura factura;
            try {
                factura = em.getReference(Factura.class, id);
                factura.getIdFactura();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The factura with id " + id + " no longer exists.", enfe);
            }
            Persona persona = factura.getPersona();
            if (persona != null) {
                persona.getFacturas().remove(factura);
                persona = em.merge(persona);
            }
            MetodoPago metodoPago = factura.getMetodoPago();
            if (metodoPago != null) {
                metodoPago.getFacturas().remove(factura);
                metodoPago = em.merge(metodoPago);
            }
            List<ProductoXFactura> productosXFactura = factura.getProductosXFactura();
            for (ProductoXFactura productosXFacturaProductoXFactura : productosXFactura) {
                productosXFacturaProductoXFactura.setFactura(null);
                productosXFacturaProductoXFactura = em.merge(productosXFacturaProductoXFactura);
            }
            em.remove(factura);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Factura> findFacturaEntities() {
        return findFacturaEntities(true, -1, -1);
    }

    public List<Factura> findFacturaEntities(int maxResults, int firstResult) {
        return findFacturaEntities(false, maxResults, firstResult);
    }

    private List<Factura> findFacturaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Factura.class));
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

    public Factura findFactura(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Factura.class, id);
        } finally {
            em.close();
        }
    }

    public int getFacturaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Factura> rt = cq.from(Factura.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Factura> findFacturasWithAllData() {
        try (EntityManager em = getEntityManager()) {
            // Con JOIN FETCH evitamos problemas de lazy loading
            // (si las colecciones están configuradas en LAZY).
            String jpql = "SELECT f "
                    + "FROM  Factura f "
                    + "JOIN FETCH f.persona p "
                    + "JOIN FETCH f.metodoPago m ";

            Query query = em.createQuery(jpql, Factura.class);

            List<Factura> facturas = query.getResultList();
            return facturas;
        }
    }

}
