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
import logica.Factura;
import java.util.ArrayList;
import java.util.List;
import logica.Persona;
import persistencia.exceptions.NonexistentEntityException;

public class PersonaJpaController implements Serializable {

    public PersonaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public PersonaJpaController() {
        emf = Persistence.createEntityManagerFactory("Proyecto_PU");
    }

    public void create(Persona persona) {
        if (persona.getFacturas() == null) {
            persona.setFacturas(new ArrayList<Factura>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Distrito distrito = persona.getDistrito();
            if (distrito != null) {
                distrito = em.getReference(distrito.getClass(), distrito.getIdDistrito());
                persona.setDistrito(distrito);
            }
            List<Factura> attachedFacturas = new ArrayList<Factura>();
            for (Factura facturasFacturaToAttach : persona.getFacturas()) {
                facturasFacturaToAttach = em.getReference(facturasFacturaToAttach.getClass(), facturasFacturaToAttach.getIdFactura());
                attachedFacturas.add(facturasFacturaToAttach);
            }
            persona.setFacturas(attachedFacturas);
            em.persist(persona);
            if (distrito != null) {
                distrito.getPersonas().add(persona);
                distrito = em.merge(distrito);
            }
            for (Factura facturasFactura : persona.getFacturas()) {
                Persona oldPersonaOfFacturasFactura = facturasFactura.getPersona();
                facturasFactura.setPersona(persona);
                facturasFactura = em.merge(facturasFactura);
                if (oldPersonaOfFacturasFactura != null) {
                    oldPersonaOfFacturasFactura.getFacturas().remove(facturasFactura);
                    oldPersonaOfFacturasFactura = em.merge(oldPersonaOfFacturasFactura);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Persona persona) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persistentPersona = em.find(Persona.class, persona.getIdPersona());
            Distrito distritoOld = persistentPersona.getDistrito();
            Distrito distritoNew = persona.getDistrito();
            List<Factura> facturasOld = persistentPersona.getFacturas();
            List<Factura> facturasNew = persona.getFacturas();
            if (distritoNew != null) {
                distritoNew = em.getReference(distritoNew.getClass(), distritoNew.getIdDistrito());
                persona.setDistrito(distritoNew);
            }
            List<Factura> attachedFacturasNew = new ArrayList<Factura>();
            for (Factura facturasNewFacturaToAttach : facturasNew) {
                facturasNewFacturaToAttach = em.getReference(facturasNewFacturaToAttach.getClass(), facturasNewFacturaToAttach.getIdFactura());
                attachedFacturasNew.add(facturasNewFacturaToAttach);
            }
            facturasNew = attachedFacturasNew;
            persona.setFacturas(facturasNew);
            persona = em.merge(persona);
            if (distritoOld != null && !distritoOld.equals(distritoNew)) {
                distritoOld.getPersonas().remove(persona);
                distritoOld = em.merge(distritoOld);
            }
            if (distritoNew != null && !distritoNew.equals(distritoOld)) {
                distritoNew.getPersonas().add(persona);
                distritoNew = em.merge(distritoNew);
            }
            for (Factura facturasOldFactura : facturasOld) {
                if (!facturasNew.contains(facturasOldFactura)) {
                    facturasOldFactura.setPersona(null);
                    facturasOldFactura = em.merge(facturasOldFactura);
                }
            }
            for (Factura facturasNewFactura : facturasNew) {
                if (!facturasOld.contains(facturasNewFactura)) {
                    Persona oldPersonaOfFacturasNewFactura = facturasNewFactura.getPersona();
                    facturasNewFactura.setPersona(persona);
                    facturasNewFactura = em.merge(facturasNewFactura);
                    if (oldPersonaOfFacturasNewFactura != null && !oldPersonaOfFacturasNewFactura.equals(persona)) {
                        oldPersonaOfFacturasNewFactura.getFacturas().remove(facturasNewFactura);
                        oldPersonaOfFacturasNewFactura = em.merge(oldPersonaOfFacturasNewFactura);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = persona.getIdPersona();
                if (findPersona(id) == null) {
                    throw new NonexistentEntityException("The persona with id " + id + " no longer exists.");
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
            Persona persona;
            try {
                persona = em.getReference(Persona.class, id);
                persona.getIdPersona();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The persona with id " + id + " no longer exists.", enfe);
            }
            Distrito distrito = persona.getDistrito();
            if (distrito != null) {
                distrito.getPersonas().remove(persona);
                distrito = em.merge(distrito);
            }
            List<Factura> facturas = persona.getFacturas();
            for (Factura facturasFactura : facturas) {
                facturasFactura.setPersona(null);
                facturasFactura = em.merge(facturasFactura);
            }
            em.remove(persona);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Persona> findPersonaEntities() {
        return findPersonaEntities(true, -1, -1);
    }

    public List<Persona> findPersonaEntities(int maxResults, int firstResult) {
        return findPersonaEntities(false, maxResults, firstResult);
    }

    private List<Persona> findPersonaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Persona.class));
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

    public Persona findPersona(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Persona.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Persona> rt = cq.from(Persona.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public boolean validarUsuarioEmpleado(String nombre, String password) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT p FROM Persona p WHERE p.nombre = :nombre AND p.password = :password AND p.isEmpleado = 1");
            query.setParameter("nombre", nombre);
            query.setParameter("password", password);
            List<Persona> resultado = query.getResultList();
            return !resultado.isEmpty();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Persona> findPersonasWithAllData() {
        try (EntityManager em = getEntityManager()) {
            // Con JOIN FETCH evitamos problemas de lazy loading
            // (si las colecciones están configuradas en LAZY).
            String jpql = "SELECT p "
                    + "FROM Persona p "
                    + "JOIN FETCH p.distrito d "
                    + "JOIN FETCH d.canton c "
                    + "JOIN FETCH c.provincia pr "
                    + "JOIN FETCH pr.pais pa "
                    + "WHERE p.isEmpleado = 0";

            Query query = em.createQuery(jpql, Persona.class);

            List<Persona> personas = query.getResultList();
            return personas;
        }
    }

    public List<Persona> findEmpleadosWithAllData() {
        try (EntityManager em = getEntityManager()) {
            // Con JOIN FETCH evitamos problemas de lazy loading
            // (si las colecciones están configuradas en LAZY).
            String jpql = "SELECT p "
                    + "FROM Persona p "
                    + "JOIN FETCH p.distrito d "
                    + "JOIN FETCH d.canton c "
                    + "JOIN FETCH c.provincia pr "
                    + "JOIN FETCH pr.pais pa "
                    + "WHERE p.isEmpleado = 1";

            Query query = em.createQuery(jpql, Persona.class);

            List<Persona> personas = query.getResultList();
            return personas;
        }
    }

}
