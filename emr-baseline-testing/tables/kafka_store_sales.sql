CREATE DATABASE IF NOT EXISTS ${DB};
USE ${DB};

DROP TABLE IF EXISTS kafka_store_sales;
CREATE TABLE kafka_store_sales
USING kafka OPTIONS (
kafka.bootstrap.servers = "${BOOTSTRAP_SERVERS}",
subscribe = 'storesales',
output.mode = 'append',
kafka.schema.registry.url = "${SCHEMA_REGISTRY_URL}",
kafka.schema.record.name = 'StoreSales',
kafka.schema.record.namespace = 'org.apache.spark.emr.baseline.testing',
kafka.auto.register.schemas = 'true');
