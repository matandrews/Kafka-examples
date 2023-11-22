
# Kafka Producer

This is a very simple Kafka producer which writes a key/value pair to a kafka topic.  The data written is just a string to ensure no special serialisers are required.

## Pre-requisites

The solution assumes you have a working Kafka environment at *localhost:9092*.  If you don't have this environment use the docker compose files mentioned in the root README.md file.

### Create Sink Topic

To create the sink (output) topic, the kafka pod is accessed and the kafka-topics command is executed within it, as follows.
```
$ docker exec kafka1 kafka-topics --create --if-not-exists --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1 --topic SimpleStringTopic
```

docker exec *podname* *command*

Here the pod is called *kafka1*.  This can be determined by using the command
```agsl
$ docker ps --format "table {{.ID}}\t{{.Names}}\t{{.Status}}"
CONTAINER ID   NAMES     STATUS
ba3be6ade29a   kafka1    Up 6 minutes
0e249788f81f   zoo1      Up 6 minutes
```

### Viewing messages on the Topic
To validate the Kakfa producer has output the messages to the topic, the following command can be used.  Again this will be executed within the docker pod, so ensure that the pod name is correct (in the following example *"'"kafka1"*).

```agsl
$ docker exec kafka1 kafka-console-consumer --bootstrap-server localhost:9092 --topic SimpleStringTopic --from-beginning
0
1
2
3
4
...

```
