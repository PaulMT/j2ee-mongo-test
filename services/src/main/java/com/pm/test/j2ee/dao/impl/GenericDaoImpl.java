package com.pm.test.j2ee.dao.impl;

import java.net.UnknownHostException;
import java.util.Collection;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;

import com.mongodb.MongoClient;
import com.pm.test.j2ee.services.GenericCRUD;

public class GenericDaoImpl<T> extends BasicDAO<T, ObjectId> implements GenericCRUD<T>{
	private Class<T> entityClass;

	public GenericDaoImpl(Class<T> entityClass) throws UnknownHostException {
		super(entityClass, new MongoClient("localhost", 27017), new Morphia(), "test");			
	}
	
	@Override
	public T create(T entity) {
		getDatastore().save(entity);
		return entity;
	}

	@Override
	public T get(String key) {
		return getDatastore().get(entityClass, key);
	}

	@Override
	public T update(T entity) {
		getDatastore().save(entity);
		return entity;
	}

	@Override
	public void deleteEntity(T entity) {
		getDatastore().delete(entity);
	}

	@Override
	public Collection<T> getAll() {
		return getDatastore().find(entityClass).asList();
	}
}
