package com.baeldung.spring.data.redis.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
public class RedisMessagePublisherTask implements MessagePublisher {

    private RedisTemplate<Long, Object> redisTemplate;
    private ChannelTopic topicTask;

    public RedisMessagePublisherTask(final RedisTemplate<Long, Object> redisTemplate, @Qualifier("topicTask") final ChannelTopic topic) {
        this.redisTemplate = redisTemplate;
        this.topicTask = topic;
    }

    @Override public void publish(Long message) {
        redisTemplate.convertAndSend(topicTask.getTopic(), message);
    }
}
