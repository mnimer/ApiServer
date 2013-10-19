package apiserver.core.convertors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * User: mikenimer
 * Date: 10/19/13
 */
public class PngMessageConvertor extends AbstractHttpMessageConverter
{
    private final Logger log = LoggerFactory.getLogger(PngMessageConvertor.class);


    public PngMessageConvertor()
    {
        /** Creates a new instance of the {@code ByteArrayHttpMessageConverter}. */
        super(new MediaType("image", "png"), MediaType.IMAGE_PNG);
    }


    @Override
    protected boolean supports(Class clazz)
    {
        return true;
    }


    public byte[] readInternal(Class clazz, HttpInputMessage inputMessage) throws IOException
    {
        long contentLength = inputMessage.getHeaders().getContentLength();
        ByteArrayOutputStream bos = new ByteArrayOutputStream(contentLength >= 0 ? (int) contentLength : StreamUtils.BUFFER_SIZE);
        StreamUtils.copy(inputMessage.getBody(), bos);
        return bos.toByteArray();
    }


    protected Long getContentLength(byte[] bytes, MediaType contentType)
    {
        return (long) bytes.length;
    }


    protected void writeInternal(byte[] bytes, HttpOutputMessage outputMessage) throws IOException
    {
        StreamUtils.copy(bytes, outputMessage.getBody());
    }


    @Override
    protected void writeInternal(Object o, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException
    {
        log.debug(o.toString());
    }
}
