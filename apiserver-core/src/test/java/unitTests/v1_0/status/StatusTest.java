package unitTests.v1_0.status;

import apiserver.apis.v1_0.status.controllers.StatusController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * User: mnimer
 * Date: 9/19/12
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:**/config/application-context.xml"})
public class StatusTest
{

    public StatusController statusController;


    @Autowired
    public void setStatusController(StatusController statusController)
    {
        this.statusController = statusController;
    }


    @Test
    public void testSimpleStatus()
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        Object view = statusController.systemCheck();//request, response);

        Assert.isInstanceOf(ModelAndView.class, view);
        Assert.isTrue(((ModelAndView) view).getModel().get("status").toString().equals("ok"));
    }


    @Test
    public void testColdFusionStatus() throws Exception
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        Object view = statusController.coldFusionCheck(request, response);

        Assert.isInstanceOf(ModelAndView.class, view);
        Assert.isTrue(((ModelAndView) view).getModel().get("status").toString().equals("ok"));
    }


}
