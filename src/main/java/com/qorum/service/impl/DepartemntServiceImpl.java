package com.qorum.service.impl;

import com.qorum.domain.Department;
import com.qorum.repository.DepartmentRepository;
import com.qorum.service.DepartmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Anca on 04-Nov-15.
 */
@Service
@Transactional
public class DepartemntServiceImpl implements DepartmentService {
    @Inject
    DepartmentRepository departmentRepository;
    @Override
    public List<Department> getDepartmentsByOrgAndByUserLogged(Long orgId, Long userId) {
        return departmentRepository.getDepartmentsByOrgIdAndByUserId(orgId, userId);
    }
}
