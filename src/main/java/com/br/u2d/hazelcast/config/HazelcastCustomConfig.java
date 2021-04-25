package com.br.u2d.hazelcast.config;

import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastCustomConfig {

    private static final String CLIENT = "client";

    @Bean
    public HazelcastInstance hazelcastConfig() {
        return Hazelcast.newHazelcastInstance(createConfig());
    }

    @Bean
    public Config createConfig() {
        Config config = new Config();
        config.setNetworkConfig(networkConfig());
        config.addMapConfig(mapConfig());
        return config;
    }

    @Bean
    public NetworkConfig networkConfig() {
        NetworkConfig networkConfig = new NetworkConfig();
        networkConfig.setPort(8082);
        networkConfig.setPortCount(1);
        networkConfig.setPortAutoIncrement(false);
        networkConfig.setJoin(joinConfig());
        return networkConfig;
    }

    @Bean
    public JoinConfig joinConfig() {
        JoinConfig joinConfig = new JoinConfig();
        joinConfig.getMulticastConfig().setEnabled(false);
        joinConfig.getTcpIpConfig()
                .addMember("localhost").setEnabled(true);
        return joinConfig;
    }

    @Bean
    public MapConfig mapConfig() {
        MapConfig mapConfig = new MapConfig(CLIENT);
        mapConfig.setTimeToLiveSeconds(60);
        mapConfig.setMaxIdleSeconds(20);
        return mapConfig;
    }
}
