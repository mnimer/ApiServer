package apiserver.core.swagger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mangofactory.swagger.OperationComparator;
import com.wordnik.swagger.core.DocumentationOperation;
import org.springframework.stereotype.Component;


/**
 * User: mikenimer
 * Date: 10/16/13
 */
public class NameOperationComparator implements OperationComparator
{
    private final Logger log = LoggerFactory.getLogger(NameOperationComparator.class);

    @Override
    public int compare(DocumentationOperation first, DocumentationOperation second) {
        return first.getNickname().compareTo(second.getNickname());
    }
}
