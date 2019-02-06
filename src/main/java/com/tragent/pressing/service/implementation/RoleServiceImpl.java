package com.tragent.pressing.service.implementation;

import java.util.Collection;
import java.util.stream.Collectors;

import com.tragent.pressing.model.RoleDTO;
import com.tragent.pressing.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import com.tragent.pressing.model.Role;
import com.tragent.pressing.repository.RoleRepository;
import com.tragent.pressing.service.RoleService;

@Service
@Secured("ROLE_ADMINISTRATION")
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleRepository	roleRepository;

	@Autowired
	private PermissionRepository permissionRepository;

	@Override
	public Collection<Role> findAll() {
		
		Collection<Role> roles = this.roleRepository.findAll();
		return roles;
	}

	@Override
	public Role findById(Long id) {
		
		Role role = this.roleRepository.findOne(id);
		return role;
	}

	@Override
	public Role findByName(String name) {
		
		Role role = this.roleRepository.findByName(name);
		return role;
	}

	@Override
	public Role create(RoleDTO roleDTO) {
		if(this.roleRepository.findByName(roleDTO.getName()) == null) {
			Role role = this.convertRoleDTOtoRole(roleDTO);
			return this.roleRepository.save(role);
		}
		return null;
	}

	@Override
	public Role update(RoleDTO roleDTO) {
		if(this.roleRepository.findByName(roleDTO.getName()) != null) {
			Role role = this.convertRoleDTOtoRole(roleDTO);
			return this.roleRepository.save(role);
		}
		return null;
	}

	private Role convertRoleDTOtoRole(RoleDTO roleDTO) {
		Role role = roleDTO.toRole();
		role.setPermission(roleDTO.getPermissionIds()
			.stream()
			.map(permissionId -> this.permissionRepository.findOne(permissionId))
			.collect(Collectors.toList()));
		return role;
	}

}
