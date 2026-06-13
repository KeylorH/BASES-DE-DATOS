package persistencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.StoredProcedureQuery;
import java.io.Serializable;
import java.util.List;
import logica.ProductoBitacoraDTO;
import logica.VentasProvinciaDTO;

public class ReportesSqlJpaController implements Serializable {

    private final EntityManagerFactory emf;

    public ReportesSqlJpaController() {
        this.emf = Persistence.createEntityManagerFactory("Proyecto_PU");
    }

    public ReportesSqlJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<VentasProvinciaDTO> obtenerVentasPorProvincia() {
        try (EntityManager em = getEntityManager()) {
            StoredProcedureQuery sp = em.createNamedStoredProcedureQuery("ReporteVentasProvincia");
            return sp.getResultList();
        }
    }

    public List<ProductoBitacoraDTO> obtenerProductosBitacora() {
        try (EntityManager em = getEntityManager()) {
            StoredProcedureQuery sp = em.createNamedStoredProcedureQuery("ReporteProductosBitacora");
            return sp.getResultList();
        }
    }
}
