/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */package org.jboss.as.controller.client.helpers.domain.impl;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.jboss.as.controller.client.helpers.domain.DeploymentAction;
import org.jboss.as.controller.client.helpers.domain.DomainUpdateListener;

/**
 * Implementation of {@link DeploymentAction}.
 *
 * @author Brian Stansberry
 */
public class DeploymentActionImpl implements DeploymentAction, Serializable {

    private static final long serialVersionUID = 613098200977026475L;

    public static DeploymentActionImpl getAddAction(String deploymentName, String fileName, byte[] hash) {
        assert fileName != null : "fileName is null";
        assert hash != null : "hash is null";
        return new DeploymentActionImpl(Type.ADD, deploymentName, fileName, hash, null);
    }

    public static DeploymentActionImpl getDeployAction(String deploymentName) {
        return new DeploymentActionImpl(Type.DEPLOY, deploymentName, null, null, null);
    }

    public static DeploymentActionImpl getRedeployAction(String deploymentName) {
        return new DeploymentActionImpl(Type.REDEPLOY, deploymentName, null, null, null);
    }

    public static DeploymentActionImpl getUndeployAction(String deploymentName) {
        return new DeploymentActionImpl(Type.UNDEPLOY, deploymentName, null, null, null);
    }

    public static DeploymentActionImpl getReplaceAction(String deploymentName, String replacedName) {
        if (replacedName == null) {
            throw new IllegalArgumentException("replacedName is null");
        }
        return new DeploymentActionImpl(Type.REPLACE, deploymentName, null, null, replacedName);
    }

    public static DeploymentActionImpl getFullReplaceAction(String deploymentName, String fileName, byte[] hash) {
        assert fileName != null : "fileName is null";
        assert hash != null : "hash is null";
        return new DeploymentActionImpl(Type.FULL_REPLACE, deploymentName, fileName, hash, null);
    }

    public static DeploymentActionImpl getRemoveAction(String deploymentName) {
        return new DeploymentActionImpl(Type.REMOVE, deploymentName, null, null, null);
    }

    private final UUID uuid = UUID.randomUUID();
    private final Type type;
    private final String deploymentUnitName;
    private final String oldDeploymentUnitName;
    private final String newContentFileName;
    private final byte[] newContentHash;
    private transient Set<DomainUpdateListener> listeners;

    private DeploymentActionImpl(Type type, String deploymentUnitName, String newContentFileName, byte[] newContentHash, String replacedDeploymentUnitName) {
        assert type != null : "type is null";
        if (deploymentUnitName == null) {
            throw new IllegalArgumentException("deploymentUnitName is null");
        }
        this.type = type;
        this.deploymentUnitName = deploymentUnitName;
        this.newContentFileName = newContentFileName;
        this.newContentHash = newContentHash;
        this.oldDeploymentUnitName = replacedDeploymentUnitName;
    }

    @Override
    public UUID getId() {
        return uuid;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public String getDeploymentUnitUniqueName() {
        return deploymentUnitName;
    }

    @Override
    public String getReplacedDeploymentUnitUniqueName() {
        return oldDeploymentUnitName;
    }

    public String getNewContentFileName() {
        return newContentFileName;
    }

    public byte[] getNewContentHash() {
        return newContentHash;
    }

    @Override
    public synchronized void addDomainUpdateListener(DomainUpdateListener listener) {
        if (listeners == null) {
            listeners = new HashSet<DomainUpdateListener>();
        }
        listeners.add(listener);

        // FIXME remove when we can actually provide notifications
        throw new UnsupportedOperationException("Use of DomainUpdateListener is not currently supported");
    }

    @Override
    public synchronized boolean removeDomainUpdateListener(DomainUpdateListener listener) {
        return listeners != null && listeners.remove(listener);
    }

    synchronized Set<DomainUpdateListener> getListeners() {
        return listeners == null ? Collections.<DomainUpdateListener>emptySet()
                                 : Collections.unmodifiableSet(listeners);
    }

}
