package erplite.erpapi.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ApiVersionConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApiVersionConfig  implements WebMvcConfigurer {

    @Override
    public void configureApiVersioning(ApiVersionConfigurer configurer){
        //Configuracion en el Header por toda la API
        configurer.useRequestHeader("X-Api-Version");

        //Configuracion por Path de toda la API
        //configurer.usePathSegment(1);//api/v1/product
    }
}
