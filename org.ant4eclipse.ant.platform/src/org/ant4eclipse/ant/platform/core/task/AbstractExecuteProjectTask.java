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
package org.ant4eclipse.ant.platform.core.task;


import org.ant4eclipse.ant.platform.PlatformExecutorValuesProvider;
import org.ant4eclipse.ant.platform.core.MacroExecutionComponent;
import org.ant4eclipse.ant.platform.core.ScopedMacroDefinition;
import org.ant4eclipse.ant.platform.core.delegate.MacroExecutionDelegate;
import org.ant4eclipse.ant.platform.core.delegate.MacroExecutionValuesProvider;
import org.ant4eclipse.lib.core.Assure;
import org.apache.tools.ant.DynamicElement;
import org.apache.tools.ant.taskdefs.MacroDef;
import org.apache.tools.ant.taskdefs.MacroDef.NestedSequential;

import java.util.List;

/**
 * <p>
 * Abstract base class for all tasks that allow to iterate over a JDT (or JDT-based) project. This class can be
 * subclassed to implement a custom executor task for specific project types (e.g. PDE plug-in projects).
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractExecuteProjectTask extends AbstractProjectPathTask implements DynamicElement,
    MacroExecutionComponent<String> {

  /** the macro execution delegate */
  private MacroExecutionDelegate<String> _macroExecutionDelegate;

  /** the platform executor values provider */
  private PlatformExecutorValuesProvider _platformExecutorValuesProvider;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractExecuteProjectTask}.
   * </p>
   * 
   * @param prefix
   *          the prefix for all scoped values
   */
  public AbstractExecuteProjectTask( String prefix ) {
    Assure.notNull( "prefix", prefix );
    _platformExecutorValuesProvider = new PlatformExecutorValuesProvider( this );
    // create the delegates
    _macroExecutionDelegate = new MacroExecutionDelegate<String>( this, prefix );
  }

  /**
   * <p>
   * </p>
   * 
   * @return the platformExecutorValuesProvider
   */
  public PlatformExecutorValuesProvider getPlatformExecutorValuesProvider() {
    return _platformExecutorValuesProvider;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final NestedSequential createScopedMacroDefinition( String scope ) {
    return _macroExecutionDelegate.createScopedMacroDefinition( scope );
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void executeMacroInstance( MacroDef macroDef, MacroExecutionValuesProvider provider ) {
    _macroExecutionDelegate.executeMacroInstance( macroDef, provider );
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final List<ScopedMacroDefinition<String>> getScopedMacroDefinitions() {
    return _macroExecutionDelegate.getScopedMacroDefinitions();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final String getPrefix() {
    return _macroExecutionDelegate.getPrefix();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setPrefix( String prefix ) {
    _macroExecutionDelegate.setPrefix( prefix );
  }
  
} /* ENDCLASS */
