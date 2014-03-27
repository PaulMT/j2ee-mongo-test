package com.pm.test.j2ee.dao.impl;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.DatastoreImpl;
import org.mongodb.morphia.Morphia;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.pm.test.j2ee.services.GenericCRUD;

public class GenericDaoImpl<T> implements GenericCRUD<T> {
	private Class<? extends T> entityClass;
	private Datastore datastore;

	public GenericDaoImpl(Class<? extends T> entityClass) {
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
	public T getById(Long id) {
		return datastore.find(entityClass, "id", id).get();
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
		List<? extends T> list = datastore.find(entityClass).asList();
		List<T> entityList = new ArrayList<>();
		for (T entity : list) {
			entityList.add(entity);
		}
		return entityList;
	}
}
