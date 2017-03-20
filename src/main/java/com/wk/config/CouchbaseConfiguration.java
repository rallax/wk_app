package com.wk.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;

import java.util.Arrays;
import java.util.List;

/**
 * Created by mikhail.pasenko on 20.03.2017.
 */
@Configuration
@EnableCouchbaseRepositories(basePackages = "com.wk.app.couchbase.repository")
@PropertySource("classpath:config/application.properties")
public class CouchbaseConfiguration extends AbstractCouchbaseConfiguration {

    @Value("${couchbase.host}")
    private String host;
    @Value("${couchbase.bucket.name}")
    private String bucketName;
    @Value("${couchbase.bucket.password}")
    private String password;


    @Override
    protected List<String> getBootstrapHosts() {
        return Arrays.asList(host);
    }

    @Override
    protected String getBucketName() {
        return bucketName;
    }

    @Override
    protected String getBucketPassword() {
        return password;
    }
}
