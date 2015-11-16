# CDH5 kafka spark streaming example
with json and filtering

Append something to https://github.com/apache/spark/blob/master/examples/src/main/scala/org/apache/spark/examples/streaming/KafkaWordCount.scala

Targets are follows
- spark-submit on yarn-cluster
- build for CDH5.4.3 with sbt
- Json parsing and filtering for DStream

## build
~~~bash
sbt -Dhadoop.version=2.6.0-cdh5.4.3 assembly
~~~
## submit
~~~bash
spark-submit --class example.KafkaWordCount --master yarn-cluster --num-executors 2 --driver-memory 2g --executor-memory 1g --executor-cores 1 ${EXAMPLE_PATH}/example.jar zk-quorum-host:3181 group1 topic1 1 /tmp/output
~~~
