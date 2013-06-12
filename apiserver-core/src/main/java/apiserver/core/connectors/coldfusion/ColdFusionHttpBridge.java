package apiserver.core.connectors.coldfusion;

import apiserver.exceptions.ColdFusionException;
import apiserver.exceptions.MessageConfigException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.tika.io.IOUtils;
import org.codehaus.jackson.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.util.JSONPObject;
import org.codehaus.jackson.type.TypeReference;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * User: mikenimer
 * Date: 3/24/13
 */
public class ColdFusionHttpBridge implements IColdFusionBridge
{
    HashMap cfcPathCache = new HashMap();

    private String ip;
    private int port;
    private String contextRoot;


    public void setIp(String ip)
    {
        this.ip = ip;
    }


    public void setPort(int port)
    {
        this.port = port;
    }


    public void setContextRoot(String contextRoot)
    {
        this.contextRoot = contextRoot;
    }


    public Object invoke(String cfcPath_, String method_, Object[] methodArgs_, HttpServletRequest request_) throws Throwable
    {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        try {
            HttpHost host = new HttpHost(ip, port, "http");
            HttpPost method = new HttpPost(contextRoot);

            MultipartEntity me = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            me.addPart("cfcPath", new StringBody(cfcPath_, "text/plain", Charset.forName( "UTF-8" )));
            me.addPart("cfcMethod", new StringBody(method_, "text/plain", Charset.forName("UTF-8")));
            method.setEntity(me);

            //ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
            //postParameters.add(new BasicNameValuePair("cfcPath", cfcPath_));
            //postParameters.add(new BasicNameValuePair("cfcMethod", method_));
            //method.setEntity(new UrlEncodedFormEntity(postParameters));


            //ResponseHandler responseHandler = new BasicResponseHandler();
            //Object debug = httpClient.execute(host, method, responseHandler);
            HttpResponse response = httpClient.execute(host, method);//, responseHandler);

            // Examine the response status
            if( response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                // Get hold of the response entity
                HttpEntity entity = response.getEntity();

                if (entity != null) {
                    InputStream inputStream = entity.getContent();
                    return  deSerializeJson(inputStream);
                }
            }
            throw new ColdFusionException("Invalid Execution");
        }
        catch (Exception ex)
        {
            throw new ColdFusionException("Invalid Result");
        }
        finally
        {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpClient.getConnectionManager().shutdown();
        }
    }

    protected Object deSerializeJson(InputStream result) throws IOException, JsonParseException
    {
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> map = mapper.readValue(result, new TypeReference<Map<String,Object>>() { });

        return map;
    }
}
