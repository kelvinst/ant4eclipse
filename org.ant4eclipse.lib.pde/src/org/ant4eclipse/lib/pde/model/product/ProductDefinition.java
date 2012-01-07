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
package org.ant4eclipse.lib.pde.model.product;

import org.ant4eclipse.lib.pde.tools.BundleStartRecord;
import org.osgi.framework.Version;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * <p>
 * Represents an eclipse product definition (<code>*.product</code>).
 * </p>
 * 
 * @author Daniel Kasmeroglu (Daniel.Kasmeroglu@Kasisoft.net)
 */
public class ProductDefinition {

  private String                        _name;

  private String                        _uid;

  private String                        _id;

  private String                        _application;

  private Version                       _version;

  private boolean                       _basedonfeatures;

  private Map<String,BundleStartRecord> _configrecords;

  private List<String>                  _pluginids;

  private List<String>                  _fragmentids;

  private Map<ProductOs,String>         _configini;

  private Map<ProductOs,String>         _programargs;

  private Map<ProductOs,String>         _vmargs;

  private String                        _launchername;

  private Map<ProductOs,String>         _vm;

  private String                        _splashplugin;

  private Map<String,Version>           _features;

  /**
   * Initialises this data structure.
   */
  public ProductDefinition() {
    _configrecords = new HashMap<String,BundleStartRecord>();
    _pluginids = new ArrayList<String>();
    _fragmentids = new ArrayList<String>();
    _name = null;
    _uid = null;
    _id = null;
    _application = null;
    _version = Version.emptyVersion;
    _basedonfeatures = false;
    _configini = new Hashtable<ProductOs,String>();
    _programargs = new Hashtable<ProductOs,String>();
    _vmargs = new Hashtable<ProductOs,String>();
    _launchername = null;
    _vm = new Hashtable<ProductOs,String>();
    _splashplugin = null;
    _features = new Hashtable<String,Version>();
  }

  /**
   * Adds a feature with a specified version to this product definition.
   * 
   * @param featureid
   *          The id of the feature. Neither <code>null</code> nor empty.
   * @param version
   *          The version of the associated feature. Not <code>null</code>.
   */
  void addFeature( String featureid, Version version ) {
    _features.put( featureid, version );
  }

  /**
   * Returns a list of all currently registered feature ids.
   * 
   * @return A list of all currently registered feature ids. Not <code>null</code>.
   */
  public String[] getFeatureIds() {
    String[] result = _features.keySet().toArray( new String[_features.size()] );
    Arrays.sort( result );
    return result;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public FeatureId[] getFeatureIdentifiers() {

    //
    List<FeatureId> result = new ArrayList<FeatureId>();

    //
    for( Entry<String,Version> featureId : _features.entrySet() ) {
      result.add( new FeatureId( featureId.getKey(), featureId.getValue() ) );
    }

    //
    return result.toArray( new FeatureId[0] );
  }

  /**
   * Returns a list of all ConfigurationRecord instances.
   * 
   * @return A list of all ConfigurationRecord instances. Not <code>null</code>.
   */
  public BundleStartRecord[] getConfigurationRecords() {
    BundleStartRecord[] result = new BundleStartRecord[_configrecords.size()];
    _configrecords.values().toArray( result );
    Arrays.sort( result );
    return result;
  }

  /**
   * <p>
   * </p>
   * 
   * @param id
   * @return
   */
  public boolean hasConfigurationRecord( String id ) {
    return _configrecords.containsKey( id );
  }

  /**
   * <p>
   * </p>
   * 
   * @param id
   * @return
   */
  public BundleStartRecord getConfigurationRecord( String id ) {
    return _configrecords.get( id );
  }

  /**
   * Returns the version associated with the supplied feature.
   * 
   * @param feature
   *          The id of the feature used to get the version from. Neither <code>null</code> nor empty.
   * 
   * @return The version of the supplied version. <code>null</code> if the feature is not valid.
   */
  public Version getFeatureVersion( String feature ) {
    return _features.get( feature );
  }

  /**
   * Changes the id of the plugin providing the splash screen.
   * 
   * @param pluginid
   *          The id of the plugin providing the splash screen.
   */
  void setSplashplugin( String pluginid ) {
    _splashplugin = pluginid;
  }

  /**
   * Returns the id of the plugin providing the splash screen.
   * 
   * @return The id of the plugin providing the splash screen. Maybe <code>null</code>.
   */
  public String getSplashplugin() {
    return _splashplugin;
  }

  public boolean hasSplashplugin() {
    return _splashplugin != null;
  }

  /**
   * Changes the name of the launcher.
   * 
   * @param newlaunchername
   *          The new name of the launcher. Neither <code>null</code> nor empty.
   */
  void setLaunchername( String newlaunchername ) {
    _launchername = newlaunchername;
  }

  /**
   * Returns the current name of the launcher.
   * 
   * @return The current name of the launcher.
   */
  public String getLaunchername() {
    return _launchername;
  }

  public boolean hasLaunchername() {
    return _launchername != null;
  }

  /**
   * Registers the required vm for a specific os.
   * 
   * @param os
   *          The associated operating system. Not <code>null</code>.
   * @param vm
   *          The required vm for this operating system. If not <code>null</code> it must be non empty.
   */
  void addVm( ProductOs os, String vm ) {
    if( vm != null ) {
      _vm.put( os, vm.trim() );
    }
  }

  /**
   * Returns the required vm for the supplied operating system.
   * 
   * @param os
   *          The operating system used to access the required vm. Not <code>null</code>.
   * 
   * @return The required vm or <code>null</code> if none has been specified.
   */
  public String getVm( ProductOs os ) {
    return _vm.get( os );
  }

  /**
   * Registers a set of vm arguments for a specific os.
   * 
   * @param os
   *          The associated operating system. Not <code>null</code>.
   * @param args
   *          The vm arguments for this operating system. If not <code>null</code> it must be non empty.
   */
  void addVmArgs( ProductOs os, String args ) {
    if( args != null ) {
      String oldargs = "";
      if( _vmargs.containsKey( os ) ) {
        oldargs = _vmargs.get( os ) + " ";
      }
      _vmargs.put( os, oldargs + args.replace( '\n', ' ' ).trim() );
    }
  }

  /**
   * Returns the vm arguments used for the supplied operating system.
   * 
   * @param os
   *          The operating system used to access the vm arguments. Not <code>null</code>.
   * 
   * @return The vm arguments. Not <code>null</code>.
   */
  public String getVmArgs( ProductOs os ) {
    if( _vmargs.containsKey( os ) ) {
      return _vmargs.get( os );
    } else {
      return "";
    }
  }

  /**
   * Registers a set of program arguments for a specific os.
   * 
   * @param os
   *          The associated operating system. Not <code>null</code>.
   * @param args
   *          The program arguments for this operating system. If not <code>null</code> it must be non empty.
   */
  void addProgramArgs( ProductOs os, String args ) {
    if( args != null ) {
      String oldargs = "";
      if( _programargs.containsKey( os ) ) {
        oldargs = _programargs.get( os ) + " ";
      }
      _programargs.put( os, oldargs + args.replace( '\n', ' ' ).trim() );
    }
  }

  /**
   * Returns the program arguments used for the supplied operating system.
   * 
   * @param os
   *          The operating system used to access the program arguments. Not <code>null</code>.
   * 
   * @return The program arguments. Not <code>null</code>.
   */
  public String getProgramArgs( ProductOs os ) {
    if( _programargs.containsKey( os ) ) {
      return _programargs.get( os );
    } else {
      return "";
    }
  }

  /**
   * Registers a config ini path for a specific os. The path is always workspace relative which means it begins with a
   * leading slash.
   * 
   * @param os
   *          The associated operating system. Not <code>null</code>.
   * @param path
   *          The path to be used for this operating system. If not <code>null</code> it must be non empty.
   */
  void addConfigIni( ProductOs os, String path ) {
    if( path != null ) {
      _configini.put( os, path.trim() );
    }
  }

  /**
   * Adds the supplied ConfigurationRecord to this product definition.
   * 
   * @param record
   *          The ConfigurationRecord which has to be added. Not <code>null</code>.
   */
  void addConfigurationRecord( BundleStartRecord record ) {
    _configrecords.put( record.getId(), record );
  }

  /**
   * Returns a config.ini path used for the supplied operating system. The path is always workspace relative which means
   * it begins with a leading slash.
   * 
   * @param os
   *          The operating system used to access the path. Not <code>null</code>.
   * 
   * @return The config.ini path or <code>null</code> if none has been specified.
   */
  public String getConfigIni( ProductOs os ) {
    return _configini.get( os );
  }

  /**
   * Registers the supplied plugin id.
   * 
   * @param pluginid
   *          The id of the included plugin. Neither <code>null</code> nor empty.
   * @param isfragment
   *          <code>true</code> <=> The plugin is a framgent.
   */
  void addPlugin( String pluginid, boolean isfragment ) {
    if( isfragment ) {
      _fragmentids.add( pluginid );
    } else {
      _pluginids.add( pluginid );
    }
  }

  /**
   * Returns a list of all currently registered plugin ids.
   * 
   * @return A list of all currently registered plugin ids. Not <code>null</code>.
   */
  public String[] getPluginIds() {
    return _pluginids.toArray( new String[_pluginids.size()] );
  }

  /**
   * Returns a list of all currently registered fragment ids.
   * 
   * @return A list of all currently registered fragment ids. Not <code>null</code>.
   */
  public String[] getFragmentIds() {
    return _fragmentids.toArray( new String[_fragmentids.size()] );
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public List<String> getPluginAndFragmentIds() {

    List<String> result = new ArrayList<String>();

    result.addAll( _pluginids );
    result.addAll( _fragmentids );

    return result;
  }

  /**
   * Changes the name for this product.
   * 
   * @param newname
   *          The new name for this product. Neither <code>null</code> nor empty.
   */
  void setName( String newname ) {
    _name = newname;
  }

  /**
   * Returns the name for this product.
   * 
   * @return The name for this product. Neither <code>null</code> nor empty.
   */
  public String getName() {
    return _name;
  }

  public boolean hasName() {
    return _name != null;
  }

  /**
   * <p>
   * Returns the uid.
   * </p>
   * 
   * @return the
   */
  public String getUid() {
    return _uid;
  }

  /**
   * <p>
   * Sets the uid.
   * </p>
   * 
   * @param uid
   */
  public void setUid( String uid ) {
    _uid = uid;
  }

  /**
   * Changes the id for this product.
   * 
   * @param newid
   *          The new id for this product. Neither <code>null</code> nor empty.
   */
  void setId( String newid ) {
    _id = newid;
  }

  /**
   * Returns the id for this product.
   * 
   * @return The id for this product. Neither <code>null</code> nor empty.
   */
  public String getId() {
    return _id;
  }

  public boolean hasId() {
    return _id != null;
  }

  /**
   * Changes the application id associated with this product.
   * 
   * @param newapplication
   *          The new application id. Neither <code>null</code> nor empty.
   */
  void setApplication( String newapplication ) {
    _application = newapplication;
  }

  /**
   * Returns the id of the product application.
   * 
   * @return The id of the product application. Neither <code>null</code> nor empty.
   */
  public String getApplication() {
    return _application;
  }

  public boolean hasApplication() {
    return _application != null;
  }

  /**
   * Changes the version for this product.
   * 
   * @param newversion
   *          The new version for this product. Not <code>null</code>.
   */
  void setVersion( Version newversion ) {
    _version = newversion;
  }

  /**
   * Returns the version of the product.
   * 
   * @return The version of the product. Not <code>null</code>.
   */
  public Version getVersion() {
    return _version;
  }

  public boolean hasVersion() {
    return _version != null;
  }

  /**
   * (Un)Marks this product as based on features.
   * 
   * @param newbasedonfeatures
   *          <code>true</code> <=> This product is based on features.
   */
  void setBasedOnFeatures( boolean newbasedonfeatures ) {
    _basedonfeatures = newbasedonfeatures;
  }

  /**
   * Returns <code>true</code> if this product is based on features rather than plugins.
   * 
   * @return <code>true</code> <=> This product is based on features.
   */
  public boolean isBasedOnFeatures() {
    return _basedonfeatures;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    StringBuffer buffer = new StringBuffer();
    buffer.append( "[ProductDefinition:" );
    buffer.append( " _name: " );
    buffer.append( _name );
    buffer.append( ", _id: " );
    buffer.append( _id );
    buffer.append( ", _application: " );
    buffer.append( _application );
    buffer.append( ", _version: " );
    buffer.append( _version );
    buffer.append( ", _launchername: " );
    buffer.append( _launchername );
    buffer.append( ", _splashplugin: " );
    buffer.append( _splashplugin );
    buffer.append( ", _basedonfeatures: " );
    buffer.append( _basedonfeatures );
    if( _basedonfeatures ) {
      String[] featureids = getFeatureIds();
      buffer.append( ", _features: {" );
      if( featureids.length > 0 ) {
        buffer.append( featureids[0] );
        buffer.append( "=" );
        buffer.append( getFeatureVersion( featureids[0] ) );
        for( int i = 1; i < featureids.length; i++ ) {
          buffer.append( "," );
          buffer.append( featureids[i] );
          buffer.append( "=" );
          buffer.append( getFeatureVersion( featureids[i] ) );
        }
      }
      buffer.append( "}" );
    } else {
      buffer.append( ", _pluginids: {" );
      if( !_pluginids.isEmpty() ) {
        buffer.append( _pluginids.get( 0 ) );
        for( int i = 1; i < _pluginids.size(); i++ ) {
          buffer.append( "," );
          buffer.append( _pluginids.get( i ) );
        }
      }
      buffer.append( "}" );
      buffer.append( ", _fragmentids: {" );
      if( !_fragmentids.isEmpty() ) {
        buffer.append( _fragmentids.get( 0 ) );
        for( int i = 1; i < _fragmentids.size(); i++ ) {
          buffer.append( "," );
          buffer.append( _fragmentids.get( i ) );
        }
      }
      buffer.append( "}" );
    }
    buffer.append( ", _configini: {" );
    boolean first = true;
    for( ProductOs os : ProductOs.values() ) {
      String configini = getConfigIni( os );
      if( configini != null ) {
        if( !first ) {
          buffer.append( ", " );
        }
        buffer.append( os );
        buffer.append( "=" );
        buffer.append( configini );
        first = false;
      }
    }
    buffer.append( "}" );
    buffer.append( ", _programargs: {" );
    first = true;
    for( ProductOs os : ProductOs.values() ) {
      String programargs = getProgramArgs( os );
      if( programargs != null ) {
        if( !first ) {
          buffer.append( ", " );
        }
        buffer.append( os );
        buffer.append( "=" );
        buffer.append( programargs );
        first = false;
      }
    }
    buffer.append( "}" );
    buffer.append( ", _vmargs: {" );
    first = true;
    for( ProductOs os : ProductOs.values() ) {
      String vmargs = getVmArgs( os );
      if( vmargs != null ) {
        if( !first ) {
          buffer.append( ", " );
        }
        buffer.append( os );
        buffer.append( "=" );
        buffer.append( vmargs );
        first = false;
      }
    }
    buffer.append( "}" );
    buffer.append( ", _vm: {" );
    first = true;
    for( ProductOs os : ProductOs.values() ) {
      String vm = getVm( os );
      if( vm != null ) {
        if( !first ) {
          buffer.append( ", " );
        }
        buffer.append( os );
        buffer.append( "=" );
        buffer.append( vm );
        first = false;
      }
    }
    buffer.append( "}" );
    buffer.append( "]" );
    return buffer.toString();

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + ((_name == null) ? 0 : _name.hashCode());
    result = 31 * result + ((_id == null) ? 0 : _id.hashCode());
    result = 31 * result + ((_application == null) ? 0 : _application.hashCode());
    result = 31 * result + ((_version == null) ? 0 : _version.hashCode());
    result = 31 * result + ((_launchername == null) ? 0 : _launchername.hashCode());
    result = 31 * result + ((_splashplugin == null) ? 0 : _splashplugin.hashCode());
    result = 31 * result + (_basedonfeatures ? 1 : 0);
    String[] featureids = getFeatureIds();
    for( String featureid : featureids ) {
      result = 31 * result + featureid.hashCode();
      Version version = getFeatureVersion( featureid );
      result = 31 * result + version.hashCode();
    }
    for( int i = 0; i < _pluginids.size(); i++ ) {
      result = 31 * result + _pluginids.get( i ).hashCode();
    }
    for( int i = 0; i < _fragmentids.size(); i++ ) {
      result = 31 * result + _fragmentids.get( i ).hashCode();
    }
    for( ProductOs os : ProductOs.values() ) {
      String configini = getConfigIni( os );
      result = 31 * result + (configini == null ? 0 : configini.hashCode());
    }
    for( ProductOs os : ProductOs.values() ) {
      String programargs = getProgramArgs( os );
      result = 31 * result + (programargs == null ? 0 : programargs.hashCode());
    }
    for( ProductOs os : ProductOs.values() ) {
      String vmargs = getVmArgs( os );
      result = 31 * result + (vmargs == null ? 0 : vmargs.hashCode());
    }
    for( ProductOs os : ProductOs.values() ) {
      String vm = getVm( os );
      result = 31 * result + (vm == null ? 0 : vm.hashCode());
    }
    return result;
  }

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  public class FeatureId {

    private String  _id;

    private Version _version;

    public FeatureId( String id, Version version ) {
      super();
      _id = id;
      _version = version;
    }

    public String getId() {
      return _id;
    }

    public Version getVersion() {
      return _version;
    }
  }

} /* ENDCLASS */
