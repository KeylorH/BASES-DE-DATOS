package persistencia;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import logica.Distrito;
import logica.Factura;
import logica.Persona;
import persistencia.exceptions.NonexistentEntityException;

public class PersonaJpaController implements Serializable {

    private EntityManagerFactory emf = null;

    public PersonaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("Proyecto_PU");
    }

    public PersonaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Persona persona) {
        if (persona.getFacturas() == null) {
            persona.setFacturas(new ArrayList<>());
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

            List<Factura> attachedFacturas = new ArrayList<>();
            for (Factura factura : persona.getFacturas()) {
                factura = em.getReference(factura.getClass(), factura.getIdFactura());
                attachedFacturas.add(factura);
            }
            persona.setFacturas(attachedFacturas);

            em.persist(persona);

            if (distrito != null) {
                distrito.getPersonas().add(persona);
                em.merge(distrito);
            }

            for (Factura factura : persona.getFacturas()) {
                Persona oldPersona = factura.getPersona();
                factura.setPersona(persona);
                em.merge(factura);

                if (oldPersona != null) {
                    oldPersona.getFacturas().remove(factura);
                    em.merge(oldPersona);
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

            List<Factura> attachedFacturasNew = new ArrayList<>();
            for (Factura factura : facturasNew) {
                factura = em.getReference(factura.getClass(), factura.getIdFactura());
                attachedFacturasNew.add(factura);
            }
            persona.setFacturas(attachedFacturasNew);

            persona = em.merge(persona);

            if (distritoOld != null && !distritoOld.equals(distritoNew)) {
                distritoOld.getPersonas().remove(persona);
                em.merge(distritoOld);
            }

            if (distritoNew != null && !distritoNew.equals(distritoOld)) {
                distritoNew.getPersonas().add(persona);
                em.merge(distritoNew);
            }

            for (Factura factura : facturasOld) {
                if (!facturasNew.contains(factura)) {
                    factura.setPersona(null);
                    em.merge(factura);
                }
            }

            for (Factura factura : facturasNew) {
                if (!facturasOld.contains(factura)) {
                    Persona oldPersona = factura.getPersona();
                    factura.setPersona(persona);
                    em.merge(factura);

                    if (oldPersona != null && !oldPersona.equals(persona)) {
                        oldPersona.getFacturas().remove(factura);
                        em.merge(oldPersona);
                    }
                }
            }

            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPersona(persona.getIdPersona()) == null) {
                throw new NonexistentEntityException("La persona con ID " + persona.getIdPersona() + " no existe.");
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
                throw new NonexistentEntityException("La persona con ID " + id + " no existe.", enfe);
            }

            Distrito distrito = persona.getDistrito();
            if (distrito != null) {
                distrito.getPersonas().remove(persona);
                em.merge(distrito);
            }

            for (Factura factura : persona.getFacturas()) {
                factura.setPersona(null);
                em.merge(factura);
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
            CriteriaQuery<Persona> cq = em.getCriteriaBuilder().createQuery(Persona.class);
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
            CriteriaQuery<Long> cq = em.getCriteriaBuilder().createQuery(Long.class);
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
            TypedQuery<Persona> query = em.createQuery(
                    "SELECT p FROM Persona p WHERE p.nombre = :nombre AND p.password = :password AND p.isEmpleado = 1", Persona.class);
            query.setParameter("nombre", nombre);
            query.setParameter("password", password);
            return !query.getResultList().isEmpty();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Persona> findPersonasWithAllData() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Persona> query = em.createQuery(
                    "SELECT p FROM Persona p "
                    + "JOIN FETCH p.distrito d "
                    + "JOIN FETCH d.canton c "
                    + "JOIN FETCH c.provincia pr "
                    + "JOIN FETCH pr.pais pa "
                    + "WHERE p.isEmpleado = 0", Persona.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Persona> findEmpleadosWithAllData() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Persona> query = em.createQuery(
                    "SELECT p FROM Persona p "
                    + "JOIN FETCH p.distrito d "
                    + "JOIN FETCH d.canton c "
                    + "JOIN FETCH c.provincia pr "
                    + "JOIN FETCH pr.pais pa "
                    + "WHERE p.isEmpleado = 1", Persona.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public boolean validarEmailExistente(String correoElectronico) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Persona> query = em.createQuery(
                    "SELECT p FROM Persona p WHERE p.correoElectronico = :correoElectronico", Persona.class);
            query.setParameter("correoElectronico", correoElectronico);
            return !query.getResultList().isEmpty();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public boolean validarUsuarioCliente(String correo, String password) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Persona> query = em.createQuery(
                    "SELECT p FROM Persona p WHERE p.correoElectronico = :correo AND p.password = :password AND p.isEmpleado = 0",
                    Persona.class);
            query.setParameter("correo", correo);
            query.setParameter("password", password);
            return !query.getResultList().isEmpty();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Persona findPersonaByEmail(String email) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT p FROM Persona p WHERE p.email = :email", Persona.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

}
