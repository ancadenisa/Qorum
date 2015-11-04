package com.qorum.service.impl;

import com.qorum.domain.Project;
import com.qorum.repository.ProjectRepository;
import com.qorum.service.ProjectService;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Anca on 04-Nov-15.
 */
public class ProjectServiceImpl implements ProjectService {
    @Inject
    ProjectRepository projectRepository;

    @Override
    public List<Project> getProjectsByDepartmentAndByUserLogged(Long deptId, Long userId) {
        return projectRepository.getProjectsByDepartmentAndByUserLogged(deptId, userId);
    }
}
