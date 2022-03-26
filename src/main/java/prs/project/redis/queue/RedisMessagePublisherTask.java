package prs.project.redis.queue;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import prs.project.task.Akcja;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

@Slf4j
@NoArgsConstructor
public class RedisMessagePublisherTask implements MessagePublisher {

    private RedisTemplate<Long, Akcja> redisTemplate;
    private ChannelTopic topicTask;

    public RedisMessagePublisherTask(final RedisTemplate<Long, Akcja> redisTemplate,
            @Qualifier("topicTask") final ChannelTopic topic) {
        this.redisTemplate = redisTemplate;
        this.topicTask = topic;
    }

    @Override public void publish(Akcja message) {
        JsonMapper jsonMapper = new JsonMapper();
        String mess = null;
        try {
            mess = jsonMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        redisTemplate.convertAndSend(topicTask.getTopic(), mess);
        log.info("Message send: " + message.toString());
    }
}
