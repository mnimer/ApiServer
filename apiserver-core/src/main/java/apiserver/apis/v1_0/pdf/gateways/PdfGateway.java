package apiserver.apis.v1_0.pdf.gateways;

import apiserver.apis.v1_0.pdf.gateways.jobs.AddFooterPdfJob;
import apiserver.apis.v1_0.pdf.gateways.jobs.AddHeaderPdfJob;
import apiserver.apis.v1_0.pdf.gateways.jobs.DDXPdfJob;
import apiserver.apis.v1_0.pdf.gateways.jobs.DeletePdfPagesJob;
import apiserver.apis.v1_0.pdf.gateways.jobs.ExtractImageJob;
import apiserver.apis.v1_0.pdf.gateways.jobs.ExtractTextJob;
import apiserver.apis.v1_0.pdf.gateways.jobs.FlattenPdfJob;
import apiserver.apis.v1_0.pdf.gateways.jobs.LinerizePdfJob;
import apiserver.apis.v1_0.pdf.gateways.jobs.MergePdfJob;
import apiserver.apis.v1_0.pdf.gateways.jobs.OptimizePdfJob;
import apiserver.apis.v1_0.pdf.gateways.jobs.PdfInfoJob;
import apiserver.apis.v1_0.pdf.gateways.jobs.RemoveHeaderFooterJob;
import apiserver.apis.v1_0.pdf.gateways.jobs.SecurePdfJob;
import apiserver.apis.v1_0.pdf.gateways.jobs.ThumbnailPdfJob;
import apiserver.apis.v1_0.pdf.gateways.jobs.WatermarkPdfJob;

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

    Future<Map> addFooterToPdf(AddFooterPdfJob args);

    Future<Map> addHeaderToPdf(AddHeaderPdfJob args);

    Future<Map> removeHeaderFooter(RemoveHeaderFooterJob args);

    Future<Map> pdfInfo(PdfInfoJob args);

    Future<Map> deletePages(DeletePdfPagesJob args);

    Future<Map> securePdf(SecurePdfJob args);

    Future<Map> thumbnail(ThumbnailPdfJob job);

    Future<Map> linerizePdf(LinerizePdfJob job);

    Future<Map> flattenPdf(FlattenPdfJob job);

    Future<Map> transformPdf(SecurePdfJob job);

    Future<Map> addWatermarkToPdf(WatermarkPdfJob job);

    Future<Map> removeWatermarkFromPdf(WatermarkPdfJob job);
}
