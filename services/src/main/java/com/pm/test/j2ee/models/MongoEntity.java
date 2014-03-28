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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MongoEntity other = (MongoEntity) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}

}
