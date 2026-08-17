package erplite.erpinfrastructure.persistence.rest.adapters;

import java.util.Optional;

import erplite.domain.entities.customer.CustomerInfo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import erplite.domain.ports.services.CustomerProviderServicePort;
import erplite.erpinfrastructure.persistence.rest.dtos.UserDTO;
import erplite.erpinfrastructure.persistence.rest.mappers.CustomerMapper;
import erplite.erpinfrastructure.persistence.rest.models.JsonplaceholderConfigModel;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JsonPlaceholderCustomerProviderAdapter implements CustomerProviderServicePort {

    private final RestClient jsonClient;
    private final CustomerMapper customerMapper;
    private final String endpoint;

    public JsonPlaceholderCustomerProviderAdapter(
            @Qualifier("jsonplaceholder") RestClient restClient,
            CustomerMapper customerMapper,
            JsonplaceholderConfigModel jsonConfig) {

        this.jsonClient = restClient;
        this.customerMapper = customerMapper;
        this.endpoint = jsonConfig.usersEndpoint();
    }

    @Override
    public Optional<CustomerInfo> findById(Long id) {
        log.info("findById: {}", id);

        try {
            final UserDTO response = this.jsonClient
                    .get()
                    .uri(endpoint, id)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                        log.error("Error on client side: {}", req);
                    })
                    .onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                        log.error("Error on server side: {}", req);
                    })
                    .body(UserDTO.class);

            if (response == null) {
                log.warn("No user found");
                return Optional.empty();
            }

            log.info("User found: {}", response);

            return Optional.of(this.customerMapper.toCustomerInfo(response));

        } catch (RestClientException rce) {
            log.error("Error on findById while call API", rce);
            return Optional.empty();
        } catch (Exception e) {
            log.error("Error on findById ", e);
            return Optional.empty();
        }
    }

    @Override
    public boolean existsById(Long id) {
        log.info("existsById: {}", id);
        return false;
    }
}
