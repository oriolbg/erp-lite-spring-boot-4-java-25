package erplite.domain.ports;

import java.util.Optional;

import erplite.domain.customer.CustomerInfo;

/**
 *  Port for external service for JSONPlaceholder
 */
public interface CustomerProviderService {

    Optional<CustomerInfo> findById(Long id);
    boolean existsById(Long id);
}
