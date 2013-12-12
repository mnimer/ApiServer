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

    <cffunction name="htmlToPdf" output="false" access="remote" returntype="ANY" >
        <cfargument name="html" type="string">
        <cfargument name="headerHtml" type="string" required="false">
        <cfargument name="footerHtml" type="string" required="false">

        <cfoutput>
        <cfdocument format="pdf" >
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


</cfcomponent>