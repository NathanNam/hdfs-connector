/**
 * (c) 2003-2016 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.hdfs.automation.functional;

import org.junit.Rule;
import org.junit.rules.Timeout;
import org.mule.modules.hdfs.HDFSConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

public class AbstractTestCases extends AbstractTestCase<HDFSConnector> {

    // Set global timeout of tests to 5 minutes
    @Rule
    public Timeout globalTimeout = new Timeout(300000);

    public AbstractTestCases() {
        super(HDFSConnector.class);
    }
}
