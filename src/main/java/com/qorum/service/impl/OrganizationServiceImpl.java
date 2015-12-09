package com.qorum.service.impl;

import com.qorum.domain.Organization;
import com.qorum.repository.OrganizationRepository;
import com.qorum.service.OrganizationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Anca on 04-Nov-15.
 */
@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {
    @Inject
    OrganizationRepository organizationRepository;
    @Override
    public List<Organization> getOrganizationsByUserLoggedId(Long userId) {
        List<Organization>  organizationList =  organizationRepository.getOrganizationsByUserLoggedId(userId);
        return organizationList.size() != 0 ?  organizationList :  organizationRepository.getOrganizationsByOrgAdmin(userId);
    }
}
