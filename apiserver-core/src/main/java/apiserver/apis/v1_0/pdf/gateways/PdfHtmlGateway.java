package apiserver.apis.v1_0.pdf.gateways;

import apiserver.apis.v1_0.pdf.gateways.jobs.PdfHtmlJob;

import java.util.Map;
import java.util.concurrent.Future;

/**
 * User: mikenimer
 * Date: 9/16/13
 */
public interface PdfHtmlGateway
{
    Future<Map> convertHtmlToPdf(PdfHtmlJob args);
}
