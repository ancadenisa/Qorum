package com.qorum.service.impl;

import com.qorum.domain.Organization;
import com.qorum.repository.OrganizationRepository;
import com.qorum.service.OrganizationService;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Anca on 04-Nov-15.
 */
public class OrganizationServiceImpl implements OrganizationService {
    @Inject
    OrganizationRepository organizationRepository;
    @Override
    public List<Organization> getOrganizationsByUserLoggedId(Long userId) {
        return organizationRepository.getOrganizationsByUserLoggedId(userId);
    }
}
