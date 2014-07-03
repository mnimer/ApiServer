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

<cfcomponent>

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
    <cffunction name="extractText">
        <cfargument name="file">
        <cfargument name="options">

        <cfpdf
                action="extracttext"
                name="pdfResult"
                source="#file#"
                attributeCollection="#options#">

        <cfreturn pdfResult>
    </cffunction>

<!---
    extract all the images in the PDF.
--->
    <cffunction name="extractImage">
        <cfargument name="file">
        <cfargument name="options">

        <cfpdf
                action="extractimage"
                name="pdfResult"
                source="#file#"
                attributeCollection="#options#">

        <cfreturn pdfResult>
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
            <cfset tmpFile = GetTempFile(GetTempDirectory(),"#createUUID()#.pdf")>
            <cffile action="write" file="#tmpFile#" output="#BinaryDecode(file, "Base64")#"/>

            <cfpdf
                    action="protect"
                    name="protectedPdf"
                    source="#tmpFile#"
                    newUserPassword="admin">

            <cfreturn BinaryEncode(toBinary(protectedPdf), "Base64")>

            <cffinally>
                <cfif fileExists(tmpFile)>
                    <cfset fileDelete(tmpFile)>
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