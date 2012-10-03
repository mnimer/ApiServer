package integrationTests.v1_0;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.MapFactoryBean;
import org.springframework.test.context.ContextConfiguration;

import java.io.File;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;

/**
 * User: mnimer
 * Date: 9/26/12
 */
@ContextConfiguration(locations = {"file:**/config/v1_0/apis-servlet.xml"})
public abstract class HttpTest
{
    @Autowired
    public MapFactoryBean unitTestProperties;

    public HttpClient client;


    private String getUrlBase() throws Exception
    {
        String host = unitTestProperties.getObject().get("tomcatHost").toString();
        String port = unitTestProperties.getObject().get("tomcatPort").toString();
        String contextRoot = unitTestProperties.getObject().get("tomcatContextRoot").toString();

        return "http://" + host + ":" + port + contextRoot;
    }

    @After
    public void cleanUp()
    {
        if( client != null )
        {
            client.getConnectionManager().closeExpiredConnections();
        }
    }


    public HttpResponse invokeHttpGet(String urlPath, File file, Map formArgs) throws Exception
    {
        String url = getUrlBase() + urlPath;

        client = new DefaultHttpClient();
        //client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);


        URIBuilder builder = new URIBuilder(url);

        if( formArgs != null )
        {
            Iterator itr = formArgs.keySet().iterator();
            while( itr.hasNext() )
            {
                Object key = itr.next();
                builder.setParameter(key.toString(), formArgs.get(key).toString() );
            }
        }
        URI uri = builder.build();
        HttpGet post = new HttpGet(uri);


        int status = 0;
        HttpResponse response = null;
        try
        {
            // Here we go!
            response = client.execute(post);
            return response;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw ex;
        }
    }


    public HttpResponse invokeHttpPost(String urlPath, File file, Map formArgs) throws Exception
    {
        String url = getUrlBase() + urlPath;// "/v1-0/image/info/size.json";

        client = new DefaultHttpClient();
        client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

        HttpPost post = new HttpPost(url);
        MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        post.setEntity(entity);

        // For File parameters
        if( file != null )
        {
            String extension = file.getName().split("\\.")[1];
            entity.addPart("file", new FileBody(file, "image/" +extension));
        }

        // For usual String form parameters
        if( formArgs != null )
        {
            Iterator itr = formArgs.keySet().iterator();
            while( itr.hasNext() )
            {
                Object key = itr.next();
                entity.addPart( key.toString(), new StringBody( formArgs.get(key).toString(), "text/plain", Charset.forName("UTF-8")));
            }
        }

        int status = 0;
        HttpResponse response = null;
        try
        {
            // Here we go!
            response = client.execute(post);
            return response;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw ex;
        }
    }
}
