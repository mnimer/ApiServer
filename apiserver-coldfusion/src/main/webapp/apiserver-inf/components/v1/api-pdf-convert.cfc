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

    <cffunction name="urlToPdf" output="false" access="remote" returntype="ANY" >
        <cfargument name="path" type="string">
        <cfargument name="options" type="any" default="#structNew()#">

        <cfdump var="#options#" output="console"/>
        <cfscript>
            if( isJSON(arguments.options) )
            {
                arguments.options = DeserializeJSON(arguments.options);
            }
        </cfscript>

        <cfoutput>
        <cfdocument
                format="pdf"
                src="#path#"
                attributeCollection="#arguments.options#"></cfdocument>
        </cfoutput>
    </cffunction>



    <cffunction name="htmlToPdf" output="false" access="remote" returntype="ANY" >
        <cfargument name="html" type="string">
        <cfargument name="headerHtml" type="string" required="false">
        <cfargument name="footerHtml" type="string" required="false">
        <cfargument name="options" type="STRUCT" default="#structNew()#">

        <cfscript>
            if( isJSON(arguments.options) )
            {
                arguments.options = DeserializeJSON(arguments.options);
            }
        </cfscript>

        <cfoutput>
        <cfdocument format="pdf" attributeCollection="#arguments.options#">
            <cfif isDefined("headerHtml")>
                <cfdocumentitem type="header">#headerHtml#</cfdocumentitem>
            </cfif>

            #html#

            <cfif isDefined("footerHtml")>
                <cfdocumentitem type="footer">#footerHtml#</cfdocumentitem>
            </cfif>
        </cfdocument>
        </cfoutput>

    </cffunction>



    <cffunction name="docToPdf" output="true" access="remote" returntype="ANY" >
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

            <cfoutput>
            <cfdocument
                    format="pdf"
                    srcFile="#path#"
                    attributeCollection="#arguments.options#">
            </cfdocument>
            </cfoutput>

            <cfcatch type="any">
                <cfdump var="#cfcatch#" output="console"/>
            </cfcatch>

            <cffinally>
                <cffile action="delete" file="#path#"/>
            </cffinally>
        </cftry>
    </cffunction>

</cfcomponent>