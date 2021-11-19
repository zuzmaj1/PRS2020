package com.baeldung.spring.data.redis.config;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

import com.baeldung.spring.data.redis.queue.MessagePublisher;
import com.baeldung.spring.data.redis.queue.RedisMessagePublisherAck;
import com.baeldung.spring.data.redis.queue.RedisMessagePublisherTask;
import com.baeldung.spring.data.redis.queue.RedisMessageSubscriberAck;
import com.baeldung.spring.data.redis.queue.RedisMessageSubscriberTask;

@Configuration
@ComponentScan("com.baeldung.spring.data.redis")
@EnableRedisRepositories(basePackages = "com.baeldung.spring.data.redis.repo")
@PropertySource("classpath:application.properties")
public class RedisConfig {

    @Bean
    ConcurrentHashMap<Long, Long> state() {
        return new ConcurrentHashMap<Long, Long>();
    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<Long, Object> redisTemplate() {
        final RedisTemplate<Long, Object> template = new RedisTemplate<Long, Object>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
        return template;
    }

    @Bean(name = "listenerTask")
    MessageListenerAdapter messageListenerTask() {
        return new MessageListenerAdapter(new RedisMessageSubscriberTask(state(), redisPublisherAck()));
    }

    @Bean(name = "listenerAck")
    MessageListenerAdapter messageListenerAck() {
        return new MessageListenerAdapter(new RedisMessageSubscriberAck());
    }

    @Bean(name = "containerTask")
    RedisMessageListenerContainer redisContainerTask() {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory());
        container.addMessageListener(messageListenerTask(), topicTask());
        return container;
    }

    @Bean(name = "containerAck")
    RedisMessageListenerContainer redisContainerAck() {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory());
        container.addMessageListener(messageListenerAck(), topicAck());
        return container;
    }

    @Bean(name = "publisherTask")
    RedisMessagePublisherTask redisPublisherTask() {
        return new RedisMessagePublisherTask(redisTemplate(), topicTask());
    }

    @Bean(name = "publisherAck")
    RedisMessagePublisherAck redisPublisherAck() {
        return new RedisMessagePublisherAck(redisTemplate(), topicAck());
    }

    @Bean(name = "topicTask")
    ChannelTopic topicTask() {
        return new ChannelTopic("pubsub:task");
    }

    @Bean(name = "topicAck")
    ChannelTopic topicAck() {
        return new ChannelTopic("pubsub:ack");
    }
}
