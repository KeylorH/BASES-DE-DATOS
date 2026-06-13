package persistencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import logica.Categoria;
import logica.Bitacora;
import java.util.ArrayList;
import java.util.List;
import logica.Producto;
import logica.ProductoXFactura;
import persistencia.exceptions.NonexistentEntityException;

public class ProductoJpaController implements Serializable {

    public ProductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public ProductoJpaController() {
        emf = Persistence.createEntityManagerFactory("Proyecto_PU");
    }

    public void create(Producto producto) {
        if (producto.getBitacoras() == null) {
            producto.setBitacoras(new ArrayList<Bitacora>());
        }
        if (producto.getProductosXFactura() == null) {
            producto.setProductosXFactura(new ArrayList<ProductoXFactura>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria categoria = producto.getCategoria();
            if (categoria != null) {
                categoria = em.getReference(categoria.getClass(), categoria.getIdCategoria());
                producto.setCategoria(categoria);
            }
            List<Bitacora> attachedBitacoras = new ArrayList<Bitacora>();
            for (Bitacora bitacorasBitacoraToAttach : producto.getBitacoras()) {
                bitacorasBitacoraToAttach = em.getReference(bitacorasBitacoraToAttach.getClass(), bitacorasBitacoraToAttach.getIdBitacora());
                attachedBitacoras.add(bitacorasBitacoraToAttach);
            }
            producto.setBitacoras(attachedBitacoras);
            List<ProductoXFactura> attachedProductosXFactura = new ArrayList<ProductoXFactura>();
            for (ProductoXFactura productosXFacturaProductoXFacturaToAttach : producto.getProductosXFactura()) {
                productosXFacturaProductoXFacturaToAttach = em.getReference(productosXFacturaProductoXFacturaToAttach.getClass(), productosXFacturaProductoXFacturaToAttach.getIdProducto());
                attachedProductosXFactura.add(productosXFacturaProductoXFacturaToAttach);
            }
            producto.setProductosXFactura(attachedProductosXFactura);
            em.persist(producto);
            if (categoria != null) {
                categoria.getProductos().add(producto);
                categoria = em.merge(categoria);
            }
            for (Bitacora bitacorasBitacora : producto.getBitacoras()) {
                Producto oldProductoOfBitacorasBitacora = bitacorasBitacora.getProducto();
                bitacorasBitacora.setProducto(producto);
                bitacorasBitacora = em.merge(bitacorasBitacora);
                if (oldProductoOfBitacorasBitacora != null) {
                    oldProductoOfBitacorasBitacora.getBitacoras().remove(bitacorasBitacora);
                    oldProductoOfBitacorasBitacora = em.merge(oldProductoOfBitacorasBitacora);
                }
            }
            for (ProductoXFactura productosXFacturaProductoXFactura : producto.getProductosXFactura()) {
                Producto oldProductoOfProductosXFacturaProductoXFactura = productosXFacturaProductoXFactura.getProducto();
                productosXFacturaProductoXFactura.setProducto(producto);
                productosXFacturaProductoXFactura = em.merge(productosXFacturaProductoXFactura);
                if (oldProductoOfProductosXFacturaProductoXFactura != null) {
                    oldProductoOfProductosXFacturaProductoXFactura.getProductosXFactura().remove(productosXFacturaProductoXFactura);
                    oldProductoOfProductosXFacturaProductoXFactura = em.merge(oldProductoOfProductosXFacturaProductoXFactura);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Producto producto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto persistentProducto = em.find(Producto.class, producto.getIdProducto());
            Categoria categoriaOld = persistentProducto.getCategoria();
            Categoria categoriaNew = producto.getCategoria();
            List<Bitacora> bitacorasOld = persistentProducto.getBitacoras();
            List<Bitacora> bitacorasNew = producto.getBitacoras();
            List<ProductoXFactura> productosXFacturaOld = persistentProducto.getProductosXFactura();
            List<ProductoXFactura> productosXFacturaNew = producto.getProductosXFactura();
            if (categoriaNew != null) {
                categoriaNew = em.getReference(categoriaNew.getClass(), categoriaNew.getIdCategoria());
                producto.setCategoria(categoriaNew);
            }
            List<Bitacora> attachedBitacorasNew = new ArrayList<Bitacora>();
            for (Bitacora bitacorasNewBitacoraToAttach : bitacorasNew) {
                bitacorasNewBitacoraToAttach = em.getReference(bitacorasNewBitacoraToAttach.getClass(), bitacorasNewBitacoraToAttach.getIdBitacora());
                attachedBitacorasNew.add(bitacorasNewBitacoraToAttach);
            }
            bitacorasNew = attachedBitacorasNew;
            producto.setBitacoras(bitacorasNew);

            List<ProductoXFactura> attachedProductosXFacturaNew = new ArrayList<ProductoXFactura>();
            for (ProductoXFactura pxFactura : productosXFacturaNew) {
                // Usar la clave primaria compuesta en lugar de getIdProducto()
                attachedProductosXFacturaNew.add(
                        em.getReference(pxFactura.getClass(), pxFactura.getProductoXFacturaPK())
                );
            }

            productosXFacturaNew = attachedProductosXFacturaNew;
            producto.setProductosXFactura(productosXFacturaNew);

            producto = em.merge(producto);
            if (categoriaOld != null && !categoriaOld.equals(categoriaNew)) {
                categoriaOld.getProductos().remove(producto);
                categoriaOld = em.merge(categoriaOld);
            }
            if (categoriaNew != null && !categoriaNew.equals(categoriaOld)) {
                categoriaNew.getProductos().add(producto);
                categoriaNew = em.merge(categoriaNew);
            }
            for (Bitacora bitacorasOldBitacora : bitacorasOld) {
                if (!bitacorasNew.contains(bitacorasOldBitacora)) {
                    bitacorasOldBitacora.setProducto(null);
                    bitacorasOldBitacora = em.merge(bitacorasOldBitacora);
                }
            }
            for (Bitacora bitacorasNewBitacora : bitacorasNew) {
                if (!bitacorasOld.contains(bitacorasNewBitacora)) {
                    Producto oldProductoOfBitacorasNewBitacora = bitacorasNewBitacora.getProducto();
                    bitacorasNewBitacora.setProducto(producto);
                    bitacorasNewBitacora = em.merge(bitacorasNewBitacora);
                    if (oldProductoOfBitacorasNewBitacora != null && !oldProductoOfBitacorasNewBitacora.equals(producto)) {
                        oldProductoOfBitacorasNewBitacora.getBitacoras().remove(bitacorasNewBitacora);
                        oldProductoOfBitacorasNewBitacora = em.merge(oldProductoOfBitacorasNewBitacora);
                    }
                }
            }
            for (ProductoXFactura productosXFacturaOldProductoXFactura : productosXFacturaOld) {
                if (!productosXFacturaNew.contains(productosXFacturaOldProductoXFactura)) {
                    productosXFacturaOldProductoXFactura.setProducto(null);
                    productosXFacturaOldProductoXFactura = em.merge(productosXFacturaOldProductoXFactura);
                }
            }
            for (ProductoXFactura productosXFacturaNewProductoXFactura : productosXFacturaNew) {
                if (!productosXFacturaOld.contains(productosXFacturaNewProductoXFactura)) {
                    Producto oldProductoOfProductosXFacturaNewProductoXFactura = productosXFacturaNewProductoXFactura.getProducto();
                    productosXFacturaNewProductoXFactura.setProducto(producto);
                    productosXFacturaNewProductoXFactura = em.merge(productosXFacturaNewProductoXFactura);
                    if (oldProductoOfProductosXFacturaNewProductoXFactura != null && !oldProductoOfProductosXFacturaNewProductoXFactura.equals(producto)) {
                        oldProductoOfProductosXFacturaNewProductoXFactura.getProductosXFactura().remove(productosXFacturaNewProductoXFactura);
                        oldProductoOfProductosXFacturaNewProductoXFactura = em.merge(oldProductoOfProductosXFacturaNewProductoXFactura);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = producto.getIdProducto();
                if (findProducto(id) == null) {
                    throw new NonexistentEntityException("The producto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void editBasico(Producto producto) throws Exception {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();

            Producto productoDB = em.find(Producto.class, producto.getIdProducto());
            if (productoDB == null) {
                throw new Exception("Producto no encontrado con ID: " + producto.getIdProducto());
            }

            productoDB.setNombre(producto.getNombre());
            productoDB.setPrecio(producto.getPrecio());
            productoDB.setCantidad(producto.getCantidad());

            em.merge(productoDB);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();

            // 1) Comprobar existencia (opcional)
            Integer count = ((Number) em.createNativeQuery(
                    "SELECT COUNT(1) FROM PRODUCTO WHERE ID_PRODUCTO = ?")
                    .setParameter(1, id)
                    .getSingleResult()).intValue();
            if (count == 0) {
                throw new NonexistentEntityException("El producto con id " + id + " no existe.");
            }

            // 2) Borrar el producto (y en cascada, las bitácoras)
            int deleted = em.createNativeQuery(
                    "DELETE FROM PRODUCTO WHERE ID_PRODUCTO = ?")
                    .setParameter(1, id)
                    .executeUpdate();
            System.out.println("Filas PRODUCTO borradas: " + deleted);

            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    public List<Producto> findProductoEntities() {
        return findProductoEntities(true, -1, -1);
    }

    public List<Producto> findProductoEntities(int maxResults, int firstResult) {
        return findProductoEntities(false, maxResults, firstResult);
    }

    private List<Producto> findProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Producto.class));
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

    public Producto findProducto(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Producto> rt = cq.from(Producto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
