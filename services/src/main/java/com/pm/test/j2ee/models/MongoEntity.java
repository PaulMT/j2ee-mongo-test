package com.pm.test.j2ee.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

public abstract class MongoEntity {

	@Id
	@XmlTransient
	protected ObjectId key;

	public ObjectId getKey() {
		return key;
	}

	public void setKey(ObjectId key) {
		this.key = key;
	}

	@XmlElement(name = "uid")
	public String getUid() {
		return key != null ? key.toString() : null;
	}

	public void setUid(String uid) {
		this.key = new ObjectId(uid);
	}

}
