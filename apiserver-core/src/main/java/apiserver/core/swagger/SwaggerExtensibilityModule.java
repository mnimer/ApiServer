package apiserver.core.swagger;

import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mangofactory.swagger.configuration.ExtensibilityModule;
import com.mangofactory.swagger.models.AlternateTypeProcessingRule;
import com.mangofactory.swagger.models.TypeProcessingRule;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.WebAsyncTask;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import static com.mangofactory.swagger.models.AlternateTypeProcessingRule.alternate;
import static com.mangofactory.swagger.models.IgnorableTypeRule.ignorable;


/**
 * User: mikenimer
 * Date: 10/16/13
 */
public class SwaggerExtensibilityModule extends ExtensibilityModule
{
    private final Logger log = LoggerFactory.getLogger(SwaggerExtensibilityModule.class);

    private final ObjectMapper documentationObjectMapper;

    @Autowired
    public SwaggerExtensibilityModule(ObjectMapper documentationObjectMapper) {
        this.documentationObjectMapper = documentationObjectMapper;
        this.documentationObjectMapper.addMixInAnnotations(HttpHeaders.class, HttpHeadersMixin.class);
    }

    @Override
    protected void customizeTypeProcessingRules(List<TypeProcessingRule> rules) {
        rules.add(ignorable(UriComponentsBuilder.class));
        rules.add(new AlternateTypeProcessingRule(ModelAndView.class, Map.class));
        rules.add(new AlternateTypeProcessingRule(BigDecimal.class, Double.class));
        rules.add(new AlternateTypeProcessingRule(LocalDate.class, Date.class));
        rules.add(responseEntityMapAlternate());
        rules.add(responseEntityBytesAlternate());
        rules.add(webAsyncTaskMapAlternate());
    }

    private class HttpHeadersMixin {
        @JsonIgnore
        public void setIfNoneMatch(List<String> ifNoneMatchList) {
        }

        @JsonIgnore
        public void setLocation(URI uri) {
        }
    }


    /**
     * Handles ResponseEntity<Map> results
     * @return
     */
    public AlternateTypeProcessingRule responseEntityMapAlternate() {
        TypeResolver resolver = new TypeResolver();
        return alternate(resolver.resolve(ResponseEntity.class, Map.class), resolver.resolve(Map.class));
    }


    /**
     * Handles ResponseEntity<byte[]> results
     * @return
     */
    public AlternateTypeProcessingRule responseEntityBytesAlternate() {
        TypeResolver resolver = new TypeResolver();
        return alternate(resolver.resolve(ResponseEntity.class, byte[].class), resolver.resolve(byte[].class));
    }

    /**
     * Handles WebAsyncTask<Map> results
     * @return
     */
    public AlternateTypeProcessingRule webAsyncTaskMapAlternate() {
        TypeResolver resolver = new TypeResolver();
        return alternate(resolver.resolve(WebAsyncTask.class, Map.class), resolver.resolve(Map.class));
    }

    /**
     * Handles Callable<Map> results
     * @return
     */
    public AlternateTypeProcessingRule callableMapAlternate() {
        TypeResolver resolver = new TypeResolver();
        return alternate(resolver.resolve(Callable.class, Map.class), resolver.resolve(Map.class));
    }
}
