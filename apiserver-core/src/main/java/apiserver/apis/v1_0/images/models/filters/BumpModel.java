package apiserver.apis.v1_0.images.models.filters;

import apiserver.core.models.FileModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: mikenimer
 * Date: 7/14/13
 */
public class BumpModel extends FileModel
{
    public final Logger log = LoggerFactory.getLogger(BumpModel.class);

    private int edgeAction;
    private boolean useAlpha;
    private float[] matrix;


    public int getEdgeAction()
    {
        return edgeAction;
    }


    public void setEdgeAction(int edgeAction)
    {
        this.edgeAction = edgeAction;
    }


    public boolean isUseAlpha()
    {
        return useAlpha;
    }


    public void setUseAlpha(boolean useAlpha)
    {
        this.useAlpha = useAlpha;
    }


    public float[] getMatrix()
    {
        return matrix;
    }


    public void setMatrix(float[] matrix)
    {
        this.matrix = matrix;
    }
}
