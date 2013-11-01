package unitTests.v1_0.images;

/*******************************************************************************
 Copyright (c) 2013 Mike Nimer.

 This file is part of ApiServer Project.

 The ApiServer Project is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 The ApiServer Project is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with the ApiServer Project.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

import apiserver.apis.v1_0.documents.DocumentJob;
import apiserver.apis.v1_0.documents.gateway.DocumentGateway;
import apiserver.apis.v1_0.documents.gateway.jobs.UploadDocumentJob;
import apiserver.apis.v1_0.documents.model.Document;
import apiserver.apis.v1_0.images.ImageConfigMBean;
import apiserver.apis.v1_0.images.ImageConfigMBeanImpl;
import apiserver.apis.v1_0.images.gateways.images.ImageMetadataGateway;
import apiserver.apis.v1_0.images.gateways.jobs.images.FileMetadataJob;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import unitTests.v1_0.images.filters.ImageMotionBlurTests;

import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * User: mikenimer
 * Date: 8/20/13
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "file:apiserver-core/src/main/webapp/WEB-INF/config/application-context-test.xml",
        "file:apiserver-core/src/main/webapp/WEB-INF/config/v1_0/apis-servlet-test.xml",
        "file:apiserver-core/src/main/webapp/WEB-INF/config/v1_0/flows/image/imageMetadata-flow.xml"})

public class ImageMetadataEXIFToolExtractorTest
{
    public final Logger log = LoggerFactory.getLogger(ImageMetadataDrewMetadataExtractorTest.class);

    public String hostName = "";
    public int port = 0;


    @Autowired
    public ImageMetadataGateway gateway;

    @Autowired
    public ImageConfigMBean config;

    private @Value("#{applicationProperties.defaultReplyTimeout}") Integer defaultTimeout;

    @Qualifier("documentAddGateway")
    @Autowired
    private DocumentGateway documentGateway;

    private String documentId;


    @Before
    public void setup() throws URISyntaxException, IOException, InterruptedException, ExecutionException
    {
        config.setMetadataLibrary(ImageConfigMBeanImpl.EXIFTOOL_METADATA_EXTRACTOR);
        System.out.println(config.getMetadataLibrary());

        File file = new File(  ImageMotionBlurTests.class.getClassLoader().getResource("sample.png").toURI()  );

        UploadDocumentJob job = new UploadDocumentJob(file);
        job.setDocument(new Document(file));
        Future<DocumentJob> doc = documentGateway.addDocument(job);
        documentId = ((DocumentJob)doc.get()).getDocument().getId();
    }



    @Test
    public void testMetadata() throws ServletException, IOException, Exception
    {
        int width = 500;
        int height = 296;
        String fileName = "Apple iPhone 4.jpeg";
        String url = "/rest/v1/image/metadata/info.json";

        File file = new File(this.getClass().getClassLoader().getSystemResource(fileName).toURI());


        FileMetadataJob model = new FileMetadataJob();
        model.setDocumentId(documentId);
        Future<Map> future = gateway.getMetadata(model);


        FileMetadataJob result = (FileMetadataJob)future.get(defaultTimeout, TimeUnit.MILLISECONDS);


        ObjectMapper mapper = new ObjectMapper();
        Map metadata = result.getMetadata();

        //Assert.notNull(root.get("coldfusion"));
        Assert.isTrue( metadata.get("ExifIFD") != null );  //ExifIFD={White Balance=Auto, Aperture Value=2.8, Color Space=sRGB, Subject Area=0, F Number=2.8, Date/Time Original=2011:01:13 14:33:39, Metering Mode=Average, Sharpness=Hard, Scene Capture Type=Standard, Exposure Mode=Auto, Exif Image Width=1296, Exif Version=0221, Exposure Time=1/15, ISO=500, Sensing Method=One-chip color area, Flash=Auto, Did not fire, Exif Image Height=968, Shutter Speed Value=1/15, Focal Length=3.9 mm, Exposure Program=Program AE, Flashpix Version=0100, Create Date=2011:01:13 14:33:39}
        Assert.isTrue( metadata.get("ICC_Profile") != null ); //ICC_Profile={Media White Point=0.95045 1 1.08905, Profile Copyright=Copyright (c) 1998 Hewlett-Packard Company, Red Tone Reproduction Curve=(Binary data 2060 bytes, use -b option to extract), Viewing Cond Desc=Reference Viewing Condition in IEC61966-2.1, Blue Matrix Column=0.14307 0.06061 0.7141, Media Black Point=0 0 0, Red Matrix Column=0.43607 0.22249 0.01392, Green Tone Reproduction Curve=(Binary data 2060 bytes, use -b option to extract), Device Model Desc=IEC 61966-2.1 Default RGB colour space - sRGB, Luminance=76.03647 80 87.12462, Device Mfg Desc=IEC http://www.iec.ch, Blue Tone Reproduction Curve=(Binary data 2060 bytes, use -b option to extract), Profile Description=sRGB IEC61966-2.1, Green Matrix Column=0.38515 0.71687 0.09708, Technology=Cathode Ray Tube Display}
        Assert.isTrue( metadata.get("JFIF") != null );  //JFIF={X Resolution=72, Resolution Unit=inches, JFIF Version=1.01, Y Resolution=72}
        Assert.isTrue( metadata.get("IFD0") != null );  //IFD0={Software=4.1, Orientation=Horizontal (normal), X Resolution=72, Camera Model Name=iPhone 4, Modify Date=2011:01:13 14:33:39, Resolution Unit=inches, Y Resolution=72, Make=Apple}
        Assert.isTrue( metadata.get("ICC-view") != null ); //ICC-view={Viewing Cond Illuminant Type=D50, Viewing Cond Surround=3.92889 4.07439 3.36179, Viewing Cond Illuminant=19.6445 20.3718 16.8089}
        Assert.isTrue( metadata.get("ICC-header") != null ); //ICC-header={Device Manufacturer=IEC, Profile File Signature=acsp, Primary Platform=Microsoft Corporation, CMM Flags=Not Embedded, Independent, Profile Version=2.1.0, Profile Date Time=1998:02:09 06:49:00, Profile Connection Space=XYZ, Connection Space Illuminant=0.9642 1 0.82491, Profile ID=0, Color Space Data=RGB, Profile CMM Type=Lino, Profile Class=Display Device Profile, Profile Creator=HP, Rendering Intent=Media-Relative Colorimetric, Device Model=sRGB, Device Attributes=Reflective, Glossy, Positive, Color}
        Assert.isTrue( metadata.get("File") != null );    //File={Y Cb Cr Sub Sampling=YCbCr4:2:0 (2 2), Encoding Process=Baseline DCT, Huffman coding, File Type=JPEG, Bits Per Sample=8, MIME Type=image/jpeg, Image Height=968, Exif Byte Order=Big-endian (Motorola, MM), Image Width=1296, Color Components=3}
        Assert.isTrue( metadata.get("GPS") != null );   //GPS={GPS Latitude Ref=North, GPS Img Direction=177.5577889, GPS Img Direction Ref=True North, GPS Time Stamp=14:33:35.62, GPS Longitude=12 deg 29' 19.80", GPS Longitude Ref=East, GPS Latitude=41 deg 51' 10.80"}
        Assert.isTrue( metadata.get("System") != null );  //System={File Permissions=rw-r--r--, File Modification Date/Time=2013:08:26 12:06:17-05:00, File Name=1619b020-797f-4651-a957-5f2991109d14.jpeg, File Size=330 kB, File Inode Change Date/Time=2013:08:26 12:06:17-05:00, File Access Date/Time=2013:08:26 12:06:17-05:00, Directory=/var/folders/2m/2tfb5my902ggdnbp491mpbq80000gn/T}
        Assert.isTrue( metadata.get("ICC-meas") != null );  //ICC-meas={Measurement Illuminant=D65, Measurement Geometry=Unknown (0), Measurement Observer=CIE 1931, Measurement Flare=0.999%, Measurement Backing=0 0 0}
        Assert.isTrue( metadata.get("Composite") != null );  //Composite={Shutter Speed=1/15, Light Value=4.6, Focal Length=3.9 mm, Image Size=1296x968, GPS Position=41 deg 51' 10.80" N, 12 deg 29' 19.80" E, Aperture=2.8, GPS Longitude=12 deg 29' 19.80" E, GPS Latitude=41 deg 51' 10.80" N}
        Assert.isTrue( metadata.get("ExifTool") != null );  //ExifTool={ExifTool Version Number=9.24}

    }
}
