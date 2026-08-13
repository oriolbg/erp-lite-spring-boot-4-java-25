package erplite.erpapi.configs;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import erplite.erpinfrastructure.persistence.aws.models.AwsConfigModel;

@Configuration
@EnableConfigurationProperties(AwsConfigModel.class)
@PropertySource(value = "classpath:aws/aws.yml", factory = YamlPropertySourceFactory.class)//Le pasamos la ruta interna del proyecto
public class YmlConfig {
}
