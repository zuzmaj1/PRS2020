package com.baeldung.spring.data.redis.queue;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class RedisMessageSubscriberAck implements MessageListener {

    Map<Long, Long> ack;

    public RedisMessageSubscriberAck() {
        this.ack =  new HashMap<>();
    }

    @Override public void onMessage(Message message, byte[] bytes) {
        Long key = Long.valueOf(message.toString());
        if (ack.containsKey(key)) {
            Long value = ack.get(key);
            ack.put(key, value + 1L);
        } else {
            ack.put(key, 1L);
        }
    }

    public void waitOnStart(Long key) throws InterruptedException {
        // change to lock / notify scenario - non active waiting
        while(!ack.containsKey(key) || ack.get(key)!=1L) {
            Thread.sleep(10);
        }
    }

    public void waitOnEnd(Long key) throws InterruptedException {
        // change to lock / notify scenario - non active waiting
        while(!ack.containsKey(key) || ack.get(key)!=2L) {
            Thread.sleep(10);
        }
    }

    public void waitForAll(long size) throws InterruptedException {
        // change to lock / notify scenario - non active waiting
        while(ack.size() < size || ack.values().stream().allMatch(value -> value == 2) == false){
            Thread.sleep(1000);
        }
    }
}
