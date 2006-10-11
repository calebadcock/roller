/*
* Licensed to the Apache Software Foundation (ASF) under one or more
*  contributor license agreements.  The ASF licenses this file to You
* under the Apache License, Version 2.0 (the "License"); you may not
* use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.  For additional information regarding
* copyright in this work, please see the NOTICE file in the top level
* directory of this distribution.
*/

package org.apache.roller.planet.model;

import org.apache.roller.RollerException;
import org.apache.roller.planet.model.PlanetManager;


/** 
 * The main entry point interface of the Roller business tier.
 */
public interface Planet {
           
    /**
     * Get PlanetManager associated with this Roller instance.
     */
    public PlanetManager getPlanetManager() throws RollerException;
        
    /**
     * Flush object states.
     */
    public void flush() throws RollerException;
    
    
    /**
     * Release all resources associated with Roller session.
     */
    public void release();
    
    
    /**
     * Release all resources necessary for this instance of Roller.
     */
    public void shutdown();
    
}
