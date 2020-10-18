package com.anmi.c4.config

enum class ConfigInstance(base: Config) : Config by base {
    TEST(ConfigCreator.fromFile("test-config.properties"))
}

