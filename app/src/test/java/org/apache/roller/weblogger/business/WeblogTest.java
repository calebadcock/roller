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
package org.apache.roller.weblogger.business;

import java.util.Collections;
import java.util.List;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.roller.weblogger.pojos.Weblog;

/**
 * WeblogTest
 * Test cases for the Weblog methods
 * Caleb Adcock
 */
public class WeblogTest extends TestCase {

    class WeblogPerm {
        String username;
        String action;
        WeblogPerm(String username, String action) {
            this.username = username;
            this.action = action;
        }
        boolean hasAction(String action) {
            return this.action.equals(action);
        }
    }
    /**
     * Function from MainMenu.jsp translated
     * <s:iterator id="weblogPerm" value="#perms.weblog.memberPermissions">
     *   <tr>
     *     <td><s:property value="#weblogPerm.userName" /></td>
     *     <td>
     *       <s:if test='#weblogPerm.hasAction("admin")'      >ADMIN</s:if>
     *       <s:if test='#weblogPerm.hasAction("post")'       >AUTHOR</s:if>
     *       <s:if test='#weblogPerm.hasAction("edit_draft")' >LIMITED</s:if>
     *     </td>
     *  </tr>
     * </s:iterator>
     */
    public static String[][] iterateThroughPerms(WeblogPerm[] perms) {
        String[][] values = new String[perms.length][2];
        for (int i = 0; i < perms.length; i++) {
            values[i][0] = perms[i].username;
            if (perms[i].hasAction("admin")) {
                values[i][1] = "ADMIN";
            } else if (perms[i].hasAction("post")) {
                values[i][1] = "AUTHOR";
            } else if (perms[i].hasAction("edit_draft")) {
                values[i][1] = "LIMITED";
            }
        }
        return values;
    }
    public static Log log = LogFactory.getLog(WeblogTest.class);

    /**
     * Test a Weblog with no permissions
     * @throws Exception
     */
    public void testCase1() throws Exception {
        log.info("BEGIN");
        WeblogPerm[] perms = {};
        String[][] tableValues = iterateThroughPerms(perms);
        assertEquals(tableValues.length, 0);
        log.info("END");
    }

    /**
     * Test a Weblog with 1 permission
     * Should go into for loop and skip all ifs
     * @throws Exception
     */
    public void testCase2() throws Exception {
        log.info("BEGIN");
        WeblogPerm[] perms = {
                new WeblogPerm("testUser", "")
        };
        String[][] tableValues = iterateThroughPerms(perms);
        assertEquals(tableValues.length, 1);
        assertEquals(tableValues[0][0], "testUser");
        assertEquals(tableValues[0][1], null);
        log.info("END");
    }

    /**
     * Test a Weblog with 1 permission
     * Should go into for loop and go into first if
     * @throws Exception
     */
    public void testCase3() throws Exception {
        log.info("BEGIN");
        WeblogPerm[] perms = {
                new WeblogPerm("testUser", "admin")
        };
        String[][] tableValues = iterateThroughPerms(perms);
        assertEquals(tableValues.length, 1);
        assertEquals(tableValues[0][0], "testUser");
        assertEquals(tableValues[0][1], "ADMIN");
        log.info("END");
    }

    /**
     * Test a Weblog with 1 permission
     * Should go into for loop and go into second if
     * @throws Exception
     */
    public void testCase4() throws Exception {
        log.info("BEGIN");
        WeblogPerm[] perms = {
                new WeblogPerm("testUser", "post")
        };
        String[][] tableValues = iterateThroughPerms(perms);
        assertEquals(tableValues.length, 1);
        assertEquals(tableValues[0][0], "testUser");
        assertEquals(tableValues[0][1], "AUTHOR");
        log.info("END");
    }

    /**
     * Test a Weblog with 1 permission
     * Should go into for loop and go into third if
     * @throws Exception
     */
    public void testCase5() throws Exception {
        log.info("BEGIN");
        WeblogPerm[] perms = {
                new WeblogPerm("testUser", "edit_draft")
        };
        String[][] tableValues = iterateThroughPerms(perms);
        assertEquals(tableValues.length, 1);
        assertEquals(tableValues[0][0], "testUser");
        assertEquals(tableValues[0][1], "LIMITED");
        log.info("END");
    }

    /**
     * Test a Weblog with 3 permissions
     * Should go into for loop and go into first if, next iteration: second if, and then last iteration: third if.
     * @throws Exception
     */
    public void testCase6() throws Exception {
        log.info("BEGIN");
        WeblogPerm[] perms = {
                new WeblogPerm("testUser1", ""),
                new WeblogPerm("testUser2", ""),
                new WeblogPerm("testUser3", "")
        };
        String[][] tableValues = iterateThroughPerms(perms);
        assertEquals(tableValues.length, 3);
        assertEquals(tableValues[0][0], "testUser1");
        assertEquals(tableValues[0][1], null);

        assertEquals(tableValues[1][0], "testUser2");
        assertEquals(tableValues[1][1], null);

        assertEquals(tableValues[2][0], "testUser3");
        assertEquals(tableValues[2][1], null);
        log.info("END");
    }

    /**
     * Test a Weblog with 3 permissions
     * Should go into for loop and go into first if
     * @throws Exception
     */
    public void testCase7() throws Exception {
        log.info("BEGIN");
        WeblogPerm[] perms = {
                new WeblogPerm("testUser1", "admin"),
                new WeblogPerm("testUser2", "admin"),
                new WeblogPerm("testUser3", "admin")
        };
        String[][] tableValues = iterateThroughPerms(perms);
        assertEquals(tableValues.length, 3);
        assertEquals(tableValues[0][0], "testUser1");
        assertEquals(tableValues[0][1], "ADMIN");

        assertEquals(tableValues[1][0], "testUser2");
        assertEquals(tableValues[1][1], "ADMIN");

        assertEquals(tableValues[2][0], "testUser3");
        assertEquals(tableValues[2][1], "ADMIN");
        log.info("END");
    }

    /**
     * Test a Weblog with 3 permissions
     * Should go into for loop and go into second if
     * @throws Exception
     */
    public void testCase8() throws Exception {
        log.info("BEGIN");
        WeblogPerm[] perms = {
                new WeblogPerm("testUser1", "post"),
                new WeblogPerm("testUser2", "post"),
                new WeblogPerm("testUser3", "post")
        };
        String[][] tableValues = iterateThroughPerms(perms);
        assertEquals(tableValues.length, 3);
        assertEquals(tableValues[0][0], "testUser1");
        assertEquals(tableValues[0][1], "AUTHOR");

        assertEquals(tableValues[1][0], "testUser2");
        assertEquals(tableValues[1][1], "AUTHOR");

        assertEquals(tableValues[2][0], "testUser3");
        assertEquals(tableValues[2][1], "AUTHOR");
        log.info("END");
    }

    /**
     * Test a Weblog with 3 permissions
     * Should go into for loop and go into third if
     * @throws Exception
     */
    public void testCase9() throws Exception {
        log.info("BEGIN");
        WeblogPerm[] perms = {
                new WeblogPerm("testUser1", "edit_draft"),
                new WeblogPerm("testUser2", "edit_draft"),
                new WeblogPerm("testUser3", "edit_draft")
        };
        String[][] tableValues = iterateThroughPerms(perms);
        assertEquals(tableValues.length, 3);
        assertEquals(tableValues[0][0], "testUser1");
        assertEquals(tableValues[0][1], "LIMITED");

        assertEquals(tableValues[1][0], "testUser2");
        assertEquals(tableValues[1][1], "LIMITED");

        assertEquals(tableValues[2][0], "testUser3");
        assertEquals(tableValues[2][1], "LIMITED");
        log.info("END");
    }
}

