package org.example.DAO;

import org.example.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

import org.example.entities.Animal;
import org.example.entities.EstadoAnimal;
import org.hibernate.Transaction;

public class AnimalDAOImpl implements AnimalDAO {


    /**
     * @return todos los animales
     */
    @Override
    public List<Animal> findAll() {

        // debemos de abrir la sesión
        Session session = HibernateUtil.getSessionFactory().openSession();

        //HQL (SQL de hibernate)
        List<Animal> animales = session.createQuery("from Animal", Animal.class).list();

        session.close();
        return animales;
    }

    @Override
    public Animal findById(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        // objeto que haya en la base de datos con id
        Animal animal = session.find(Animal.class, id);

        session.close();
        return animal;
    }

    @Override
    public List<Animal> findByEspecie(String especie) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<Animal> animales = session.createQuery(
                        "FROM Animal a WHERE lower(a.especie) = :esp", Animal.class)
                .setParameter("esp", especie.toLowerCase())
                .list();

        session.close();
        return animales;
    }


    @Override
    public Animal create(Animal animal) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(animal);
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
            session.update(animal);
            tx.commit();
            return animal;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return null;
        }
    }


    /**
     * @param id
     * @return borra un id concreto
     */
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

    // Metodo adicional para actualizar solo el estado
    public boolean actualizarEstado(Integer id, EstadoAnimal nuevoEstado) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Animal animal = session.get(Animal.class, id);
            if (animal == null) return false;
            tx = session.beginTransaction();
            animal.setEstado(nuevoEstado);
            session.update(animal);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }
}


