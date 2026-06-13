package persistencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import logica.Factura;
import logica.MetodoPago;
import logica.Persona;
import logica.ProductoXFactura;
import logica.ProductoXFacturaPK;
import persistencia.exceptions.NonexistentEntityException;

public class FacturaJpaController implements Serializable {

    private EntityManagerFactory emf = null;

    public FacturaJpaController() {
        emf = Persistence.createEntityManagerFactory("Proyecto_PU");
    }

    public FacturaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Factura factura) {
        if (factura.getProductosXFactura() == null) {
            factura.setProductosXFactura(new ArrayList<>());
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

            List<ProductoXFactura> attachedProductosXFactura = new ArrayList<>();
            for (ProductoXFactura pxFactura : factura.getProductosXFactura()) {
                ProductoXFacturaPK pk = new ProductoXFacturaPK(
                    pxFactura.getIdProducto(),
                    factura.getIdFactura()
                );
                pxFactura = em.getReference(pxFactura.getClass(), pk);
                attachedProductosXFactura.add(pxFactura);
            }
            factura.setProductosXFactura(attachedProductosXFactura);

            em.persist(factura);

            if (persona != null) {
                persona.getFacturas().add(factura);
                em.merge(persona);
            }

            if (metodoPago != null) {
                metodoPago.getFacturas().add(factura);
                em.merge(metodoPago);
            }

            for (ProductoXFactura pxFactura : factura.getProductosXFactura()) {
                Factura oldFactura = pxFactura.getFactura();
                pxFactura.setFactura(factura);
                pxFactura = em.merge(pxFactura);
                if (oldFactura != null && !oldFactura.equals(factura)) {
                    oldFactura.getProductosXFactura().remove(pxFactura);
                    em.merge(oldFactura);
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

            List<ProductoXFactura> attachedProductosXFacturaNew = new ArrayList<>();
            for (ProductoXFactura pxFactura : productosXFacturaNew) {
                ProductoXFacturaPK pk = new ProductoXFacturaPK(
                    pxFactura.getIdProducto(),
                    factura.getIdFactura()
                );
                pxFactura = em.getReference(pxFactura.getClass(), pk);
                attachedProductosXFacturaNew.add(pxFactura);
            }
            productosXFacturaNew = attachedProductosXFacturaNew;
            factura.setProductosXFactura(productosXFacturaNew);

            factura = em.merge(factura);

            if (personaOld != null && !personaOld.equals(personaNew)) {
                personaOld.getFacturas().remove(factura);
                em.merge(personaOld);
            }

            if (personaNew != null && !personaNew.equals(personaOld)) {
                personaNew.getFacturas().add(factura);
                em.merge(personaNew);
            }

            if (metodoPagoOld != null && !metodoPagoOld.equals(metodoPagoNew)) {
                metodoPagoOld.getFacturas().remove(factura);
                em.merge(metodoPagoOld);
            }

            if (metodoPagoNew != null && !metodoPagoNew.equals(metodoPagoOld)) {
                metodoPagoNew.getFacturas().add(factura);
                em.merge(metodoPagoNew);
            }

            for (ProductoXFactura pxFactura : productosXFacturaOld) {
                if (!productosXFacturaNew.contains(pxFactura)) {
                    pxFactura.setFactura(null);
                    em.merge(pxFactura);
                }
            }

            for (ProductoXFactura pxFactura : productosXFacturaNew) {
                if (!productosXFacturaOld.contains(pxFactura)) {
                    Factura oldFactura = pxFactura.getFactura();
                    pxFactura.setFactura(factura);
                    pxFactura = em.merge(pxFactura);
                    if (oldFactura != null && !oldFactura.equals(factura)) {
                        oldFactura.getProductosXFactura().remove(pxFactura);
                        em.merge(oldFactura);
                    }
                }
            }

            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFactura(factura.getIdFactura()) == null) {
                throw new NonexistentEntityException("The factura with id " + factura.getIdFactura() + " no longer exists.");
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
                em.merge(persona);
            }

            MetodoPago metodoPago = factura.getMetodoPago();
            if (metodoPago != null) {
                metodoPago.getFacturas().remove(factura);
                em.merge(metodoPago);
            }

            List<ProductoXFactura> productosXFactura = factura.getProductosXFactura();
            for (ProductoXFactura pxFactura : productosXFactura) {
                pxFactura.setFactura(null);
                em.merge(pxFactura);
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
            String jpql = "SELECT f FROM Factura f "
                    + "JOIN FETCH f.persona p "
                    + "JOIN FETCH f.metodoPago m";

            Query query = em.createQuery(jpql, Factura.class);
            return query.getResultList();
        }
    }

    public Factura obtenerFacturaEnProcesoPorEmail(String email) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT f FROM Factura f WHERE f.persona.email = :email AND f.estado = 'proceso'";
            Query query = em.createQuery(jpql, Factura.class);
            query.setParameter("email", email);
            List<Factura> resultados = query.getResultList();
            return resultados.isEmpty() ? null : resultados.get(0);
        } finally {
            em.close();
        }
    }
}
