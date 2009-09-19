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
package org.ant4eclipse.platform.ant.core;

import org.ant4eclipse.core.util.StringMap;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Encapsulates all properties and references that should be accessible within a macro execution.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public final class MacroExecutionValues {

  /** the properties */
  private StringMap           properties;

  /** the references */
  private Map<String, Object> references;

  /**
   * <p>
   * Creates a new instance of type {@link MacroExecutionValues}.
   * </p>
   */
  public MacroExecutionValues() {
    // create properties map
    this.properties = new StringMap();
    // create references map
    this.references = new HashMap<String, Object>();
  }

  /**
   * <p>
   * Return the map with all properties.
   * </p>
   * 
   * @return the map with all properties.
   */
  public StringMap getProperties() {
    return this.properties;
  }

  /**
   * <p>
   * Return the map with all references.
   * </p>
   * 
   * @return the map with all references.
   */
  public Map<String, Object> getReferences() {
    return this.references;
  }
}
