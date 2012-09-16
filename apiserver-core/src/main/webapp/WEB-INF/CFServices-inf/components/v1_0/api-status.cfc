<cfcomponent extends="WEB-INF.cfservices-inf.components.v1_0.debug">

    <cffunction name="ping" access="remote">
        <cfset view = StructNew()>
        <cfset view['status'] = "ok">
        <cfset view['timestamp'] = now()>
        <cfset view['server'] = server>
        <cfreturn view>
    </cffunction>

</cfcomponent>