#
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

from __future__ import print_function
import sys
from pyspark.sql import SparkSession

def odps_datasource(spark, table, project, accessKeyId, accessKeySecret):
    df = spark.read.load("examples/src/main/resources/users.parquet")
    df.select("name", "favorite_color").write.format("org.apache.spark.aliyun.odps.datasource")\
    .option("odpsUrl", "http://service.odps.aliyun.com/api")\
    .option("tunnelUrl", "http://dt.odps.aliyun.com")\
    .option("table", table)\
    .option("project", project)\
    .option("accessKeySecret", accessKeySecret)\
    .option("accessKeyId", accessKeyId).mode("overwrite").save()

    df = spark.read\
    .format("org.apache.spark.aliyun.odps.datasource")\
    .option("odpsUrl", "http://service.odps.aliyun.com/api")\
    .option("tunnelUrl", "http://dt.odps.aliyun.com")\
    .option("table", table)\
    .option("project", project)\
    .option("accessKeySecret", accessKeySecret)\
    .option("accessKeyId", accessKeyId).load()

    df.printSchema()
    for record in df.collect():
        print(record)

if __name__ == "__main__":

    if len(sys.argv) != 5:
        print("Usage: odps_datasource <project> <table> <accessKeyId> <accessKeySecret>", file=sys.stderr)
        exit(-1)

    spark = SparkSession \
        .builder \
        .appName("Odps data source sample") \
        .getOrCreate()

    project = sys.argv[1]
    table = sys.argv[2]
    accessKeyId = sys.argv[3]
    accessKeySecret = sys.argv[4]
    odps_datasource(spark, table, project, accessKeyId, accessKeySecret)

    spark.stop()