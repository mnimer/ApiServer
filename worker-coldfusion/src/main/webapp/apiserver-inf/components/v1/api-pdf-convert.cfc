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

    <cffunction name="urlToPdf" access="remote" returntype="Binary">
        <cfargument name="path" type="string">
        <cfargument name="options" type="any" default="#structNew()#">

        <cfdump var="#options#" output="console"/>
        <cfscript>
            if( isJSON(arguments.options) )
            {
                arguments.options = DeserializeJSON(arguments.options);
            }
        </cfscript>

        <cfdocument
                format="pdf"
                src="#path#" name="pdfResult"
                attributeCollection="#options#"></cfdocument>

        <cfreturn pdfResult/>
    </cffunction>



    <cffunction name="htmlToPdf" >
        <cfargument name="HTML" type="string">
        <cfargument name="HEADERHTML" type="string" default="default header">
        <cfargument name="FOOTERHTML" type="string" default="default footer">
        <cfargument name="OPTIONS" type="STRUCT" default="#structNew()#">

        <cfparam name="HEADERHTML" default="default header2"/>

        <cfdump var="#arguments#" output="console">

        <cfscript>
            if( isJSON(arguments.options) )
            {
                arguments.options = DeserializeJSON(arguments.options);
            }
        </cfscript>

        <cfoutput>
        <cfdocument
                name="pdfResult"
                format="pdf"
                attributeCollection="#arguments.options#">
            <cfdocumentitem type="header">Header:<cfif isDefined("HEADERHTML")>#HEADERHTML#</cfif></cfdocumentitem>
            #HTML#

            <cfdocumentitem type="footer" evalatprint="true">
                <cfoutput>#cfdocument.currentpagenumber# of #cfdocument.totalpagecount#</cfoutput>
            </cfdocumentitem>
            </cfdocument></cfoutput>

        <cfreturn pdfResult/>
    </cffunction>



    <cffunction name="docToPdf">
        <cfargument name="file" type="STRING">
        <cfargument name="contentType" type="STRING">
        <cfargument name="name" type="STRING">
        <cfargument name="options" type="STRUCT" default="#structNew()#">

        <cfscript>
            if( isJSON(arguments.options) )
            {
                arguments.options = DeserializeJSON(arguments.options);
            }
        </cfscript>

        <cfset path = getTempDirectory() &createUUID() &"_" &name>
        <cfdump var="#path#" output="console"/>

        <cftry>
            <cffile action="write"
                    nameconflict="overwrite"
                    file="#path#" output="#toBinary(file)#"/>


            <cfdocument
                    name="pdfResult"
                    format="pdf"
                    srcFile="#path#"
                    attributeCollection="#arguments.options#">
            </cfdocument>

            <cfreturn pdfResult/>

            <cfcatch type="any">
                <cfrethrow/>
            </cfcatch>

            <cffinally>
                <cffile action="delete" file="#path#"/>
            </cffinally>
        </cftry>
    </cffunction>

</cfcomponent>