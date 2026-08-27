package erplite.erpapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableJpaRepositories(basePackages ="erplite.erpinfrastructure.persistence.jpa.repositories")
//@EnableMongoRepositories(basePackages ="erplite.erpinfrastructure.persistence.mongo.repositories")
//@EntityScan(basePackages = "erplite.erpinfrastructure.persistence.jpa.entities")
@SpringBootApplication(scanBasePackages = "erplite")
public class ErpLiteApplication {
	
//	@Autowired
//	private CatalogRepository catalogRepository;
//	
//	@Autowired
//	private ProductRepository productRepository;

	public static void main(String[] args) {
		SpringApplication.run(ErpLiteApplication.class, args);
	}

	/**
	 * Para visualizar los datos almacenados y validar la conexion con la base de datos
	 */
//	@Override
//	public void run(String... args) throws Exception {
//		this.catalogRepository.findAll().stream()
//			.map(CatalogDocument::getName)
//			.forEach(System.out::println);
//		
//		this.productRepository.findAll().stream()
//		.map(ProductEntity::getName)
//		.forEach(System.out::println);
//	}

}
