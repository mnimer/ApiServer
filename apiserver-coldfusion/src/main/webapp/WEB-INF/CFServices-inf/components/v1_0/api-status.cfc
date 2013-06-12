<cfcomponent>

    <cffunction name="health" access="remote">
        <cfset view = StructNew()>
        <cfset view['coldfusion']['status'] = "ok">
        <cfset view['coldfusion']['timestamp'] = now()>
        <cfset view['coldfusion']['server']['productversion'] = server.get("coldfusion").get("productversion")>
        <cfreturn view>
    </cffunction>

</cfcomponent>