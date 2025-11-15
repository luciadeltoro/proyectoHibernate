package org.example.DAO;

import org.example.entities.Animal;
import org.example.entities.Clasificacion;
import org.example.entities.EstadoAnimal;
import org.example.entities.Persona;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class AnimalDAOImpl implements AnimalDAO {

    @Override
    public List<Animal> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Trae animales con dueños y clasificaciones para evitar LazyInitializationException
            return session.createQuery(
                            "SELECT DISTINCT a FROM Animal a " +
                                    "LEFT JOIN FETCH a.clasificaciones " +
                                    "LEFT JOIN FETCH a.dueno", Animal.class)
                    .list();
        }
    }

    @Override
    public Animal findById(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT a FROM Animal a " +
                                    "LEFT JOIN FETCH a.clasificaciones " +
                                    "LEFT JOIN FETCH a.dueno " +
                                    "WHERE a.id = :id", Animal.class)
                    .setParameter("id", id)
                    .uniqueResult();
        }
    }

    @Override
    public List<Animal> findByEspecie(String especie) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT DISTINCT a FROM Animal a " +
                                    "LEFT JOIN FETCH a.clasificaciones " +
                                    "LEFT JOIN FETCH a.dueno " +
                                    "WHERE lower(a.especie) = :esp", Animal.class)
                    .setParameter("esp", especie.toLowerCase())
                    .list();
        }
    }

    @Override
    public Animal create(Animal animal) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(animal);
            tx.commit();
            return animal;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Animal update(Animal animal) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(animal);
            tx.commit();
            return animal;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Animal animal = session.get(Animal.class, id);
            if (animal == null) return false;
            tx = session.beginTransaction();
            session.delete(animal);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Animal> findByOwnerId(Integer personaId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT DISTINCT a FROM Animal a " +
                                    "LEFT JOIN FETCH a.clasificaciones " +
                                    "LEFT JOIN FETCH a.dueno " +
                                    "WHERE a.dueno.id = :pid", Animal.class)
                    .setParameter("pid", personaId)
                    .list();
        }
    }

    @Override
    public List<Animal> findByClasificacionCodigo(String codigo) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT DISTINCT a FROM Animal a " +
                                    "JOIN FETCH a.clasificaciones c " +
                                    "LEFT JOIN FETCH a.dueno " +
                                    "WHERE c.codigo = :cod", Animal.class)
                    .setParameter("cod", codigo)
                    .list();
        }
    }

    @Override
    public void assignOwner(Integer animalId, Integer personaId) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Animal animal = session.get(Animal.class, animalId);
            Persona persona = session.get(Persona.class, personaId);
            if (animal == null || persona == null) return;
            tx = session.beginTransaction();
            animal.setDueno(persona);
            persona.getAnimales().add(animal); // mantener relación bidireccional
            session.merge(animal);
            session.merge(persona);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void addClasificacionToAnimal(Integer animalId, Integer clasificacionId) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Animal animal = session.get(Animal.class, animalId);
            Clasificacion clasificacion = session.get(Clasificacion.class, clasificacionId);
            if (animal == null || clasificacion == null) return;
            tx = session.beginTransaction();
            animal.addClasificacion(clasificacion); // helper maneja la bidireccionalidad
            session.merge(animal);
            session.merge(clasificacion);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public boolean actualizarEstado(Integer id, EstadoAnimal nuevoEstado) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Animal animal = session.get(Animal.class, id);
            if (animal == null) return false;
            tx = session.beginTransaction();
            animal.setEstado(nuevoEstado);
            session.merge(animal);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }
}
