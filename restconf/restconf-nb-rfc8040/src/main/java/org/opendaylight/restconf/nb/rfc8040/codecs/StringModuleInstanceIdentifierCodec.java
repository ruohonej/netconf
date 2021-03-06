/*
 * Copyright (c) 2016 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.restconf.nb.rfc8040.codecs;

import com.google.common.base.Preconditions;
import java.net.URI;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.opendaylight.yangtools.yang.data.util.AbstractModuleStringInstanceIdentifierCodec;
import org.opendaylight.yangtools.yang.data.util.DataSchemaContextTree;
import org.opendaylight.yangtools.yang.model.api.Module;
import org.opendaylight.yangtools.yang.model.api.SchemaContext;

public final class StringModuleInstanceIdentifierCodec extends AbstractModuleStringInstanceIdentifierCodec {

    private final DataSchemaContextTree dataContextTree;
    private final SchemaContext context;
    private final String defaultPrefix;

    public StringModuleInstanceIdentifierCodec(final SchemaContext context) {
        this.context = Preconditions.checkNotNull(context);
        this.dataContextTree = DataSchemaContextTree.from(context);
        this.defaultPrefix = "";
    }

    public StringModuleInstanceIdentifierCodec(final SchemaContext context, @Nonnull final String defaultPrefix) {
        this.context = Preconditions.checkNotNull(context);
        this.dataContextTree = DataSchemaContextTree.from(context);
        this.defaultPrefix = defaultPrefix;
    }

    @Override
    protected Module moduleForPrefix(@Nonnull final String prefix) {
        final String moduleName = prefix.isEmpty() && !defaultPrefix.isEmpty() ? defaultPrefix : prefix;
        return context.findModules(moduleName).stream().findFirst().orElse(null);
    }

    @Nonnull
    @Override
    public DataSchemaContextTree getDataContextTree() {
        return this.dataContextTree;
    }

    @Nullable
    @Override
    protected String prefixForNamespace(@Nonnull final URI namespace) {
        return this.context.findModules(namespace).stream().findFirst().map(Module::getName).orElse(null);
    }
}
