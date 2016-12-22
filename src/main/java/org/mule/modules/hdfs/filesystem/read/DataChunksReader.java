/**
 * (c) 2003-2016 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.hdfs.filesystem.read;

import org.mule.modules.hdfs.filesystem.dto.DataChunk;

/**
 * @author MuleSoft, Inc.
 */
public interface DataChunksReader {

    DataChunk nextChunk();

    boolean hasNext();

    void close();
}
