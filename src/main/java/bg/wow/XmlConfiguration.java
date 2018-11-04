package bg.wow;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({"classpath:spring.xml", "classpath:database.xml"})
public class XmlConfiguration {

}
