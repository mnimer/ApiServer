<cfcomponent>

    <cffunction name="htmlToPdf">
        <cfargument name="html" type="string"/>

        <!--cfdump var="#html#" output="console"/-->

        <!---cfset fileName =  "#getTempDirectory()#/#createUUID()#.pdfs"--->

        <cfdocument name="pdf" format="pdfs" >
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