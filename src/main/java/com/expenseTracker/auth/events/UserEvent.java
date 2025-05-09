package com.expenseTracker.auth.events;

import com.expenseTracker.auth.model.Role;

public class UserEvent {
    private Long id;
    private String username;
    private String passwordHash;
    private Role role;
    
    public UserEvent(){
    }
    
    public UserEvent(Long id, String username, String passwordHash, Role role) {
    	this.id = id;
    	this.passwordHash = passwordHash;
    	this.role = role;
    	this.username = username;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
    
    
    
}
