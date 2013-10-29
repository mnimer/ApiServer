package apiserver.core.views;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * User: mikenimer
 * Date: 10/19/13
 */
public class ImageViewHandler extends AbstractUrlBasedView
{
    private final Logger log = LoggerFactory.getLogger(ImageViewHandler.class);

    public static final String DEFAULT_CONTENT_TYPE = "image/png";


    public ImageViewHandler()
    {
        setContentType(DEFAULT_CONTENT_TYPE);
        setExposePathVariables(false);
    }


    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        super.render(model, request, response);
    }


    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        log.debug(model.toString());
    }
}
