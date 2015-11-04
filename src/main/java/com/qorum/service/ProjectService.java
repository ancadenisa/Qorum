package com.qorum.service;

import com.qorum.domain.Project;

import java.util.List;

/**
 * Created by Anca on 04-Nov-15.
 */
public interface ProjectService {

    List<Project> getProjectsByDepartmentAndByUserLogged(Long deptId, Long userId);
}
