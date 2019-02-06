package com.tragent.pressing.model;

import java.util.ArrayList;
import java.util.List;

public class RoleDTO {
	private Long id;
	private String name;
	private String description;
	private List<Long> permissionIds;
	
	public RoleDTO() {
		super();
	}

	public RoleDTO(String name, String description, List<Long> permissionIds) {
		super();
		this.name = name;
		this.description = description;
		this.permissionIds = permissionIds;
	}

	public RoleDTO(Long id, String name, String description, List<Long> permissionIds) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.permissionIds = permissionIds;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Long> getPermissionIds() {
		return this.permissionIds;
	}

	public void setPermissionIds(ArrayList<Long> permissionIds) {
		this.permissionIds = permissionIds;
	}

	public Role toRole() {
		return new Role(this.id, this.name, this.description);
	}
	
}
