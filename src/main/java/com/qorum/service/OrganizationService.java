package com.qorum.service;

import com.qorum.domain.Organization;

import java.util.List;

/**
 * Created by Anca on 04-Nov-15.
 */
public interface OrganizationService {

    List<Organization> getOrganizationsByUserLoggedId(Long userId);
}
