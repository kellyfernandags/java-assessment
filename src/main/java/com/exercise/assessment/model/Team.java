package com.exercise.assessment.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Team {
	
	@Id
    private String id;
    private String name;
    
    public Team() {
    }
    
    public Team (String id, String name) {
    	this.id = id;
    	this.name = name;
    }

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return String.format("User [id=%s, name=%s]", id, name);
	}
}
