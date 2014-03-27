package com.pm.test.j2ee.dao.impl;

import java.net.UnknownHostException;
import java.util.Collection;

import javax.annotation.PostConstruct;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.DatastoreImpl;
import org.mongodb.morphia.Morphia;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.pm.test.j2ee.services.GenericCRUD;

public class GenericDaoImpl<T> implements GenericCRUD<T> {
	private Class<T> entityClass;
	private Datastore datastore;

	public GenericDaoImpl(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	@PostConstruct
	public void initMorphia() throws UnknownHostException {
		Mongo mongo = new MongoClient("localhost", 27017);
		Morphia morphia = new Morphia();
		morphia.map(entityClass);
		datastore = new DatastoreImpl(morphia, mongo, "test");
	}

	@Override
	public T create(T entity) {
		datastore.save(entity);
		return entity;
	}

	@Override
	public T get(String key) {
		return datastore.get(entityClass, key);
	}

	@Override
	public T update(T entity) {
		datastore.save(entity);
		return entity;
	}

	@Override
	public void delete(T entity) {
		datastore.delete(entity);
	}

	@Override
	public Collection<T> getAll() {
		return datastore.find(entityClass).asList();
	}
}
