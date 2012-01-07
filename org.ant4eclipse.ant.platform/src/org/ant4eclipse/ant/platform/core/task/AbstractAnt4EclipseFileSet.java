package org.ant4eclipse.ant.platform.core.task;

import org.ant4eclipse.ant.core.AbstractAnt4EclipseDataType;
import org.ant4eclipse.ant.platform.core.EclipseProjectComponent;
import org.ant4eclipse.ant.platform.core.delegate.EclipseProjectDelegate;
import org.ant4eclipse.lib.platform.model.resource.EclipseProject;
import org.ant4eclipse.lib.platform.model.resource.Workspace;
import org.ant4eclipse.lib.platform.model.resource.role.ProjectRole;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.Reference;
import org.apache.tools.ant.types.Resource;
import org.apache.tools.ant.types.ResourceCollection;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractAnt4EclipseFileSet extends AbstractAnt4EclipseDataType implements ResourceCollection, EclipseProjectComponent {

  /** the ant attribute 'useDefaultExcludes' */
  private boolean                _useDefaultExcludes = true;

  /** the ant attribute 'caseSensitive' */
  private boolean                _caseSensitive      = false;

  /** 'derived' attributes **/

  /** the result resource list */
  private List<Resource>         _resourceList;

  /** the eclipse project delegate */
  private EclipseProjectDelegate _eclipseProjectDelegate;

  /** indicates if the file list already has been computed */
  private boolean                _fileListComputed   = false;

  /**
   * <p>
   * Creates a new instance of type {@link PdeProjectFileSet}.
   * </p>
   * 
   * @param project
   *          the ant project
   */
  public AbstractAnt4EclipseFileSet( Project project ) {
    super( project );

    // create the project delegate
    _eclipseProjectDelegate = new EclipseProjectDelegate( this );

    // create the result list
    _resourceList = new ArrayList<Resource>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setRefid( Reference ref ) {
    if( isWorkspaceDirectorySet() || isProjectNameSet() ) {
      throw tooManyAttributes();
    }

    super.setRefid( ref );
  }

  /**
   * <p>
   * Sets whether default exclusions should be used or not.
   * </p>
   * 
   * @param useDefaultExcludes
   *          <code>boolean</code>.
   */
  public synchronized void setDefaultexcludes( boolean useDefaultExcludes ) {
    if( isReference() ) {
      throw tooManyAttributes();
    }

    _useDefaultExcludes = useDefaultExcludes;
  }

  /**
   * <p>
   * Whether default exclusions should be used or not.
   * </p>
   * 
   * @return the default exclusions value.
   */
  public synchronized boolean getDefaultexcludes() {
    return isReference() ? getRef( getProject() ).getDefaultexcludes() : _useDefaultExcludes;
  }

  /**
   * <p>
   * Sets case sensitivity of the file system.
   * </p>
   * 
   * @param caseSensitive
   *          <code>boolean</code>.
   */
  public synchronized void setCaseSensitive( boolean caseSensitive ) {
    if( isReference() ) {
      throw tooManyAttributes();
    }
    _caseSensitive = caseSensitive;
  }

  /**
   * <p>
   * Find out if the file set is case sensitive.
   * </p>
   * 
   * @return <code>boolean</code> indicating whether the file set is case sensitive.
   */
  public synchronized boolean isCaseSensitive() {
    return isReference() ? getRef( getProject() ).isCaseSensitive() : _caseSensitive;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void ensureRole( Class<? extends ProjectRole> projectRoleClass ) {
    _eclipseProjectDelegate.ensureRole( projectRoleClass );
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EclipseProject getEclipseProject() throws BuildException {
    return _eclipseProjectDelegate.getEclipseProject();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final Workspace getWorkspace() {
    return _eclipseProjectDelegate.getWorkspace();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final File getWorkspaceDirectory() {
    return _eclipseProjectDelegate.getWorkspaceDirectory();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean isProjectNameSet() {
    return _eclipseProjectDelegate.isProjectNameSet();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean isWorkspaceDirectorySet() {
    return _eclipseProjectDelegate.isWorkspaceDirectorySet();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void requireWorkspaceAndProjectNameSet() {
    _eclipseProjectDelegate.requireWorkspaceAndProjectNameSet();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getWorkspaceId() {
    return _eclipseProjectDelegate.getWorkspaceId();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isWorkspaceIdSet() {
    return _eclipseProjectDelegate.isWorkspaceIdSet();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void requireWorkspaceDirectoryOrWorkspaceIdSet() {
    _eclipseProjectDelegate.requireWorkspaceDirectoryOrWorkspaceIdSet();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setWorkspaceId( String identifier ) {
    _eclipseProjectDelegate.setWorkspaceId( identifier );
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Deprecated
  public void setProject( File projectPath ) {
    _eclipseProjectDelegate.setProject( projectPath );
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setProjectName( String projectName ) {
    _eclipseProjectDelegate.setProjectName( projectName );
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Deprecated
  public final void setWorkspace( String workspace ) {
    _eclipseProjectDelegate.setWorkspace( workspace );
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setWorkspaceDirectory( String workspaceDirectory ) {
    _eclipseProjectDelegate.setWorkspaceDirectory( workspaceDirectory );
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isFilesystemOnly() {
    return true;
  }

  /**
   * <p>
   * Performs the check for circular references and returns the referenced {@link PdeProjectFileSet}.
   * </p>
   * 
   * @param p
   *          the current project
   * @return the referenced {@link PdeProjectFileSet}
   */
  protected AbstractAnt4EclipseFileSet getRef( Project p ) {
    return (AbstractAnt4EclipseFileSet) getCheckedRef( p );
  }

  /**
   * <p>
   * </p>
   */
  protected void clear() {
    _resourceList.clear();
    _fileListComputed = false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Iterator<Resource> iterator() {
    computeFileSet();

    return _resourceList.iterator();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int size() {
    computeFileSet();

    return _resourceList.size();
  }

  public boolean isFileListComputed() {
    return _fileListComputed;
  }

  /**
   * <p>
   * Computes the file set if it has not been computed already
   * </p>
   */
  protected void computeFileSet() {
    // return if file list already is computed
    if( _fileListComputed ) {
      return;
    }

    // Clear the FileList
    _resourceList.clear();

    // do the work
    doComputeFileSet( _resourceList );

    // set _fileListComputed
    _fileListComputed = true;
  }

  /**
   * Compute the actual FileSet.
   * 
   * <p>
   * This method will only be called if the FileSet has not been computed already. Implementors don't need to check if
   * it's neccessary to compute the fileset
   */
  /**
   * @param resourceList
   *          The result list all resources should be added to
   */
  protected abstract void doComputeFileSet( List<Resource> resourceList );

} /* ENDCLASS */
