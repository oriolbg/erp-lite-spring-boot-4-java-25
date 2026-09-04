package erplite.domain.ports.messages;

import erplite.domain.common.DomainEvent;

public interface EventPublisherPort {

    void publish(DomainEvent event);
}
