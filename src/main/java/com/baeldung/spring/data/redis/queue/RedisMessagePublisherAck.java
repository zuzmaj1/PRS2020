package com.baeldung.spring.data.redis.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
public class RedisMessagePublisherAck implements MessagePublisher {

    private RedisTemplate<Long, Object> redisTemplate;
    private ChannelTopic topicAck;

    public RedisMessagePublisherAck(final RedisTemplate<Long, Object> redisTemplate,  @Qualifier("topicAck") final ChannelTopic topic) {
        this.redisTemplate = redisTemplate;
        this.topicAck = topic;
    }


    @Override public void publish(Long message) {
        redisTemplate.convertAndSend(topicAck.getTopic(), message);
    }
}
