package com.mprybicki.cameraservice.cameradata.config;

import com.mprybicki.cameraservice.common.model.CameraData;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;


@Configuration
public class CameraDataSenderConfig {

    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServer;

    @Bean
    public ProducerFactory<String, CameraData> producerRFDataFactory() {
        Map<String, Object> configProperties = new HashMap<>();
        configProperties.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        configProperties.put(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProperties.put(VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProperties);
    }

    @Bean
    public KafkaTemplate<String, CameraData> rfDataKafkaTemplate() {
        return new KafkaTemplate<>(producerRFDataFactory());
    }

    @Bean
    public CameraDataSenderConfig rfDataSender() {
        return new CameraDataSenderConfig();
    }
}
