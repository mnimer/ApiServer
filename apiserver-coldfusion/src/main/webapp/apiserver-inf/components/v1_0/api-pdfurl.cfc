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

    <cffunction name= 'urltopdf'>
        <cfargument name="url" type="string"/>


<!--- Call a webpage--->
        <cfhttp url="#arguments.url#" method="get" resolveurl="yes">
<!--- Convert content of webpage to PDF and Scale it --->
        <cfdocument name="pdf" format="PDF" localurl="no">
            <cfoutput>#cfhttp.fileContent#</cfoutput>
        </cfdocument>

        <cfset results = structNew()>
        <cfset results['pdfs'] = pdf>
        <cfreturn results>
    </cffunction>
</cfcomponent>