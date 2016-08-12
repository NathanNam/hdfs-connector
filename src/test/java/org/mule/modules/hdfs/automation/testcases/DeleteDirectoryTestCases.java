/**
 * (c) 2003-2016 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.hdfs.automation.testcases;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.hdfs.HDFSConnector;
import org.mule.modules.hdfs.automation.HDFSTestParent;
import org.mule.modules.hdfs.automation.RegressionTests;
import org.mule.modules.hdfs.automation.SmokeTests;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

public class DeleteDirectoryTestCases extends HDFSTestParent {

    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("deleteDirectoryTestData");
        runFlowAndGetPayload("make-directories");

    }

    @Category({
            SmokeTests.class,
            RegressionTests.class
    })
    @Test
    public void testDeleteDirectory() {
        try {
            runFlowAndGetPayload("delete-directory");
            assertFalse((Boolean) runFlowAndGetInvocationProperty("get-metadata", HDFSConnector.HDFS_PATH_EXISTS));

        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }

    }

}
