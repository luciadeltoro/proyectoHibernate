package org.example.DAO;

import org.example.entities.Clasificacion;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ClasificacionDAOImpl implements ClasificacionDAO {

    @Override
    public Clasificacion findById(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Clasificacion.class, id);
        }
    }

    @Override
    public Clasificacion findByCodigo(String codigo) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Clasificacion c WHERE c.codigo = :codigo", Clasificacion.class)
                    .setParameter("codigo", codigo)
                    .uniqueResult();
        }
    }

    @Override
    public Clasificacion create(Clasificacion c) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(c);
            tx.commit();
            return c;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Clasificacion update(Clasificacion c) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.update(c);
            tx.commit();
            return c;
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
            Clasificacion c = session.get(Clasificacion.class, id);
            if (c == null) return false;
            tx = session.beginTransaction();
            session.delete(c);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Clasificacion> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Clasificacion", Clasificacion.class).list();
        }
    }
}
