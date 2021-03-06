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

    <cffunction name= 'mergePdf'>
        <cfargument name="file1">
        <cfargument name="file2">
        <cfargument name="options">

    </cffunction>

    <cffunction name= 'optimizePdf'>
        <cfargument name="file">
        <cfargument name="options">

    </cffunction>

    <cffunction name= 'processDDX'>
        <cfargument name="file">
        <cfargument name="ddx">

    </cffunction>

    <cffunction name= 'addFooter'>
        <cfargument name="file">
        <cfargument name="options">

           <!--- check for image in options, and deserialize base64 --->
    </cffunction>

    <cffunction name= 'addHeader'>
        <cfargument name="file">
        <cfargument name="options">

<!--- check for image in options, and deserialize base64 --->
    </cffunction>

    <cffunction name= 'pdfInfo'>
        <cfargument name="file">
        <cfargument name="options">

    </cffunction>

    <cffunction name= 'deletePdfPages'>
        <cfargument name="file">
        <cfargument name="options">

    </cffunction>

    <cffunction name= 'securePdf'>
        <cfargument name="file">
        <cfargument name="options">

    </cffunction>

    <cffunction name= 'transformPdf'>
        <cfargument name="file">
        <cfargument name="options">

    </cffunction>
</cfcomponent>