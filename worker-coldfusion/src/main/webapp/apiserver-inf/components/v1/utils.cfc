<cfcomponent>

    <cffunction name="isBase64">
        <cfargument name="str">

        <cftry>
            <cfset x = toBinary(str)>
            <cfreturn true>

            <cfcatch type="any">
                <cfreturn false>
            </cfcatch>
        </cftry>
    </cffunction>

    <cffunction name="transformMapToStruct">
        <cfargument name="options">

        <cfif not isDefined("options")>
            <cfset options = structNew()>
        </cfif>

        <cfset _options = structNew()>
        <cfloop item="key" collection="#options#">
            <cfset _options[key] = options[key]>
        </cfloop>

        <cfreturn _options>
    </cffunction>

</cfcomponent>