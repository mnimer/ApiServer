package apiserver.core.connectors.coldfusion;

/*******************************************************************************
 Copyright (c) 2013 Mike Nimer.

 This file is part of ApiServer Project.

 The ApiServer Project is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 The ApiServer Project is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with the ApiServer Project.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

import apiserver.ApiServerConstants;
import apiserver.MimeType;
import apiserver.core.model.IDocument;
import apiserver.core.model.IDocumentJob;
import apiserver.exceptions.ColdFusionException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.xerces.impl.dv.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * User: mikenimer
 * Date: 3/24/13
 */
@Component
public class ColdFusionHttpBridge implements IColdFusionBridge
{

    HashMap cfcPathCache = new HashMap();

    @Value("${CFHost}")
    private String ip;
    @Value("${CFPort}")
    private int port;
    @Value("${CFContextRoot}")
    private String contextRoot;
    @Value("${CFPath}")
    private String cfcPath;


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


    public void setCfcPath(String cfcPath)
    {
        this.cfcPath = cfcPath;
    }


    public Object invoke(String cfcPath_, String method_, Map<String,Object> methodArgs_) throws Throwable
    {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        try {
            HttpHost host = new HttpHost(ip, port, "http");
            HttpPost method = new HttpPost(validatePath(contextRoot +cfcPath) +cfcPath_);

            MultipartEntity me = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

            if( methodArgs_ != null )
            {
                for (String s : methodArgs_.keySet())
                {
                    Object obj = methodArgs_.get(s);

                    if( obj != null )
                    {
                        if (obj instanceof String)
                        {
                            me.addPart(s, new StringBody((String) obj, "text/plain", Charset.forName("UTF-8")));
                        }
                        else if (obj instanceof Integer)
                        {
                            me.addPart(s, new StringBody(((Integer) obj).toString(), "text/plain", Charset.forName("UTF-8")));
                        }
                        else if (obj instanceof File)
                        {
                            me.addPart(s, new FileBody((File) obj));
                        }
                        else if (obj instanceof IDocument)
                        {
                            me.addPart(s, new FileBody(((IDocument) obj).getFile()));// new StringBody( Base64.encode( ((IDocument) obj).getFileBytes() )));
                            me.addPart("name",  new StringBody( ((IDocument) obj).getFileName() ) );
                            me.addPart("contentType",  new StringBody( ((IDocument) obj).getContentType().contentType ) );
                            //me.addPart(s, new FileBody(((Document) obj).getFile()));
                            //me.addPart(s, new ByteArrayBody( ((Document) obj).getFileBytes(), ((Document) obj).getContentType().contentType, ((Document) obj).getFileName() ));
                        }
                        else if (obj instanceof BufferedImage)
                        {
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            ImageIO.write((BufferedImage)obj, "jpg", baos);

                            me.addPart(s, new ByteArrayBody( baos.toByteArray(), ((MimeType)methodArgs_.get(ApiServerConstants.CONTENT_TYPE)).getExtension(),  (String)methodArgs_.get(ApiServerConstants.FILE_NAME) ));
                        }
                        else if (obj instanceof byte[])
                        {
                            me.addPart(s, new ByteArrayBody((byte[]) obj, (String) methodArgs_.get("name")));
                        }
                        else if (obj instanceof Map)
                        {
                            ObjectMapper mapper = new ObjectMapper();
                            String _json = mapper.writeValueAsString(obj);

                            me.addPart(s, new StringBody(_json) );
                        }
                    }
                }
            }

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
                    //return inputStream;
                    return IOUtils.toByteArray(inputStream);
                    //Map json = (Map)deSerializeJson(inputStream);
                    //return json;
                }
            }
            throw new ColdFusionException(response.getStatusLine().toString());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
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


    /**
     * make sure the path starts and ends with /'s
     * @param path
     */
    protected String validatePath(String path)
    {
        String _path = path.trim();
        if( !_path.startsWith("/") )
        {
            _path = "/" +path;
        }

        if( !_path.endsWith("/") )
        {
            _path = _path +"/";
        }

        _path = _path.replace("//", "/");
        return _path;
    }


    /**
     * return all of the message payload properties, without http request/response parts.
     * @param props
     * @return
     */
    public Map<String, Object> extractPropertiesFromPayload(Object props) throws IOException
    {
        Map<String, Object> methodArgs = new HashMap<String, Object>();


        if( props instanceof Map )
        {
            methodArgs.putAll( (Map)props );
        }
        else
        {
            Method[] methods = props.getClass().getDeclaredMethods();

            for (Method method : methods)
            {
                if( method.getName().startsWith("get") )
                {
                    try
                    {
                        String name = method.getName().substring(3, method.getName().length());
                        Object val = method.invoke(props, null);

                        methodArgs.put(name, val);
                    }catch (Exception ex){}
                }
            }

            if( props instanceof IDocumentJob)
            {
                methodArgs.put(ApiServerConstants.IMAGE, ((IDocumentJob)props).getDocument().getFile() );
            }
        }

        return methodArgs;
    }
}
