package apiserver.apis.v1_0.common;

import apiserver.ApiServerConstants;
import com.adobe.xmp.impl.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.message.GenericMessage;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Take an HTTP request and convert it into a spring integration message and send it through the system.
 *
 * User: mnimer
 * Date: 9/18/12
 *
 */
public class HttpChannelInvoker
{

    public ModelAndView invokeGenericChannel(HttpServletRequest request, HttpServletResponse response, Map arguments, MessageChannel channel)
    {
        if( arguments == null )
        {
            arguments = new HashMap<String, Object>();
        }
        arguments.put(ApiServerConstants.HTTP_REQUEST, request);
        arguments.put(ApiServerConstants.HTTP_RESPONSE, response);
        arguments.putAll(arguments);

        GenericMessage msg = new GenericMessage( arguments );
        channel.send(msg);

        Map payload = (Map)msg.getPayload();
        payload.remove(ApiServerConstants.HTTP_REQUEST);
        payload.remove(ApiServerConstants.HTTP_RESPONSE);

        ModelAndView view = new ModelAndView("help");
        view.addAllObjects( payload );
        return view;
    }





}
