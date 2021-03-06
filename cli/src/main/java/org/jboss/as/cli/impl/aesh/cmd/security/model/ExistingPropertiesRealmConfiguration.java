/*
Copyright 2018 Red Hat, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package org.jboss.as.cli.impl.aesh.cmd.security.model;

import org.jboss.as.cli.Util;

/**
 * A configuration for an existing properties-realm
 *
 * @author jdenise@redhat.com
 */
public class ExistingPropertiesRealmConfiguration implements MechanismConfiguration {

    private final String name;
    private final String exposedRealmName;
    private String realmMapper;
    public ExistingPropertiesRealmConfiguration(String name, String exposedRealmName) {
        this.name = name;
        this.exposedRealmName = exposedRealmName;
    }

    @Override
    public String getRealmName() {
        return name;
    }

    @Override
    public String getRoleDecoder() {
        return Util.GROUPS_TO_ROLES;
    }

    @Override
    public String getRoleMapper() {
        return null;
    }

    @Override
    public String getRealmMapper() {
        return realmMapper;
    }

    @Override
    public String getExposedRealmName() {
        return exposedRealmName;
    }

    @Override
    public void setRealmMapperName(String constantMapper) {
        this.realmMapper = constantMapper;
    }

}
