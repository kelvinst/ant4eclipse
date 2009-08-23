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
package org.ant4eclipse.pydt.test.data;

import org.ant4eclipse.core.Assert;
import org.ant4eclipse.core.util.Pair;

import org.ant4eclipse.pydt.test.builder.DLTKProjectBuilder;
import org.ant4eclipse.pydt.test.builder.PyDevProjectBuilder;
import org.ant4eclipse.pydt.test.builder.PythonProjectBuilder;
import org.ant4eclipse.pydt.test.builder.WorkspaceBuilder;

import java.net.URL;
import java.text.DecimalFormat;

/**
 * Helper class used to setup several python related projects.
 * 
 * @author Daniel Kasmeroglu (Daniel.Kasmeroglu@Kasisoft.net)
 */
public class ProjectSuite implements ProjectSuiteApi {

  private static final String NAME_PREFIX = "a4ePython%s";

  private WorkspaceBuilder    _workspacebuilder;

  private boolean             _dltk;

  private int                 _count;

  private DecimalFormat       _formatter;

  /**
   * Initialises this project suite
   * 
   * @param wsbuilder
   *          The builder used for the workspace. Not <code>null</code>.
   * @param dltk
   *          <code>true</code> <=> Use a DLTK based python nature, PyDev otherwise.
   */
  public ProjectSuite(final WorkspaceBuilder wsbuilder, final boolean dltk) {
    Assert.notNull(wsbuilder);
    _workspacebuilder = wsbuilder;
    _dltk = dltk;
    _count = 1;
    _formatter = new DecimalFormat("000");
  }

  /**
   * {@inheritDoc}
   */
  public String createEmptyProject(final URL script, final int projectsettings) {
    final String result = newName();
    final PythonProjectBuilder builder = newProjectBuilder(result);
    builder.setBuildScript(script);
    if ((projectsettings & KIND_MULTIPLESOURCEFOLDERSPRIMARY) != 0) {
      builder.addSourceFolder(NAME_GENERATEDSOURCE);
    }
    builder.populate(_workspacebuilder);
    return result;
  }

  /**
   * {@inheritDoc}
   */
  public Pair<String, String> createComplexProject(final URL script, final int projectsettings) {
    final String mainname = newName();
    final PythonProjectBuilder mainbuilder = newProjectBuilder(mainname);
    mainbuilder.setBuildScript(script);
    if ((projectsettings & KIND_MULTIPLESOURCEFOLDERSPRIMARY) != 0) {
      mainbuilder.addSourceFolder(NAME_GENERATEDSOURCE);
    }
    final String secondaryname = newName();
    final PythonProjectBuilder secondarybuilder = newProjectBuilder(secondaryname);
    if ((projectsettings & KIND_MULTIPLESOURCEFOLDERSSECONDARY) != 0) {
      secondarybuilder.addSourceFolder(NAME_GENERATEDSOURCE);
    }
    mainbuilder.useProject(secondaryname, true);
    mainbuilder.populate(_workspacebuilder);
    secondarybuilder.populate(_workspacebuilder);
    return new Pair<String, String>(mainname, secondaryname);
  }

  /**
   * {@inheritDoc}
   */
  public Pair<String, String> createCyclicProject(final URL script, final int projectsettings) {
    final String mainname = newName();
    final PythonProjectBuilder mainbuilder = newProjectBuilder(mainname);
    mainbuilder.setBuildScript(script);
    if ((projectsettings & KIND_MULTIPLESOURCEFOLDERSPRIMARY) != 0) {
      mainbuilder.addSourceFolder(NAME_GENERATEDSOURCE);
    }
    final String secondaryname = newName();
    final PythonProjectBuilder secondarybuilder = newProjectBuilder(secondaryname);
    if ((projectsettings & KIND_MULTIPLESOURCEFOLDERSSECONDARY) != 0) {
      secondarybuilder.addSourceFolder(NAME_GENERATEDSOURCE);
    }
    mainbuilder.useProject(secondaryname, true);
    secondarybuilder.useProject(mainname, true);
    mainbuilder.populate(_workspacebuilder);
    secondarybuilder.populate(_workspacebuilder);
    return new Pair<String, String>(mainname, secondaryname);
  }

  /**
   * Creates a new instance of a PythonProjectBuilder with the supplied project name.
   * 
   * @param projectname
   *          The name of the project. Neither <code>null</code> nor empty.
   * 
   * @return The builder used to create the projects. Not <code>null</code>.
   */
  private PythonProjectBuilder newProjectBuilder(final String projectname) {
    if (_dltk) {
      return new DLTKProjectBuilder(projectname);
    } else {
      return new PyDevProjectBuilder(projectname);
    }
  }

  /**
   * Just a generative function to produce new names.
   * 
   * @return A newly generated name. Neither <code>null</code> nor empty.
   */
  private String newName() {
    return String.format(NAME_PREFIX, _formatter.format(_count++));
  }

} /* ENDCLASS */
