package com.tragent.pressing.controller;

import java.util.ArrayList;
import java.util.Collection;

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
import com.tragent.pressing.service.PermissionService;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
@RequestMapping("api/v1/permissions")
public class PermissionController {

	@Autowired
	private PermissionService permissionService;
		
	/**
	 * Get all permissions or permission by name.
	 *
	 * @return Collection of permissions or permission with the given name
	 */
	@RequestMapping(method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Permission>> getPermissions() {
		Collection<Permission> permissions = new ArrayList<>();
		Collection<Permission> allPermission = permissionService.findAll();
		permissions.addAll(allPermission);
		return new ResponseEntity<>(permissions, HttpStatus.OK);	
	}

	/**
	 * Get permission by name.
	 *
	 * @param name
	 * @return Permission object or 404 if permission is not found
	 */
	@RequestMapping(value="/name/{name}",
			method=RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Permission> getPermissionByName(@PathVariable("name") String name){
		Permission permission = permissionService.findByName(name);
		if (permission == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(permission, HttpStatus.OK);
	}

	/**
	 * Get permission by id.
	 * 
	 * @param permissionId
	 * @return Permission object or 404 if permission is not found
	 */
	@RequestMapping(value="/{permissionId}",
			method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Permission> getPermissionById(@PathVariable("permissionId") Long permissionId){
		Permission permission = permissionService.findById(permissionId);
		if (permission == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(permission, HttpStatus.OK);
	}
	
	/**
	 * Create new permission.
	 * 
	 * @param permission
	 * @return Permission object (created object)
	 */
	@RequestMapping(method=RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Permission> createPermission(@RequestBody Permission permission){
		Permission newPermission = permissionService.create(permission);
		if (newPermission == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(newPermission, HttpStatus.CREATED);	
	}
	
	/**
	 * Update permission record.
	 * 
	 * @param permission
	 * @return Permission object (updated object)
	 */
	@RequestMapping(value="/{permissionId}",
			method=RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Permission> updatePermission(@RequestBody Permission permission,
			@PathVariable("permissionId") Long permissionId){
		if (permission.getId().compareTo(permissionId) == 0) {
			Permission updatedPermission = this.permissionService.update(permission);
			if (updatedPermission != null) {
				return new ResponseEntity<>(updatedPermission, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
}
