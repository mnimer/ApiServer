<!---~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
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
 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~--->

<cfcomponent extends="utils">

<!---
    Add footer
--->
    <cffunction name="addFooter">
        <cfargument name="file">
        <cfargument name="options">

        <cfpdf
                action="addFooter"
                name="pdfResult"
                source="#file#"
                attributeCollection="#options#">

        <cfreturn pdfResult>
    </cffunction>


<!---
    Add Header
--->
    <cffunction name="addHeader">
        <cfargument name="file">
        <cfargument name="options">

        <cfpdf
                action="addHeader"
                name="pdfResult"
                source="#file#"
                attributeCollection="#options#">

        <cfreturn pdfResult>
    </cffunction>


<!---
    Add a watermark to a PDF document
--->
    <cffunction name="addWatermark">
        <cfargument name="file">
        <cfargument name="options">

        <cfpdf
                action="addwatermark"
                name="pdfResult"
                source="#file#"
                attributeCollection="#options#">

        <cfreturn pdfResult>
    </cffunction>


<!---
    Delete pages from a PDF document
--->
    <cffunction name="deletePages">
        <cfargument name="file">
        <cfargument name="options">

        <cfpdf
                action="deletepages"
                name="pdfResult"
                source="#file#"
                attributeCollection="#options#">

        <cfreturn pdfResult>
    </cffunction>


<!---
    extract all the words in the PDF.
--->
    <cffunction name="extractText" access="remote">
        <cfargument name="file">
        <cfargument name="options" default="#structNew()#">

        <cfscript>
            var images = arrayNew(1);
            var tmpDir = GetTempDirectory();
            var tmpDir = "#tmpDir##createUUID()#";
            var tmpFile = "";//init

            // create tmp dir.
            directoryCreate(tmpDir);

            var tmpFile = GetTempFile(tmpDir, "#createUUID()#.pdf");

        </cfscript>


        <cfset _options = transformMapToStruct(options)>
        <cfparam name="_options.type" default="xml">
        <cfparam name="_options.pages" default="*">


        <cftry>
            <cfif isBase64(file)>
                <cffile action="write" file="#tmpFile#" output="#BinaryDecode(file, "Base64")#"/>
            <cfelse>
                <cfset tmpFile = file>
            </cfif>


            <cfpdf
                    action="extracttext"
                    name="pdfResult"
                    source="#tmpFile#"
                    attributeCollection="#_options#">

            <cfdump var="#pdfResult#" output="console"/>
            <cfreturn pdfResult>

            <cffinally>
                <cfif directoryExists(tmpDir)>
                    <cfset directoryDelete(tmpDir, true)>
                </cfif>
            </cffinally>
        </cftry>

    </cffunction>

    <!---
        extract all the images in the PDF.
    --->
    <cffunction name="extractImage" access="remote">
        <cfargument name="file">
        <cfargument name="options">
        <cfscript>
            var images = arrayNew(1);
            var tmpDir = GetTempDirectory();
            var tmpDir = "#tmpDir##createUUID()#";
            var tmpFile = GetTempFile(tmpDir, "#createUUID()#.pdf");

        // create tmp dir.
            directoryCreate(tmpDir);

            if (not isDefined("options")) {
                //options = structNew();
            }
        </cfscript>

        <cftry>
            <cfparam name="options['pages']" default="*"/>
            <cfparam name="options['format']" default="jpg"/>

            <!---
            <cfif not isBinary(file)>
                <cfset tmpFile = file>
            <cfelse>
            </cfif>
            --->
            <cffile action="write" file="#tmpFile#" output="#BinaryDecode(file, "Base64")#"/>


            <cfpdf
                    action="extractimage"
                    overwrite="true"
                    destination="#tmpDir#"
                    pages="*"
                    format="#options['format']#"
                    source="#tmpFile#">

            <cfdirectory name="fileList" action="list" directory="#tmpDir#" filter="*.#options['format']#">
            <cfloop query="#fileList#">
                <cfscript>
                    file = fileReadBinary("#directory#/#name#");
                    fileStruct = structNew();
                    fileStruct.name = name;
                    fileStruct.file = BinaryEncode(file, "Base64");
                    arrayAppend(images, fileStruct);
                    //fileDelete("#directory#/#name#");
                </cfscript>
            </cfloop>
            <cfreturn images>

            <cffinally>
                <cfif directoryExists(tmpDir)>
                    <cfset directoryDelete(tmpDir, true)>
                </cfif>
            </cffinally>
        </cftry>

    </cffunction>


<!---
    Retrieve information about a PDF document
--->
    <cffunction name= 'getInfo'>
        <cfargument name="file">
        <cfargument name="options">

        <cfpdf
                action="getinfo"
                name="pdfResult"
                source="#file#"
                attributeCollection="#options#">

        <cfreturn pdfResult>
    </cffunction>


<!---
    Generate thumbnails from pages in a PDF document
--->
    <cffunction name= 'generateThumbnail'>
        <cfargument name="file">
        <cfargument name="options">

        <cfpdf
                action="thumbnail"
                name="pdfResult"
                source="#file#"
                attributeCollection="#options#">

        <cfreturn pdfResult>
    </cffunction>


<!---
    Merge PDF documents into an output PDF file
--->
    <cffunction name= 'mergePdf'>
        <cfargument name="file">
        <cfargument name="options">

        <cfpdf
                action="merge"
                name="pdfResult"
                source="#file#"
                attributeCollection="#options#">

        <cfreturn pdfResult>
    </cffunction>

<!---
    Reduce the quality of a PDF document
--->
    <cffunction name= 'optimizePdf'>
        <cfargument name="file">
        <cfargument name="options">

        <cfpdf
                action="deletepages"
                name="pdfResult"
                source="#file#"
                attributeCollection="#options#">

        <cfreturn pdfResult>
    </cffunction>


<!---
    Use DDX instructions to manipulate PDF documents
--->
    <cffunction name= 'processDDX'>
        <cfargument name="file">
        <cfargument name="ddx">

        <cfpdf
                action="processddx"
                name="pdfResult"
                source="#file#"
                attributeCollection="#options#">

        <cfreturn pdfResult>
    </cffunction>


<!---
    Set passwords and encrypt PDF documnets
--->
    <cffunction name="protectPdf" access="remote" returntype="any">
        <cfargument name="file">
        <cfargument name="options">

        <cftry>
            <cfset tmpDir = "#GetTempDirectory()##createUUID()#">
            <cfset tmpFile = GetTempFile(tmpDir, "#createUUID()#.pdf")>
            <cffile action="write" file="#tmpFile#" output="#BinaryDecode(file, "Base64")#"/>

            <cfpdf
                    action="protect"
                    name="protectedPdf"
                    source="#tmpFile#"
                    attributeCollection="#options#">

            <cfreturn BinaryEncode(toBinary(protectedPdf), "Base64")>

            <cffinally>
                <cfif directoryExists(tmpDir)>
                    <cfset directoryDelete(tmpDir, true)>
                </cfif>
            </cffinally>
        </cftry>
    </cffunction>


<!---
    Remove a watermark from a PDF document
--->
    <cffunction name="removeWatermark">
        <cfargument name="file">
        <cfargument name="options">

        <cfpdf
                action="removewatermark"
                name="pdfResult"
                source="#file#"
                attributeCollection="#options#">

        <cfreturn pdfResult>
    </cffunction>


<!---
    Delete headers and footers .
--->
    <cffunction name="removeHeaderFooter">
        <cfargument name="file">
        <cfargument name="options">

        <cfpdf
                action="removeheaderfooter"
                name="pdfResult"
                source="#file#"
                attributeCollection="#options#">

        <cfreturn pdfResult>
    </cffunction>


<!---
    Set information about a PDF document
--->
    <cffunction name= 'setPdfInfo'>
        <cfargument name="file">
        <cfargument name="options">

        <cfpdf
                action="setinfo"
                name="pdfResult"
                source="#file#"
                attributeCollection="#options#">

        <cfreturn pdfResult>
    </cffunction>


<!---
    Page level transformations
--->
    <cffunction name= 'transformPdf'>
        <cfargument name="file">
        <cfargument name="options">

        <cfpdf
                action="transform"
                name="pdfResult"
                source="#file#"
                attributeCollection="#options#">

        <cfreturn pdfResult>
    </cffunction>


</cfcomponent>