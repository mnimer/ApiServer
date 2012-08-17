package library.service.interfaces;


import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * User: mnimer
 * Date: 8/17/12
 */
public interface Status
{
    Map ping(HttpServletRequest request);
}
