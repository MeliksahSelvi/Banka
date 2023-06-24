package com.meliksah.banka.app.gen.util;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.meliksah.banka.app.gen.dto.JwtToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Author mselvi
 * @Created 24.06.2023
 */

@Service
public class HazelCastCacheUtil {

    @Value("${application-user}")
    private String applicationUser;

    private HazelcastInstance hazelcastInstance;

    private final String CUSTOMER_TOKEN_MAP = "CUSTOMER_TOKEN";

    public HazelCastCacheUtil() {

        Config config = new Config();
        config.setClusterName(applicationUser);
        config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);

        hazelcastInstance = Hazelcast.newHazelcastInstance(config);
    }

    public JwtToken readTokenMap(String token) {
        IMap<String, JwtToken> customerTokenMap = getCustomerTokenMap();

        JwtToken jwtToken = customerTokenMap.get(token);
        return jwtToken;
    }

    public void writeTokenMap(JwtToken jwtToken) {
        IMap<String, JwtToken> tokenMap = getCustomerTokenMap();
        String token = jwtToken.getToken();

        tokenMap.putIfAbsent(token,jwtToken);
    }

    public void deleteFromTokenMap(String token) {
        IMap<String, JwtToken> customerTokenMap = getCustomerTokenMap();

        customerTokenMap.delete(customerTokenMap);
    }

    private IMap<String, JwtToken> getCustomerTokenMap() {
        IMap<String, JwtToken> customerTokenMap = hazelcastInstance.getMap(CUSTOMER_TOKEN_MAP);
        return customerTokenMap;
    }
}
