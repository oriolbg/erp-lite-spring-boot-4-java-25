package erplite.erpinfrastructure.persistence.rest.configs;



import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestClient;

import erplite.erpinfrastructure.persistence.rest.models.JsonplaceholderConfigModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class RestClientConfig {

	private final JsonplaceholderConfigModel jsonConfig;

	@Bean(name = "jsonplaceholder")
	@ConditionalOnProperty(
		prefix = "jsonplaceholder.api",
		name = "enabled",
		havingValue = "true",
		matchIfMissing = true
	)
	public RestClient restClient() {
		return RestClient.builder()
			.baseUrl(jsonConfig.baseUrl())
			.requestInterceptors(interceptors -> {
				interceptors.add(loggingInterceptor());
				interceptors.add(errorLoggingInterceptor());
			})
			.defaultHeaders(headers -> {
				headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
				headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
			})
			.build();
	}
	
	private ClientHttpRequestInterceptor loggingInterceptor() {
		return (HttpRequest req, byte[] body, ClientHttpRequestExecution exe) -> {
			log.info("Calling Jsonplaceholder API");
			log.info("Method: {}", req.getMethod());
			log.info("URI: {}", req.getURI());
			log.info("Headers: {}", req.getHeaders());
			log.info("Attributes: {}", req.getAttributes());
			
			final long startTime = System.currentTimeMillis();
			final long requiredTime;
			try {
				final var res = exe.execute(req, body);
				requiredTime = System.currentTimeMillis() - startTime;
				log.info("Status: {}", res.getStatusCode() + " - " + res.getStatusText());
				log.info("Execution Time: {} ms", requiredTime);
				return res;
			}catch (Exception e) {
				log.error("Error calling JsonPlaceholder API", e);
				throw e;
			}
		};
	}
	private ClientHttpRequestInterceptor errorLoggingInterceptor() {
		return (HttpRequest req, byte[] body, ClientHttpRequestExecution exe) -> {
			try {
				return exe.execute(req, body);
			}catch(Exception e) {
				log.error("Error message: {}", e.getMessage());
				throw e;
			}
		};
	}
}
