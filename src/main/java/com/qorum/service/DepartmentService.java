package com.qorum.service;

import com.qorum.domain.Department;

import java.util.List;

/**
 * Created by Anca on 04-Nov-15.
 */

public interface DepartmentService {
    List<Department> getDepartmentsByOrgAndByUserLogged(Long orgId, Long userId);
}
