package com.exercise.assessment.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class User {
	
	@Id
    private String id;
    private String displayName;
    
    @ManyToOne (cascade = CascadeType.ALL)
    private Role role;
    
    public User() {
    }
    
    public User (String id, String displayName, Role role) {
    	this.id = id;
    	this.displayName = displayName;
    	this.role = role;
    }

	public String getId() {
		return id;
	}

	public String getDisplayName() {
		return displayName;
	}
	
	public Role getRole() {
		return role;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return String.format("User [id=%s, displayName=%s, role=%s]", id, displayName, role);
	}
}
