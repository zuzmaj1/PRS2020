package prs.project.redis.config;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import prs.project.ParallelExecutor;
import prs.project.controllers.Settings;
import prs.project.redis.queue.RedisMessageSubscriberTask;
import prs.project.task.Akcja;

@Configuration
@ComponentScan("prs.project")
@PropertySource("classpath:application.yml")
public class RedisConfig {

    @Autowired ParallelExecutor parallelExecutor;
    @Autowired Settings settings;

    @Bean
    @ConditionalOnProperty(
            value="ustawienia.activeRedis",
            havingValue = "true",
            matchIfMissing = false)
    JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(settings.getRedisHost(), settings.getRedisPort());
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    @ConditionalOnProperty(
            value="ustawienia.activeRedis",
            havingValue = "true",
            matchIfMissing = false)
    public RedisTemplate<Long, Akcja> redisTemplate() {
        final RedisTemplate<Long, Akcja> template = new RedisTemplate<Long, Akcja>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setValueSerializer(new GenericToStringSerializer<Akcja>(Akcja.class));
        return template;
    }

    @Bean(name = "listenerTask")
    @ConditionalOnProperty(
            value="ustawienia.activeRedis",
            havingValue = "true",
            matchIfMissing = false)
    MessageListenerAdapter messageListenerTask() {
        return new MessageListenerAdapter(new RedisMessageSubscriberTask(parallelExecutor));
    }

    @Bean(name = "containerTask")
    @ConditionalOnProperty(
            value="ustawienia.activeRedis",
            havingValue = "true",
            matchIfMissing = false)
    RedisMessageListenerContainer redisContainerTask() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory());
        container.setTaskExecutor(executorService);
        container.addMessageListener(messageListenerTask(), topicTask());
        return container;
    }

    @Bean(name = "topicTask")
    @ConditionalOnProperty(
            value="ustawienia.activeRedis",
            havingValue = "true",
            matchIfMissing = false)
    ChannelTopic topicTask() {
        return new ChannelTopic("pubsub:task");
    }

}
