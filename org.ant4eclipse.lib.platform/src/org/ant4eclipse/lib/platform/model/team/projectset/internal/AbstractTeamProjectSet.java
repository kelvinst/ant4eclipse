/**********************************************************************
 * Copyright (c) 2005-2006 ant4eclipse project team.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Nils Hartmann, Daniel Kasmeroglu, Gerd Wuetherich
 **********************************************************************/
package org.ant4eclipse.lib.platform.model.team.projectset.internal;

import org.ant4eclipse.lib.core.Assure;
import org.ant4eclipse.lib.platform.model.team.projectset.TeamProjectDescription;
import org.ant4eclipse.lib.platform.model.team.projectset.TeamProjectSet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractTeamProjectSet implements TeamProjectSet {

  /** the name of the project set */
  private String                       _name;

  /** the team project descriptions */
  private List<TeamProjectDescription> _projectDescriptions;

  /**
   * Creates a new instance of type TeamProjectSet.
   * 
   * @param name
   *          the name of the team project set.
   */
  public AbstractTeamProjectSet( String name ) {
    Assure.notNull( "name", name );
    _name = name;
    _projectDescriptions = new ArrayList<TeamProjectDescription>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {
    return _name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TeamProjectDescription[] getTeamProjectDescriptions() {
    return _projectDescriptions.toArray( new TeamProjectDescription[0] );
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TeamProjectDescription getTeamProjectDescriptionByName( String name ) {
    Assure.notNull( "name", name );

    for( TeamProjectDescription description : _projectDescriptions ) {
      if( name.equals( description.getProjectName() ) ) {
        return description;
      }
    }

    throw new RuntimeException( "EclipseProject " + name + " does not exist!" );
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String[] getProjectNames() {

    String[] result = new String[_projectDescriptions.size()];
    int i = 0;
    for( TeamProjectDescription description : _projectDescriptions ) {
      String projectName = description.getProjectName();
      result[i] = projectName;
      i++;
    }

    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    StringBuffer buffer = new StringBuffer();
    buffer.append( "[TeamProjectSet:" );
    buffer.append( " name: " );
    buffer.append( _name );
    buffer.append( " { " );
    for( Iterator<TeamProjectDescription> iterator = _projectDescriptions.iterator(); iterator.hasNext(); ) {
      TeamProjectDescription description = iterator.next();
      buffer.append( description );

      if( iterator.hasNext() ) {
        buffer.append( "," );
      }
    }
    buffer.append( " } " );
    buffer.append( "]" );
    return buffer.toString();
  }

  /**
   * @param description
   */
  protected void addTeamProjectDescription( TeamProjectDescription description ) {
    Assure.notNull( "description", description );
    _projectDescriptions.add( description );
  }

  protected List<TeamProjectDescription> getProjectDescriptions() {
    return _projectDescriptions;
  }

} /* ENDCLASS */
