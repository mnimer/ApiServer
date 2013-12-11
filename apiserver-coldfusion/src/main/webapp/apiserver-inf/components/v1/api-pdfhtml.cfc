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

    <cffunction name="htmlToPdf">
        <cfargument name="html" type="string"/>

        <!--cfdump var="#html#" output="console"/-->

        <cfdocument name="pdf" format="pdfs">
            <cfoutput>#arguments.html#</cfoutput>
        </cfdocument>

        <cfset results = structNew()>
        <cfset results['pdfs'] = pdf>
        <cfreturn results>
    </cffunction>


    <cffunction name="artistList">
        <cfargument name="artistQuery"/>

        <cfdocument name="pdf" format="pdfs" bookmark="true">
            <cfoutput>
                <cfdocumentitem type="header">
                    Current Page Number: #cfdocument.currentpagenumber#
                </cfdocumentitem>

                <cfdocumentsection name="Artist List">
                    <h4 style="color:DarkCyan; font-style:italic">
                        Artist
                    </h4>

                    <cftable query="artistQuery" colheaders="yes" htmltable="no" border="no">
                        <cfcol header="Artist ID" text="#ArtistId#">
                        <cfcol header="Name" text="#Name#">
                    </cftable>
                </cfdocumentsection>

            </cfoutput>
        </cfdocument>

        <cfset results = structNew()>
        <cfset results['pdfs'] = pdf>
        <cfreturn results>
    </cffunction>

</cfcomponent>