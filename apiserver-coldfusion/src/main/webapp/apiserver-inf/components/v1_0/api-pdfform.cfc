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
        <cfargument name="PolicyNum"/>
        <cfargument name="strInsuredName"/>
        <cfargument name="address1"/>
        <cfargument name="State"/>
        <cfargument name="Zip"/>
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

        <cfset results = structNew()>
        <cfset results['pdfs'] = binaryPdf>
        <cfreturn results>
    </cffunction>


    <cffunction name="readPdf">
        <cfpdfform
                action="read"
                source="#expandPath('/demos-inf/pdfs/populatedForm.pdfs')#"
                result="pdfResult"/>

        <cfset results = structNew()>
        <cfset results['pdfs'] = pdfResult>
        <cfreturn results>
    </cffunction>


    <cffunction name="dump">
        <cfargument name="data">

        <cfsavecontent variable="dump">
            <cfdump var="#data#" expand="true"/>
        </cfsavecontent>

        <cfreturn dump>
    </cffunction>
</cfcomponent>
