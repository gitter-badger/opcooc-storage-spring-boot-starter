/*
 * Copyright © 2020-2029 organization opcooc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.opcooc.storage.spring.boot.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

import com.opcooc.storage.StorageClient;
import com.opcooc.storage.aop.DynamicClientAnnotationAdvisor;
import com.opcooc.storage.aop.DynamicClientAnnotationInterceptor;
import com.opcooc.storage.drivers.ClientDriver;
import com.opcooc.storage.drivers.DynamicRoutingClientDriver;
import com.opcooc.storage.holder.ClientDriverHolder;
import com.opcooc.storage.processor.OsHeaderProcessor;
import com.opcooc.storage.processor.OsProcessor;
import com.opcooc.storage.processor.OsSessionProcessor;
import com.opcooc.storage.processor.OsSpelExpressionProcessor;
import com.opcooc.storage.provider.YmlClientDriverProvider;
import com.opcooc.storage.support.BucketConverter;
import com.opcooc.storage.support.ClientDriverHealthIndicator;
import com.opcooc.storage.support.ObjectConverter;

import lombok.extern.slf4j.Slf4j;

/**
 * 自动IOC注入类
 *
 * @author shenqicheng
 * @since 1.2.0
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(DynamicStorageProperties.class)
@ConditionalOnProperty(prefix = DynamicStorageProperties.PREFIX, name = DynamicStorageProperties.ENABLED, havingValue = "true", matchIfMissing = true)
public class DynamicStorageAutoConfiguration {

    private final DynamicStorageProperties properties;

    @SuppressWarnings({"SpringJavaAutowiredFieldsWarningInspection"})
    @Autowired(required = false)
    private BucketConverter bucketConverter;

    @SuppressWarnings({"SpringJavaAutowiredFieldsWarningInspection"})
    @Autowired(required = false)
    private ObjectConverter objectConverter;

    public DynamicStorageAutoConfiguration(DynamicStorageProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    public YmlClientDriverProvider ymlClientDriverProvider() {
        return new YmlClientDriverProvider(properties.getDriver());
    }

    @Bean
    @ConditionalOnMissingBean
    public ClientDriverHolder clientDriverHolder() {
        ClientDriverHolder clientDriverHolder = new ClientDriverHolder();
        clientDriverHolder.setProperties(properties);
        return clientDriverHolder;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = DynamicStorageProperties.PREFIX, name = DynamicStorageProperties.HEALTH, havingValue = "true")
    public ClientDriverHealthIndicator clientDriverHealthIndicator(ClientDriver clientDriver) {
        return new ClientDriverHealthIndicator(clientDriver);
    }

    @Bean
    @ConditionalOnMissingBean
    public ClientDriver clientDriver() {
        DynamicRoutingClientDriver dataSource = new DynamicRoutingClientDriver();
        dataSource.setPrimary(properties.getPrimary());
        dataSource.setStrict(properties.getStrict());
        return dataSource;
    }

    @Bean
    public StorageClient storageClient(ClientDriver clientDriver) {
        StorageClient client = new StorageClient(clientDriver);
        if (bucketConverter != null) {
            client.setBucketConverter(bucketConverter);
        }
        if (objectConverter != null) {
            client.setObjectConverter(objectConverter);
        }
        return new StorageClient(clientDriver);
    }

    @Bean
    @ConditionalOnMissingBean
    public OsProcessor dsProcessor() {
        OsHeaderProcessor headerProcessor = new OsHeaderProcessor();
        OsSessionProcessor sessionProcessor = new OsSessionProcessor();
        OsSpelExpressionProcessor spelExpressionProcessor = new OsSpelExpressionProcessor();
        headerProcessor.setNextProcessor(sessionProcessor);
        sessionProcessor.setNextProcessor(spelExpressionProcessor);
        return headerProcessor;
    }

    @Role(value = BeanDefinition.ROLE_INFRASTRUCTURE)
    @Bean
    @ConditionalOnMissingBean
    public DynamicClientAnnotationAdvisor dynamicStorageAnnotationAdvisor(OsProcessor osProcessor) {
        DynamicClientAnnotationInterceptor interceptor = new DynamicClientAnnotationInterceptor(properties.isAllowedPublicOnly(), osProcessor);
        DynamicClientAnnotationAdvisor advisor = new DynamicClientAnnotationAdvisor(interceptor);
        advisor.setOrder(properties.getOrder());
        return advisor;
    }

}
