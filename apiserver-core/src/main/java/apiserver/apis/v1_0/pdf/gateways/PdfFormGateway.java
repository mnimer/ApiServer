package apiserver.apis.v1_0.pdf.gateways;

import apiserver.apis.v1_0.pdf.gateways.jobs.ExtractPdfFormJob;
import apiserver.apis.v1_0.pdf.gateways.jobs.PopulatePdfFormJob;

import java.util.Map;
import java.util.concurrent.Future;

/**
 * Created by mnimer on 4/13/14.
 */
public interface PdfFormGateway
{
    Future<Map> extractPdfForm(ExtractPdfFormJob args);
    Future<Map> populatePdfForm(PopulatePdfFormJob args);
}
