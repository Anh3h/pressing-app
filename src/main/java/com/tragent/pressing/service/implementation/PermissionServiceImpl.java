package com.tragent.pressing.service.implementation;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import com.tragent.pressing.model.Permission;
import com.tragent.pressing.repository.PermissionRepository;
import com.tragent.pressing.service.PermissionService;

@Service
@Secured("ROLE_ADMINISTRATION")
public class PermissionServiceImpl implements PermissionService {
	
	@Autowired
	private PermissionRepository permissionRepository;

	@Override
	public Collection<Permission> findAll() {
		Collection<Permission> permissions = this.permissionRepository.findAll();
		return permissions;
	}

	@Override
	public Permission findById(Long id) {
		Permission permission = this.permissionRepository.findOne(id);
		return permission;
	}

	@Override
	public Permission findByName(String name) {
		Permission permission = this.permissionRepository.findByName(name);
		return permission;
	}

	@Override
	public Permission create(Permission permission) {
		Permission savedPermission = this.permissionRepository.save(permission);
		return savedPermission;
	}

	@Override
	public Permission update(Permission permission) {
		if(this.permissionRepository.findOne(permission.getId()) != null) {
			Permission updatedPermission = this.permissionRepository.save(permission);
			return updatedPermission;
		}
		return null;
	}

}
