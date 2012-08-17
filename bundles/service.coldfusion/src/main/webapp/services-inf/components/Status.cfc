<cfcomponent>

    <!---
        Simple ping method, to validate the health of the server
    --->
    <cffunction name="ping" access="remote">
        <cfset status = structNew()>
        <cfset status['status'] = "ok">
        <cfset status['timestamp'] = now()>
        <cfreturn status>
    </cffunction>
</cfcomponent>