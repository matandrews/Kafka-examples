# Kafka-examples
A collection of sample kafka projects.

## Prerequisites

All the readme files in this solution assume you are using the docker compose files to create your development kafka environment.

The folder *docker-compose* contains the compose file required.

If you have never used docker before;

- Download docker desktop from https://www.docker.com/products/docker-desktop/
- Install docker desktop

To spin up a docker pod in the development environment;

- Open a terminal at the compose.yaml file you want to create, for example to create a simple single Kafka broker environment;
- Create the docker environment by spinning up docker compose.

```agsl
$ cd Kafka-examples/docker-compose/kafka/zk-single-kafka-single
$ docker compose up
```

The docker compose environment will now be initialised in docker.

