/**********************************************************************
 * Copyright (c) 2005-2009 ant4eclipse project team.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Nils Hartmann, Daniel Kasmeroglu, Gerd Wuetherich
 **********************************************************************/
package org.ant4eclipse.pydt.internal.tools;

import org.ant4eclipse.core.Assert;

import org.ant4eclipse.pydt.model.RawPathEntry;
import org.ant4eclipse.pydt.model.ResolvedPathEntry;
import org.ant4eclipse.pydt.tools.PathEntryRegistry;

import java.util.Hashtable;
import java.util.Map;

/**
 * Implementation of the path registry.
 * 
 * @author Daniel Kasmeroglu (Daniel.Kasmeroglu@Kasisoft.net)
 */
public class PathEntryRegistryImpl implements PathEntryRegistry {

  private final Map<RawPathEntry, ResolvedPathEntry> _resolvedentries;

  /**
   * Initialises this registry for path entries.
   */
  public PathEntryRegistryImpl() {
    _resolvedentries = new Hashtable<RawPathEntry, ResolvedPathEntry>();
  }

  /**
   * {@inheritDoc}
   */
  public boolean isResolved(RawPathEntry entry) {
    Assert.notNull(entry);
    return _resolvedentries.containsKey(entry);
  }

  /**
   * {@inheritDoc}
   */
  public void registerResolvedPathEntry(final RawPathEntry origin, final ResolvedPathEntry solution) {
    Assert.notNull(origin);
    Assert.notNull(solution);
    _resolvedentries.put(origin, solution);
  }

  /**
   * {@inheritDoc}
   */
  public ResolvedPathEntry getResolvedPathEntry(final RawPathEntry entry) {
    Assert.notNull(entry);
    return _resolvedentries.get(entry);
  }

} /* ENDCLASS */