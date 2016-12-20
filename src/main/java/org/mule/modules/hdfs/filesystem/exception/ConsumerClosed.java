/**
 * (c) 2003-2016 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.hdfs.filesystem.exception;

/**
 * @author MuleSoft, Inc.
 */
public class ConsumerClosed extends RuntimeIO {

    private static final long serialVersionUID = 5020757614632933073L;

    public ConsumerClosed(String message) {
        super(message, null);
    }
}
