package com.tragent.pressing.service;

import java.util.Collection;

import com.tragent.pressing.model.Role;
import com.tragent.pressing.model.RoleDTO;

/** 
 * Service that provides CRUD operations for role 
 **/
public interface RoleService {
	
	/**
	 * Get all roles in the system.
	 * 
	 * @return cCollection of roles
	 * 
	 */
	public Collection<Role> findAll();
	
	/**
	 * Find a role by Id.
	 * 
	 * @param roleId
	 * @return the role object if found, else return null
	 */
	public Role findById(Long roleId);
	
	/**
	 * Find a role by name.
	 * 
	 * @param name
	 * @return the role object if found, else return null
	 */
	public Role findByName(String name);
	
	/**
	 * Create a new role.
	 * 
	 * @param roleDTO
	 * @return Role object (created role object)
	 */
	public Role create(RoleDTO roleDTO);
	
	/**
	 * Update an existing role.
	 * 
	 * @param roleDTO
	 * @return Role object (updated role object)
	 */
	public Role update(RoleDTO roleDTO);
	
}
