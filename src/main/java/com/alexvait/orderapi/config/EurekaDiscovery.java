package com.alexvait.orderapi.config;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("cloud")
@EnableDiscoveryClient
@Configuration
public class EurekaDiscovery {
}
