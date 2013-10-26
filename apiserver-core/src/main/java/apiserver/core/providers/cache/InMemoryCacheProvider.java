package apiserver.core.providers.cache;

import apiserver.apis.v1_0.documents.model.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;

/**
 * User: mikenimer
 * Date: 10/25/13
 */
public class InMemoryCacheProvider implements IDocumentCacheProvider
{
    private final Logger log = LoggerFactory.getLogger(InMemoryCacheProvider.class);

    // todo: replace with a better cache collection type
    private static Map<String, Object> cache = Collections.synchronizedMap(new LinkedHashMap<String, Object>());


    public InMemoryCacheProvider()
    {
        loadTestData();
    }


    private void loadTestData()
    {
        try
        {
            String imgID = "8D981024-A297-4169-8603-E503CC38EEDA";
            File file = new File(  InMemoryCacheProvider.class.getClassLoader().getResource("sample.png").toURI()  );
            Document doc = new Document(file);
            doc.setId(imgID);
            cache.put(imgID, doc);
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }


    @Override
    public Document add(Document doc)
    {
        if( doc.getId() == null )
        {
            doc.setId(UUID.randomUUID().toString());
        }
        cache.put(doc.getId(), doc);
        return doc;
    }


    @Override
    public Document get(String key)
    {
        return (Document)cache.get(key);
    }


    @Override
    public Document delete(String key)
    {
        return (Document)cache.remove(key);
    }
}
