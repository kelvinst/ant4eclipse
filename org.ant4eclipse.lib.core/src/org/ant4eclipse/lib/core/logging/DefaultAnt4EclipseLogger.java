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
package org.ant4eclipse.lib.core.logging;

import java.io.PrintStream;

/**
 * <p>
 * Default implementation of an {@link Ant4EclipseLogger} which makes use of a specific {@link PrintStream} or
 * {@link System#out} by default.
 * </p>
 */
public class DefaultAnt4EclipseLogger implements Ant4EclipseLogger {

  public static enum Priority {

    error(0, "ERROR"), warn(1, "WARN"), info(2, "INFO"), debug(3, "DEBUG"), trace(4, "TRACE");

    private int    level;

    private String description;

    Priority(int lev, String desc) {
      this.level = lev;
      this.description = desc;
    }

    public boolean isEnabled(Priority value) {
      return value.level >= this.level;
    }

    @Override
    public String toString() {
      return this.description;
    }

  } /* ENDENUM */

  private Priority    _logLevel;

  private PrintStream _printer;

  /**
   * Sets up this logger implementation to make use of standard output.
   */
  public DefaultAnt4EclipseLogger() {
    this(null);
  }

  /**
   * Sets up this logger implementation to make use of a specified printer.
   * 
   * @param printer
   *          The printer that will be used for the output. If <code>null</code> the standard output is used.
   */
  public DefaultAnt4EclipseLogger(PrintStream printer) {
    this._logLevel = Priority.trace;
    this._printer = printer;
    if (this._printer == null) {
      this._printer = System.out;
    }
  }

  /**
   * {@inheritDoc}
   */
  public boolean isDebuggingEnabled() {
    return Priority.debug.isEnabled(this._logLevel);
  }

  /**
   * {@inheritDoc}
   */
  public boolean isTraceingEnabled() {
    return Priority.trace.isEnabled(this._logLevel);
  }

  /**
   * Changes the current loglevel for this logger.
   * 
   * @param loglevel
   *          The new log level to be used for this logger.
   */
  public void setLogLevel(Priority loglevel) {
    this._logLevel = loglevel;
  }

  /**
   * {@inheritDoc}
   */
  public void setContext(Object context) {
    //
  }

  /**
   * {@inheritDoc}
   */
  public void debug(String msg, Object... args) {
    if (isDebuggingEnabled()) {
      log(Priority.debug, msg, args);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void error(String msg, Object... args) {
    log(Priority.error, msg, args);
  }

  /**
   * {@inheritDoc}
   */
  public void info(String msg, Object... args) {
    log(Priority.info, msg, args);
  }

  /**
   * {@inheritDoc}
   */
  public void trace(String msg, Object... args) {
    if (isTraceingEnabled()) {
      log(Priority.trace, msg, args);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void warn(String msg, Object... args) {
    log(Priority.warn, msg, args);
  }

  /**
   * Dumps a log into the console.
   * 
   * @param level
   *          The level as declared by one of the level constants above.
   * @param msg
   *          The formatting message.
   * @param args
   *          The arguments to be used for the formatting message.
   */
  private void log(Priority level, String msg, Object... args) {
    this._printer.println("[" + level + "] " + String.format(msg, args));
  }

} /* ENDCLASS */
