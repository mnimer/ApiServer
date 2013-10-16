package apiserver.core.swagger;

import com.mangofactory.swagger.EndpointComparator;
import com.mangofactory.swagger.OperationComparator;
import com.mangofactory.swagger.configuration.DocumentationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * User: mikenimer
 * Date: 10/16/13
 */
@Configuration
@Import(DocumentationConfig.class)
public class CustomDocumentationConfig
{
    private final Logger log = LoggerFactory.getLogger(CustomDocumentationConfig.class);

    @Bean
    public EndpointComparator endPointComparator() {
        return new NameEndPointComparator();
    }

    @Bean
    public com.mangofactory.swagger.OperationComparator operationComparator() {
        return new NameOperationComparator();
    }
}
