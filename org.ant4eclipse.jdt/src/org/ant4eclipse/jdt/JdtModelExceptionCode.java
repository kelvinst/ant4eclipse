/**********************************************************************
 * Copyright (c) 2005-2008 ant4eclipse project team.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Nils Hartmann, Daniel Kasmeroglu, Gerd Wuetherich
 **********************************************************************/
package org.ant4eclipse.jdt;

import org.ant4eclipse.core.exception.ExceptionCode;
import org.ant4eclipse.core.nls.NLS;
import org.ant4eclipse.core.nls.NLSMessage;

public class JdtModelExceptionCode extends ExceptionCode {

  @NLSMessage("The specified directory '%s' doesn't point to a valid java runtime environment.")
  public static JdtModelExceptionCode INVALID_JRE_DIRECTORY;

  @NLSMessage("Project '%s' must have a java project role.")
  public static JdtModelExceptionCode NO_JAVA_PROJECT_ROLE;

  @NLSMessage("Default java runtime could not be resolved!")
  public static JdtModelExceptionCode NO_DEFAULT_JAVA_RUNTIME_EXCEPTION;

  @NLSMessage("Exception while executing java launcher ('%s')")
  public static JdtModelExceptionCode JAVA_LAUNCHER_EXECUTION_EXCEPTION;

  static {
    NLS.initialize(JdtModelExceptionCode.class);
  }

  private JdtModelExceptionCode(final String message) {
    super(message);
  }
}