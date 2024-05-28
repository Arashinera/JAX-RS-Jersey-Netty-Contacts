package app.repository.impl;

import app.config.HibernateUtil;
import app.domain.contact.Contact;
import app.repository.AppRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContactRepository implements AppRepository<Contact> {

    private static final Logger LOGGER =
            Logger.getLogger(ContactRepository.class.getName());

    @Override
    public void create(Contact contact) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String hql = "INSERT INTO Contact (firstName, lastName, phoneNumber) " +
                    "VALUES (:firstName, :lastName, :phoneNumber)";
            MutationQuery query = session.createMutationQuery(hql);
            query.setParameter("firstName", contact.getFirstName());
            query.setParameter("lastName", contact.getLastName());
            query.setParameter("phoneNumber", contact.getPhoneNumber());
            query.executeUpdate();

            transaction.commit();
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.WARNING, exception.getMessage(), exception);
        }
    }

    @Override
    public Optional<List<Contact>> fetchAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction;

            transaction = session.beginTransaction();
            List<Contact> list =
                    session.createQuery("FROM Contact", Contact.class).list();

            transaction.commit();

            return Optional.of(list);
        } catch (Exception exception) {
            return Optional.empty();
        }
    }

    // ---- Path Params ----------------------

    @Override
    public Optional<Contact> fetchById(Long id) {
        Transaction transaction;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
            Query<Contact> query = session.createQuery("FROM Contact WHERE id = :id", Contact.class);
            query.setParameter("id", id);
            query.setMaxResults(1);
            Contact contact = query.uniqueResult();

            transaction.commit();

            return Optional.ofNullable(contact);
        } catch (Exception exception) {
            LOGGER.log(Level.WARNING, exception.getMessage(), exception);

            return Optional.empty();
        }
    }

    @Override
    public void update(Long id, Contact contact) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
            String hql = "UPDATE Contact SET firstName = :firstName," +
                    " lastName = :lastName, phoneNumber = :phoneNumber" +
                    " WHERE id = :id";
            MutationQuery query = session.createMutationQuery(hql);
            query.setParameter("firstName", contact.getFirstName());
            query.setParameter("lastName", contact.getLastName());
            query.setParameter("phoneNumber", contact.getPhoneNumber());
            query.setParameter("id", id);
            query.executeUpdate();
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.WARNING, exception.getMessage(), exception);
        }
    }

    @Override
    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
            String hql = "DELETE FROM Contact WHERE id = :id";
            MutationQuery query = session.createMutationQuery(hql);
            query.setParameter("id", id);
            query.executeUpdate();

            transaction.commit();
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.WARNING, exception.getMessage(), exception);
        }
    }

    public Optional<List<Contact>> fetchByFirstName(String firstName) {
        String hql = "FROM Contact WHERE firstName = :firstName";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction;

            transaction = session.beginTransaction();
            Query<Contact> query = session.createQuery(hql, Contact.class);
            query.setParameter("firstName", firstName);
            List<Contact> list = query.list();

            transaction.commit();

            return Optional.of(list);
        } catch (Exception exception) {
            return Optional.empty();
        }
    }

    public Optional<List<Contact>> fetchByLastName(String lastName) {
        String hql = "FROM Contact WHERE lastName = :lastName";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction;

            transaction = session.beginTransaction();
            Query<Contact> query = session.createQuery(hql, Contact.class);
            query.setParameter("lastName", lastName);
            List<Contact> list = query.list();

            transaction.commit();

            return Optional.of(list);
        } catch (Exception exception) {
            return Optional.empty();
        }
    }

    public Optional<List<Contact>> fetchAllOrderBy(String orderBy) {
        String hql = "FROM Contact ORDER BY " + orderBy;
        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction;

            transaction = session.beginTransaction();
            List<Contact> list =
                    session.createQuery(hql, Contact.class)
                            .list();

            transaction.commit();

            return Optional.of(list);
        } catch (Exception exception) {
            return Optional.empty();
        }
    }

    public Optional<List<Contact>> fetchByLastNameOrderBy(String lastName, String orderBy) {
        String hql = "FROM Contact WHERE lastName = :lastName ORDER BY " + orderBy;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction;

            transaction = session.beginTransaction();
            List<Contact> list = session.createQuery(hql, Contact.class)
                    .setParameter("lastName", lastName)
                    .list();

            transaction.commit();

            return Optional.of(list);
        } catch (Exception exception) {
            return Optional.empty();
        }
    }

    public Optional<List<Contact>> fetchBetweenIds(Integer from, Integer to) {
        String hql = "FROM Contact u WHERE u.id BETWEEN :from AND :to";
        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction;

            transaction = session.beginTransaction();
            List<Contact> list =
                    session.createQuery(hql, Contact.class)
                            .setParameter("from", from)
                            .setParameter("to", to)
                            .list();

            transaction.commit();

            return Optional.of(list);
        } catch (Exception exception) {
            return Optional.empty();
        }
    }

    public Optional<List<Contact>> fetchLastNameIn(String lastName1, String lastName2) {
        String hql = "FROM Contact u WHERE u.lastName IN (:lastName1, :lastName2)";
        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction;

            transaction = session.beginTransaction();
            List<Contact> list =
                    session.createQuery(hql, Contact.class)
                            .setParameter("lastName1", lastName1)
                            .setParameter("lastName2", lastName2)
                            .list();

            transaction.commit();

            return Optional.of(list);
        } catch (Exception exception) {
            return Optional.empty();
        }
    }

    public boolean isIdExists(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Contact contact = new Contact();
            contact.setId(id);
            contact = session.get(Contact.class, contact.getId());

            if (contact != null) {
                Query<Contact> query = session.createQuery("FROM Contact", Contact.class);
                query.setMaxResults(1);
                query.getResultList();
            }

            return contact != null;
        }
    }

    public Optional<Contact> getLastEntity() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
            Query<Contact> query = session.createQuery("FROM Contact ORDER BY id DESC", Contact.class);
            query.setMaxResults(1);
            Contact contact = query.uniqueResult();

            transaction.commit();

            return Optional.of(contact);
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.WARNING, exception.getMessage(), exception);

            return Optional.empty();
        }
    }
}
