package org.ant4eclipse.ant.platform;

import static org.ant4eclipse.lib.core.logging.A4ELogging.trace;

import java.io.File;
import java.util.Collection;

import org.ant4eclipse.ant.platform.core.MacroExecutionValues;
import org.ant4eclipse.ant.platform.core.ScopedMacroDefinition;
import org.ant4eclipse.ant.platform.core.delegate.MacroExecutionValuesProvider;
import org.ant4eclipse.ant.platform.core.task.AbstractExecuteProjectTask;
import org.ant4eclipse.lib.core.exception.Ant4EclipseException;
import org.ant4eclipse.lib.core.service.ServiceRegistry;
import org.ant4eclipse.lib.platform.PlatformExceptionCode;
import org.ant4eclipse.lib.platform.model.launcher.LaunchConfiguration;
import org.ant4eclipse.lib.platform.model.launcher.LaunchConfigurationReader;
import org.ant4eclipse.lib.platform.model.resource.variable.EclipseStringSubstitutionService;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.MacroDef;

public class ExecuteLauncherTask extends AbstractExecuteProjectTask {

  private static final String SCOPE_NAME_LAUNCHER = "forLauncher";

  private File                _launchConfigurationFile;

  public File getLaunchConfigurationFile() {
    return this._launchConfigurationFile;
  }

  public void setLaunchConfigurationFile(File launchConfiguration) {
    this._launchConfigurationFile = launchConfiguration;
  }

  public ExecuteLauncherTask(String prefix) {
    super(prefix);
  }

  public ExecuteLauncherTask() {
    this("executeLauncher");
  }

  @Override
  protected void doExecute() {

    // make sure the actual launch configuration is supported by this task
    ensureSupportedLaunchConfiguration(getLaunchConfiguration());

    // execute scoped macro definitions
    for (ScopedMacroDefinition<String> scopedMacroDefinition : getScopedMacroDefinitions()) {

      MacroDef macroDef = scopedMacroDefinition.getMacroDef();
      doExecute(scopedMacroDefinition.getScope(), macroDef);

    }
  }

  protected void ensureSupportedLaunchConfiguration(LaunchConfiguration launchConfiguration) {
    // nothing to do: all types are supported
  }

  protected void doExecute(String scope, MacroDef macroDef) {
    // execute SCOPE_LIBRARY
    if (SCOPE_NAME_LAUNCHER.equals(scope)) {
      executeLauncherScopedMacroDef(macroDef);
    }
    // scope unknown
    else {
      throw new Ant4EclipseException(PlatformExceptionCode.UNKNOWN_EXECUTION_SCOPE, scope);
    }
  }

  @Override
  protected void preconditions() throws BuildException {
    super.preconditions();
    // check require fields
    requireWorkspaceAndProjectNameSet();

    if (this._launchConfigurationFile == null) {
      throw new BuildException("You must specify the 'launchConfiguration' property");
    }

    if (!this._launchConfigurationFile.exists()) {
      throw new BuildException("The launch configuration file '" + this._launchConfigurationFile + "' does not exists");
    }

    if (!this._launchConfigurationFile.isFile()) {
      throw new BuildException("The launch configuration file '" + this._launchConfigurationFile + "' is not a file");
    }

  }

  /**
   * The LaunchConfiguration or null if it has not been read
   */
  private LaunchConfiguration _launchConfiguration;

  protected LaunchConfiguration getLaunchConfiguration() {

    if (this._launchConfiguration == null) {

      LaunchConfigurationReader launchConfigurationReader = ServiceRegistry.instance().getService(
          LaunchConfigurationReader.class);

      System.out.println(" * * * READING LAUNCH CONFIG: " + getLaunchConfigurationFile());

      final LaunchConfiguration launchConfiguration = launchConfigurationReader
          .readLaunchConfiguration(getLaunchConfigurationFile());

      this._launchConfiguration = launchConfiguration;
    }
    return this._launchConfiguration;
  }

  private void executeLauncherScopedMacroDef(MacroDef macroDef) {
    executeMacroInstance(macroDef, new MacroExecutionValuesProvider() {
      public MacroExecutionValues provideMacroExecutionValues(MacroExecutionValues values) {
        return provideDefaultMacroExecutionValues(values);
      }
    });
  }

  /**
   * Provides MacroExecutionValues that are valid for all macros
   * 
   * @param values
   * @return
   */
  protected MacroExecutionValues provideDefaultMacroExecutionValues(MacroExecutionValues values) {
    final LaunchConfiguration launchConfiguration = getLaunchConfiguration();
    final EclipseStringSubstitutionService eclipseVariableResolver = ServiceRegistry.instance().getService(
        EclipseStringSubstitutionService.class);
    final Collection<String> attributeNames = launchConfiguration.getAttributeNames();
    for (String attributeName : attributeNames) {
      String rawAttributeValue = launchConfiguration.getAttribute(attributeName);
      String attributeValue = eclipseVariableResolver.substituteEclipseVariables(rawAttributeValue,
          getEclipseProject(), null);
      trace("setting '" + attributeName + "' to '" + attributeValue + "'");
      values.getProperties().put(attributeName, attributeValue);
    }
    return values;
  }

  public Object createDynamicElement(String name) throws BuildException {
    // handle 'ForLauncher' element
    if (SCOPE_NAME_LAUNCHER.equalsIgnoreCase(name)) {
      return createScopedMacroDefinition(SCOPE_NAME_LAUNCHER);
    }

    // default: not handled
    return null;
  }

}
