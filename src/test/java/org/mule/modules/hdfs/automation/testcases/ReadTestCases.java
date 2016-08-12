/**
 * (c) 2003-2016 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.hdfs.automation.testcases;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.hamcrest.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.internal.matchers.TypeSafeMatcher;
import org.junit.rules.ExpectedException;
import org.mule.construct.Flow;
import org.mule.modules.hdfs.automation.HDFSTestParent;
import org.mule.modules.hdfs.automation.RegressionTests;
import org.mule.modules.hdfs.exception.HDFSConnectorException;
import org.mule.modules.tests.ConnectorTestUtils;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ReadTestCases extends HDFSTestParent {

    String fileContentString;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("readTestData");
        InputStream fileContent = getTestRunMessageValue("payloadRef");
        fileContentString = IOUtils.toString(fileContent);
        upsertOnTestRunMessage("payloadRef", IOUtils.toInputStream(fileContentString));
        runFlowAndGetPayload("write-default-values");
    }

    @After
    public void tearDown() throws Exception {
        runFlowAndGetPayload("delete-file");
    }

    @Category({ RegressionTests.class
    })
    @Test
    public void testRead() {
        try {
            Flow flow = muleContext.getRegistry()
                    .get("read");
            flow.start();
            Object payload = muleContext.getClient()
                    .request("vm://receive", 5000)
                    .getPayload();
            assertEquals(fileContentString, payload);

        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @Category({ RegressionTests.class
    })
    @Test
    public void testReadOperation() throws Exception {
        String payload = runFlowAndGetPayload("readOperation");
        assertEquals(fileContentString, payload);
    }

    @Category({ RegressionTests.class
    })
    @Test
    public void testReadOperationWhenFileDoesNotExist() throws Exception {
        expectedException.expect(new IsCausedByMatcher(HDFSConnectorException.class, ""));
        runFlowAndGetPayload("readOperationWhenFileDoesNotExist");
    }

    private static class IsCausedByMatcher extends TypeSafeMatcher<Throwable> {

        private final Class<? extends Throwable> type;
        private final String expectedMessage;

        public IsCausedByMatcher(Class<? extends Throwable> type, String expectedMessage) {
            this.type = type;
            this.expectedMessage = expectedMessage;
        }

        @Override
        public boolean matchesSafely(Throwable item) {
            return item.getCause()
                    .getClass()
                    .isAssignableFrom(type)
                    && item.getCause()
                            .getMessage()
                            .contains(expectedMessage);
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("expects cause by type ")
                    .appendValue(type);
            if (StringUtils.isNotEmpty(expectedMessage)) {
                description.appendText(" and a cause by message ")
                        .appendValue(expectedMessage);
            }
        }
    }

}
