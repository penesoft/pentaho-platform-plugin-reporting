package org.pentaho.reporting.platform.plugin;

/*
 * Copyright 2006 - 2008 Pentaho Corporation.  All rights reserved. 
 * This software was developed by Pentaho Corporation and is provided under the terms 
 * of the Mozilla Public License, Version 1.1, or any later version. You may not use 
 * this file except in compliance with the license. If you need a copy of the license, 
 * please go to http://www.mozilla.org/MPL/MPL-1.1.txt. The Original Code is the Pentaho 
 * BI Platform.  The Initial Developer is Pentaho Corporation.
 *
 * Software distributed under the Mozilla Public License is distributed on an "AS IS" 
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or  implied. Please refer to 
 * the license for the specific language governing your rights and limitations.
 *
 * Created Jan 9, 2006, Jan 15, 2009
 * @author mbatchel, mdamour
 */

import org.pentaho.platform.api.engine.IPentahoSession;
import org.pentaho.platform.api.engine.IPentahoSystemListener;
import org.pentaho.platform.util.logging.Logger;
import org.pentaho.reporting.engine.classic.core.ClassicEngineBoot;

public class ReportingSystemStartupListener implements IPentahoSystemListener {
  public ReportingSystemStartupListener()
  {
  }

  /*
   * This method will startup the classic reporting engine. If the engine is
   * already started the result is a 'no-op'.
   * 
   * The IPentahoSession is optional here, as it is not used internally to this
   * method or class, but it is required for satisfaction of the
   * IPentahoSystemListener interface.
   * 
   * @see
   * org.pentaho.platform.api.engine.IPentahoSystemListener#startup(org.pentaho
   * .platform.api.engine.IPentahoSession)
   */
  public boolean startup(final IPentahoSession session)
  {
    try
    {
      synchronized (ClassicEngineBoot.class)
      {
        if (ClassicEngineBoot.getInstance().isBootDone() == false)
        {
          ClassicEngineBoot.setUserConfig(new ReportingConfiguration());
          ClassicEngineBoot.getInstance().start();
          Logger.debug(ClassicEngineBoot.class.getName(), ClassicEngineBoot.class.getSimpleName() + " startup invoked"); //$NON-NLS-1$
          return true;
        }
      }
    } catch (Exception ex)
    {
      Logger.warn(ReportingSystemStartupListener.class.getName(), "Failed to startup: " + ReportingSystemStartupListener.class.getSimpleName(), ex); //$NON-NLS-1$
    }
    return false;
  }

  public void shutdown()
  {
    // Nothing required
  }

}
