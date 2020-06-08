package ro.ubb.catalog.core.service;

import ro.ubb.catalog.core.model.domain.Client;

import java.util.Collection;

public interface IClientService extends IService<Integer, Client> {
    Collection<Client> filterClientsByAge(int age);
}
