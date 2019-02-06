package com.tragent.pressing.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tragent.pressing.model.Permission;
import com.tragent.pressing.model.Role;
import com.tragent.pressing.model.RoleDTO;
import com.tragent.pressing.service.PermissionService;
import com.tragent.pressing.service.RoleService;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
@RequestMapping("api/v1/roles")
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private PermissionService permissionService;
		
	/**
	 * Get all roles or role by name.
	 *
	 * @return Collection of roles in the system or role with the given name
	 */
	@RequestMapping(method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Role>> getRoles() {
		Collection<Role> roles = new ArrayList<>();
		roles.addAll(this.roleService.findAll());
		return new ResponseEntity<>(roles, HttpStatus.OK);
	}

	/**
	 * Get role by name.
	 *
	 * @param name
	 * @return Role object or 404 if role is not found
	 */
	@RequestMapping(value="/name/{name}",
			method=RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Role> getRoleByName(@PathVariable("name") String name){
		Role role = this.roleService.findByName(name);
		if (role == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(role, HttpStatus.OK);
	}
	
	/**
	 * Get role by id.
	 * 
	 * @param roleId
	 * @return Role object or 404 if role is not found
	 */
	@RequestMapping(value="/{roleId}",
			method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Role> getRoleById(@PathVariable("roleId") Long roleId){
		Role role = this.roleService.findById(roleId);
		if (role == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(role, HttpStatus.OK);
	}
	
	/**
	 * Get all permissions in role.
	 * 
	 * @param roleId
	 * @return Collection of permissions
	 */
	@RequestMapping(value="/{roleId}/permissions",
			method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Permission>> getRolePermissions(@PathVariable("roleId") Long roleId){
		Collection<Permission> permissions = this.roleService.findById(roleId).getPermission();
		if (permissions == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(permissions, HttpStatus.OK);
	}
	
	/**
	 * Create new role.
	 * 
	 * @param role
	 * @return Role Object (created role object)
	 */
	@RequestMapping(method=RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Role> createRole(@RequestBody RoleDTO roleDTO){
		Role newRole = roleService.create(roleDTO);
		if (newRole == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(newRole, HttpStatus.CREATED);	
	}
	
	/**
	 * Update role record.
	 * 
	 * @param roleDTO
	 * @return Role object (updated role object).
	 */
	@RequestMapping(value="/{roleId}",
			method=RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Role> updateRole(@RequestBody RoleDTO roleDTO,
			@PathVariable("roleId") Long roleId){
		if( roleDTO.getId().compareTo(roleId) == 0){
			Role updatedRole = roleService.update(roleDTO);
			if (updatedRole != null) {
				return new ResponseEntity<>(updatedRole, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
}
