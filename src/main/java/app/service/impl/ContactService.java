package app.service.impl;

import app.domain.contact.Contact;
import app.repository.impl.ContactRepository;
import app.service.AppService;

import java.util.Collections;
import java.util.List;

public class ContactService implements AppService<Contact> {

    ContactRepository repository = new ContactRepository();

    public Contact create(Contact contact) {
        repository.create(contact);

        return repository.getLastEntity()
                .orElse(null);
    }

    public List<Contact> fetchAll() {
        return repository.fetchAll()
                .orElse(Collections.emptyList());
    }

    public Contact fetchById(Long id) {
        return repository.fetchById(id).orElse(null);
    }

    public Contact update(Long id, Contact contact) {
        if (repository.fetchById(id).isPresent()) {
            repository.update(id, contact);
        }

        return repository.fetchById(id).orElse(null);
    }

    public boolean delete(Long id) {
        if (repository.isIdExists(id)) {
            repository.delete(id);

            return true;
        } else return false;
    }

    public List<Contact> fetchByFirstName(String firstName) {
        return repository.fetchByFirstName(firstName)
                .orElse(Collections.emptyList());
    }

    public List<Contact> fetchByLastName(String lastName) {
        return repository.fetchByLastName(lastName)
                .orElse(Collections.emptyList());
    }

    public List<Contact> fetchAllOrderBy(String orderBy) {
        return repository.fetchAllOrderBy(orderBy)
                .orElse(Collections.emptyList());
    }

    public List<Contact> fetchByLastNameOrderBy(String lastName,
                                             String orderBy) {
        return repository.fetchByLastNameOrderBy(lastName,
                orderBy).orElse(Collections.emptyList());
    }

    public List<Contact> fetchBetweenIds(int from, int to) {
        return repository.fetchBetweenIds(from, to)
                .orElse(Collections.emptyList());
    }

    public List<Contact> fetchLastNameIn(String lastName1,
                                      String lastName2) {
        return repository.fetchLastNameIn(lastName1,
                        lastName2)
                .orElse(Collections.emptyList());
    }
}
