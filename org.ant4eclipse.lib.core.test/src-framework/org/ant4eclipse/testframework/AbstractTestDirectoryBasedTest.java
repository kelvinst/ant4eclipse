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
package org.ant4eclipse.testframework;

import org.ant4eclipse.core.service.ServiceRegistry;

import org.ant4eclipse.ant.core.Ant4EclipseConfigurator;

import java.io.File;

import junit.framework.TestCase;

/**
 * Baseclass for all buildfile-based tests in the platform layer
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public abstract class AbstractTestDirectoryBasedTest extends TestCase {

  /** - */
  private TestDirectory _testWorkspace;

  /**
   * Creates the Test Environment before execution of a test case
   */
  @Override
  public void setUp() {
    Ant4EclipseConfigurator.configureAnt4Eclipse();
    this._testWorkspace = new TestDirectory();
  }

  /**
   * Disposes the test environment and resets the {@link ServiceRegistry}
   */
  @Override
  protected void tearDown() {
    try {
      super.tearDown();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    this._testWorkspace.dispose();
    ServiceRegistry.reset();
  }

  /**
   * Returns a {@link TestDirectory} for this test case.
   * 
   * @return A TestDirectory for this test case. Not <code>null</code> during a test.
   */
  protected TestDirectory getTestDirectory() {
    return this._testWorkspace;
  }

  /**
   * Returns the root directory of the workspace.
   * 
   * @return The root directory of the workspace. Not <code>null</code> during a test.
   */
  protected File getTestDirectoryRootDir() {
    return this._testWorkspace.getRootDir();
  }

} /* ENDCLASS */