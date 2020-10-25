package com.anmi.c4.demosystem.model

import com.anmi.c4.model.element.Technology

enum class ETechnology(override val label: String, override var version: String = "") : Technology {
    JAVA_11("Java", "11"),
    GRPC("gRPC"),
    THRIFT("Apache Thrift"),
    SPRING_BOOT_2_1("Spring Boot", "2.1"),
    SPRING_BOOT_2_2("Spring Boot", "2.2"),
    SPRING_BATCH("Spring Batch"),
    SPRING_INTEGRATION("Spring Integration"),
    JAX_RS("JAX-RS"),
    RX_JAVA("RxJava"),
    ZAB("Zookeeper Atomic Broadcast"),
    TCP("TCP"),
    JDBC("JDBC"),
    REST("HTTP/S REST"),
    HTTP("HTTP(S)"),
    AWS_KINESIS("HTTP", "2"),
    ELASTIC_SEARCH("Elasticsearch", "6.5.4"),
    POSTGRES("Postgres", "11.4"),
    JAVA_SWING("Java Swing"),
    MS_SQL("MS SQL");

    override fun toString(): String {
        return "'$label '$version"
    }
}