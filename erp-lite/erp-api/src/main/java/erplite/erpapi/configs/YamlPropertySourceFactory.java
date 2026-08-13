package erplite.erpapi.configs;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

/**
 * Clase para castear un archivo .yaml y convertirlo a tipo Properties
 */
public class YamlPropertySourceFactory implements PropertySourceFactory{

	@Override
	public PropertySource<?> createPropertySource(@Nullable String name, EncodedResource resource) throws IOException {
		var factory = new YamlPropertiesFactoryBean();
        factory.setResources(resource.getResource());

        Properties properties = factory.getObject();
        
        return new PropertiesPropertySource(
            Objects.requireNonNull(resource.getResource().getFilename()), 
            properties
        );
	}

}
