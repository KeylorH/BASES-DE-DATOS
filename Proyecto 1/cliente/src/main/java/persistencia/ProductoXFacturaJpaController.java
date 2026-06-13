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
import logica.Producto;
import logica.Factura;
import logica.ProductoXFactura;
import persistencia.exceptions.NonexistentEntityException;
import persistencia.exceptions.PreexistingEntityException;

public class ProductoXFacturaJpaController implements Serializable {

    public ProductoXFacturaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public ProductoXFacturaJpaController() {
        emf = Persistence.createEntityManagerFactory("Proyecto_PU");
    }

    public void create(ProductoXFactura productoXFactura) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto producto = productoXFactura.getProducto();
            if (producto != null) {
                producto = em.getReference(producto.getClass(), producto.getIdProducto());
                productoXFactura.setProducto(producto);
            }
            Factura factura = productoXFactura.getFactura();
            if (factura != null) {
                factura = em.getReference(factura.getClass(), factura.getIdFactura());
                productoXFactura.setFactura(factura);
            }
            em.persist(productoXFactura);
            if (producto != null) {
                producto.getProductosXFactura().add(productoXFactura);
                producto = em.merge(producto);
            }
            if (factura != null) {
                factura.getProductosXFactura().add(productoXFactura);
                factura = em.merge(factura);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProductoXFactura(productoXFactura.getIdProducto()) != null) {
                throw new PreexistingEntityException("ProductoXFactura " + productoXFactura + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ProductoXFactura productoXFactura) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProductoXFactura persistentProductoXFactura = em.find(ProductoXFactura.class, productoXFactura.getIdProducto());
            Producto productoOld = persistentProductoXFactura.getProducto();
            Producto productoNew = productoXFactura.getProducto();
            Factura facturaOld = persistentProductoXFactura.getFactura();
            Factura facturaNew = productoXFactura.getFactura();
            if (productoNew != null) {
                productoNew = em.getReference(productoNew.getClass(), productoNew.getIdProducto());
                productoXFactura.setProducto(productoNew);
            }
            if (facturaNew != null) {
                facturaNew = em.getReference(facturaNew.getClass(), facturaNew.getIdFactura());
                productoXFactura.setFactura(facturaNew);
            }
            productoXFactura = em.merge(productoXFactura);
            if (productoOld != null && !productoOld.equals(productoNew)) {
                productoOld.getProductosXFactura().remove(productoXFactura);
                productoOld = em.merge(productoOld);
            }
            if (productoNew != null && !productoNew.equals(productoOld)) {
                productoNew.getProductosXFactura().add(productoXFactura);
                productoNew = em.merge(productoNew);
            }
            if (facturaOld != null && !facturaOld.equals(facturaNew)) {
                facturaOld.getProductosXFactura().remove(productoXFactura);
                facturaOld = em.merge(facturaOld);
            }
            if (facturaNew != null && !facturaNew.equals(facturaOld)) {
                facturaNew.getProductosXFactura().add(productoXFactura);
                facturaNew = em.merge(facturaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = productoXFactura.getIdProducto();
                if (findProductoXFactura(id) == null) {
                    throw new NonexistentEntityException("The productoXFactura with id " + id + " no longer exists.");
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
            ProductoXFactura productoXFactura;
            try {
                productoXFactura = em.getReference(ProductoXFactura.class, id);
                productoXFactura.getIdProducto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The productoXFactura with id " + id + " no longer exists.", enfe);
            }
            Producto producto = productoXFactura.getProducto();
            if (producto != null) {
                producto.getProductosXFactura().remove(productoXFactura);
                producto = em.merge(producto);
            }
            Factura factura = productoXFactura.getFactura();
            if (factura != null) {
                factura.getProductosXFactura().remove(productoXFactura);
                factura = em.merge(factura);
            }
            em.remove(productoXFactura);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ProductoXFactura> findProductoXFacturaEntities() {
        return findProductoXFacturaEntities(true, -1, -1);
    }

    public List<ProductoXFactura> findProductoXFacturaEntities(int maxResults, int firstResult) {
        return findProductoXFacturaEntities(false, maxResults, firstResult);
    }

    private List<ProductoXFactura> findProductoXFacturaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ProductoXFactura.class));
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

    public ProductoXFactura findProductoXFactura(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ProductoXFactura.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoXFacturaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ProductoXFactura> rt = cq.from(ProductoXFactura.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
