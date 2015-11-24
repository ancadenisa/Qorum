package com.qorum.service;

import com.qorum.Application;
import com.qorum.repository.DepartmentRepository;
import com.qorum.repository.OrganizationRepository;
import com.qorum.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by Anca on 04-Nov-15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
@Transactional
public class DepartmentServiceTest {
    @Inject
    DepartmentService departmentService;
    @Inject
    DepartmentRepository departmentRepository;
    @Inject
    OrganizationRepository organizationRepository;

    @Inject
    UserRepository userRepository;
    @Test
    public void testGetDepartmentByOrgAndByUserLoggedFromDataBase(){
        departmentService.getDepartmentsByOrgAndByUserLogged(1021L, 4L);
    }
}
