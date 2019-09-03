/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.spark.sql.aliyun.datahub

import org.apache.spark.sql.catalyst.InternalRow
import org.apache.spark.sql.sources.v2.writer.{DataWriter, DataWriterFactory, WriterCommitMessage}
import org.apache.spark.sql.sources.v2.writer.streaming.StreamWriter

class DatahubStreamWriter extends StreamWriter {
  override def commit(epochId: Long, messages: Array[WriterCommitMessage]): Unit = {

  }

  override def abort(epochId: Long, messages: Array[WriterCommitMessage]): Unit = {

  }

  override def createWriterFactory(): DatahubStreamWriterFactory = {
    DatahubStreamWriterFactory()
  }
}


case class DatahubStreamWriterFactory() extends DataWriterFactory[InternalRow] {

  override def createDataWriter(
      partitionId: Int,
      taskId: Long,
      epochId: Long): DataWriter[InternalRow] = {
    new DatahubStreamDataWriter()
  }
}

class DatahubStreamDataWriter extends DataWriter[InternalRow] {
  override def commit(): WriterCommitMessage = {
    DatahubWriterCommitMessage
  }

  override def abort(): Unit = {

  }

  override def write(record: InternalRow): Unit = {

  }
}

case object DatahubWriterCommitMessage extends WriterCommitMessage