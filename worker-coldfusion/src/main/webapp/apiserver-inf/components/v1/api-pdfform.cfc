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

    <cffunction name="populateFormFields" access="remote" returntype="Any">
        <cfargument name="file"/>
        <cfargument name="fields" hint="xml packet of field name/values"/>
        <cfargument name="password" required="false"/>

        <cfset _options = structNew()>
        <cfif isDefined("password") and len(password) gt 0>
            <cfset _options["password"] = password>
        </cfif>


        <cfsavecontent variable="x1">
            <?xml version="1.0" encoding="UTF-8"?><xfdf xmlns="http://ns.adobe.com/xfdf/" xml:space="preserve"><fields><field name="Check1"><value>Yes</value></field><field name="Email"><value>mike@foo.com</value></field><field name="FavoriteColor"><value>green</value><value>purple</value></field><field name="FirstName"><value>Mike</value></field><field name="LastName"><value>Nimer</value></field></fields><ids original="BEE1C4660EB520429C7654A7A02417CE" modified="2C87012FF28C4BF8AC0CA24ECF314DEB"></ids></xfdf>
        </cfsavecontent>
<!---
       <cfsavecontent variable="x2">
           <xfa:data xmlns:xfa="http://www.xfa.org/schema/xfa-data/1.0/">
               <form1>
                   <Check1>Yes</Check1>
                   <Email>mike@foo.com</Email>
                   <FavoriteColor>green</FavoriteColor>
                   <FirstName>Mike</FirstName>
                   <LastName>Nimer</LastName>
               </form1>
           </xfa:data>
       </cfsavecontent>
       --->
        <cfpdfform
                action="populate"
                source="#file#"
                xmldata="#trim(x1)#">
        </cfpdfform>

    </cffunction>


    <cffunction name="extractFormFields" access="remote" returntype="struct">
        <cfargument name="file">

        <cfpdfform
                action="read"
                source="#file#"
                result="pdfResult"/>

        <cfreturn pdfResult>
    </cffunction>


</cfcomponent>
