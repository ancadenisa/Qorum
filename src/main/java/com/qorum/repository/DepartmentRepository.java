package com.qorum.repository;

import com.qorum.domain.Department;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Department entity.
 */
public interface DepartmentRepository extends JpaRepository<Department,Long> {

    @Query("select distinct department from Department department left join fetch department.users")
    List<Department> findAllWithEagerRelationships();

    @Query("select department from Department department left join fetch department.users left join fetch department.projects where department.id =:id")
    Department findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select distinct department from Department department, User user inner join department.users user  where department.organization.id = :orgId and user.id = :userId")
    List<Department> getDepartmentsByOrgIdAndByUserId(@Param("orgId") Long orgId, @Param("userId") Long userId);

    @Query("select distinct department from Department department left join fetch department.users   where department.organization.id = :orgId")
    List<Department> getDepartmentsByOrgId(@Param("orgId") Long orgId);

    @Query("select distinct project.departments from Project project where project.id=:projectId")
    List<Department> getByProjectId(@Param("projectId") Long projectId);
}
