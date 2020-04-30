/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.aliyun.fs.oss.utils;

import java.io.IOException;

public abstract class Task implements Runnable {

  protected Object response;
  protected String uuid = this.toString();
  private TaskEngine taskEngine;

  public void setTaskEngine(TaskEngine taskEngine) {
    this.taskEngine = taskEngine;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  @Override
  public void run() {
    try {
      execute(taskEngine);
    } catch (IOException e) {
      e.printStackTrace();
    }
    taskEngine.registerResponse(uuid, response);
    taskEngine.reportCompleted();
  }

  public abstract void execute(TaskEngine engineRef) throws IOException;
}
