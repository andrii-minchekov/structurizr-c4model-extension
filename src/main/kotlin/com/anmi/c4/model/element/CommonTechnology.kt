package com.anmi.c4.model.element

enum class CommonTechnology(override val label: String, override var version: String = "") : Technology {
    JAVA_11("Java", "11"),
    JAVA_17("Java", "17"),
    GRPC("gRPC"),
    THRIFT("Apache Thrift"),
    SPRING_BOOT_2_1("Spring Boot", "2.1"),
    SPRING_BOOT_2_2("Spring Boot", "2.2"),
    SPRING_BOOT_2_7("Spring Boot", "2.7.x"),
    SPRING_BATCH("Spring Batch"),
    SPRING_INTEGRATION("Spring Integration"),
    JAX_RS("JAX-RS"),
    RX_JAVA("RxJava"),
    NODE_JS("Node.js", "12"),
    ZAB("Zookeeper Atomic Broadcast"),
    TCP("TCP"),
    JDBC("JDBC"),
    REST("HTTP/S REST"),
    HTTP("HTTP(S)"),
    WEB_SOCKET("WebSocket"),
    R_SOCKET("RSocket"),
    IBM_MQ("IBM MQ"),
    AWS_S3("AWS S3", "Amazon Simple Storage Service"),
    AWS_SQS("AWS SQS", "Amazon Simple Queue Service"),
    AWS_RDS_POSTGRES("AWS RDS for PostgreSQL", "14"),
    AWS_KINESIS("AWS Kinesis", "2"),
    KAFKA("Kafka", "2"),
    ELASTIC_SEARCH("Elasticsearch", "6.5.4"),
    POSTGRES("PostgreSQL", "10"),
    POSTGRES_14("PostgreSQL", "14"),
    FLUTTER("Flutter SDK"),
    GO("Golang", "1.15.2"),
    PYTHON_3_10("Python", "3.10.x"),
    DJANGO_4("Django web framework", "4.0.x"),
    TYPE_SCRIPT_4("TypeScript", "4.4.x"),
    REACTJS("ReactJS", ""),
    REACT_NATIVE("React Native", ""),
    ANGULAR("Angular JS", "10.0.3");

    override fun toString(): String {
        return "'$label '$version"
    }
}