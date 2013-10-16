package apiserver.core.swagger;

import com.mangofactory.swagger.EndpointComparator;
import com.wordnik.swagger.core.DocumentationEndPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * User: mikenimer
 * Date: 10/16/13
 */
@Component
public class NameEndPointComparator implements EndpointComparator
{
    private final Logger log = LoggerFactory.getLogger(NameEndPointComparator.class);

    @Override
    public int compare(DocumentationEndPoint first, DocumentationEndPoint second) {
        return first.getPath().compareTo(second.getPath());
    }
}
