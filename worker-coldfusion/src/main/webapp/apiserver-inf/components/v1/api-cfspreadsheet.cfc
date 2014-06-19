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

    <cffunction name="queryToExcel">
        <cfargument name="dir">
        <cfargument name="fileName">
        <cfargument name="query">

        <cfif not directoryExists(expandPath(dir))>
            <cfdirectory action="create" directory="#expandPath(dir)#">
        </cfif>

        <cfspreadsheet
                action="write"
                filename="#expandPath(dir)#/#fileName#"
                query="query"
                overwrite="true">
    </cffunction>


    <cffunction name="readExcelQuery">
        <cfargument name="dir">
        <cfargument name="fileName">
        <cfargument name="endRow">

        <cfspreadsheet
                action="read"
                src="#expandPath(dir)#/#fileName#"
                rows="1-#endRow#"
                query="myQuery"/>

        <cfreturn myQuery>
    </cffunction>


    <cffunction name="readExcel">
        <cfargument name="dir">
        <cfargument name="fileName">
        <cfargument name="format" default="html">

        <cfspreadsheet
                action="read"
                format="#format#"
                src="#expandPath(dir)#/#fileName#"
                name="myQuery"/>

        <cfreturn myQuery>
    </cffunction>


    <cffunction name="dump">
        <cfargument name="data">

        <cfsavecontent variable="dump">
            <cfdump var="#data#" expand="true"/>
        </cfsavecontent>

        <cfreturn dump>
    </cffunction>
</cfcomponent>