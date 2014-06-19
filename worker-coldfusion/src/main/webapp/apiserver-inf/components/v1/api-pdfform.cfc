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

    <cffunction name="pdfForm">
        <cfargument name="policyNum"/>
        <cfargument name="strInsuredName"/>
        <cfargument name="address1"/>
        <cfargument name="state"/>
        <cfargument name="zip"/>
        <cfargument name="city"/>
        <cfargument name="phone"/>
        <cfargument name="bankName"/>
        <cfargument name="PaymentName"/>
        <cfargument name="bankAcctNum"/>
        <cfargument name="bankCity"/>

        <cfpdfform name="myForm"
                action="populate"
                source="#expandPath('/demos-inf/pdfs/EFTAuthorization.pdfs')#">
            <cfpdfformparam name="ViewPolicy*PolicyNum" value="#PolicyNum#">
            <cfpdfformparam name="ViewPolicy*strInsuredName" value="#strInsuredName#">
            <cfpdfformparam name="ViewPolicy*address1" value="#address1#">
            <cfpdfformparam name="ViewPolicy*State" value="#State#">
            <cfpdfformparam name="ViewPolicy*Zip" value="#Zip#">
            <cfpdfformparam name="ViewPolicy*city" value="#city#">
            <cfpdfformparam name="ViewPolicy*phone" value="#phone#">
            <cfpdfformparam name="EFTAuthorization*bankName" value="#bankName#">
            <cfpdfformparam name="EFTAuthorization*PaymentName" value="#PaymentName#">
            <cfpdfformparam name="EFTAuthorization*bankAcctNum" value="#bankAcctNum#">
            <cfpdfformparam name="EFTAuthorization*bankCity" value="#bankCity#">
        </cfpdfform>

        <cfset binaryPdf = ToBinary(myForm)>

        <cffile action="write"
                file="#expandPath('/demos-inf/pdfs/populatedForm.pdfs')#"
                output="#binaryPdf#"/>

        <cfreturn binaryPdf>
    </cffunction>




    <cffunction name="extractPdfForm">
        <cfargument name="file" type="STRING">

        <cftry>
            <cffile action="write"
                    nameconflict="overwrite"
                    file="#path#" output="#toBinary(file)#"/>

            <cfoutput>
                    <cfpdfform
                            action="read"
                            source="#path#"
                            result="pdfResult"/>
            </cfoutput>

            <cfcatch type="any">
                <cfdump var="#cfcatch#" output="console"/>
            </cfcatch>

            <cffinally>
                <cffile action="delete" file="#path#"/>
            </cffinally>
        </cftry>

        <cfreturn pdfResult>
    </cffunction>




    <cffunction name="populatePdfForm">
        <cfargument name="file" type="STRING">
        <cfargument name="xfdf" type="STRING">

        <cftry>
            <cffile action="write"
                    nameconflict="overwrite"
                    file="#path#" output="#toBinary(file)#"/>

            <cfoutput>
                <cfpdfform
                        action="populate"
                        source="#path#"
                        xmldata="#arguments.xfdf#"
                        attributeCollection="#arguments.options#"/>
            </cfoutput>

            <cfcatch type="any">
                <cfdump var="#cfcatch#" output="console"/>
            </cfcatch>

            <cffinally>
                <cffile action="delete" file="#path#"/>
            </cffinally>
        </cftry>

        <cfreturn pdfResult>
    </cffunction>


</cfcomponent>
