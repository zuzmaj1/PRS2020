package prs.project.redis.queue;

import prs.project.task.Akcja;
public interface MessagePublisher {

    void publish(final Akcja message);
}
