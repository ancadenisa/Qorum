package com.qorum.repository;

import com.qorum.domain.Project;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Project entity.
 */
public interface ProjectRepository extends JpaRepository<Project,Long> {

    @Query("select distinct project from Project project left join fetch project.departments left join fetch project.users")
    List<Project> findAllWithEagerRelationships();

    @Query("select project from Project project left join fetch project.departments left join fetch project.users where project.id =:id")
    Project findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select distinct project from Project project, User user, Department department join project.users user  join project.departments department  where user.id = :userId and department.id = :deptId")
    List<Project> getProjectsByDepartmentAndByUserLogged(@Param("deptId") Long deptId, @Param("userId") Long userId);

    @Query("select distinct project from Project project, Department department join project.departments department where department.id = :deptId")
    List<Project> getProjectsByDepartment(@Param("deptId") Long deptId);
}
