package erplite.erpapi.configs;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import erplite.erpinfrastructure.persistence.aws.models.AwsConfigModel;
import erplite.erpinfrastructure.persistence.rest.models.JsonplaceholderConfigModel;

@Configuration
@EnableConfigurationProperties({
	AwsConfigModel.class, JsonplaceholderConfigModel.class
})
@PropertySource(value = "classpath:aws/aws.yml", factory = YamlPropertySourceFactory.class) //Le pasamos la ruta interna del proyecto
@PropertySource(value = "classpath:jsonplaceholder/jsonplaceholder.yml", factory = YamlPropertySourceFactory.class)
public class YmlConfig {
}
