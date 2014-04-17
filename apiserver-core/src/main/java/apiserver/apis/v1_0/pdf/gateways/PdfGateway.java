package apiserver.apis.v1_0.pdf.gateways;

import apiserver.apis.v1_0.pdf.gateways.jobs.DDXPdfJob;
import apiserver.apis.v1_0.pdf.gateways.jobs.ExtractImageJob;
import apiserver.apis.v1_0.pdf.gateways.jobs.ExtractTextJob;
import apiserver.apis.v1_0.pdf.gateways.jobs.MergePdfJob;
import apiserver.apis.v1_0.pdf.gateways.jobs.OptimizePdfJob;

import java.util.Map;
import java.util.concurrent.Future;

/**
 * Created by mnimer on 4/13/14.
 */
public interface PdfGateway
{
    Future<Map> mergePdf(MergePdfJob args);

    Future<Map> optimizePdf(OptimizePdfJob args);

    Future<Map> processDDX(DDXPdfJob args);

    Future<Map> extractText(ExtractTextJob args);

    Future<Map> extractImage(ExtractImageJob args);
}
