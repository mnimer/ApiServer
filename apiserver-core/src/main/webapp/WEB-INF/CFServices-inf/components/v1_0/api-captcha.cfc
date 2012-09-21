<cfcomponent>

    <cffunction name="generateCaptcha">
        <cfargument name="text"/>
        <cfargument name="difficulty"/>
        <cfargument name="width"/>
        <cfargument name="height"/>
        <cfargument name="debug" default="false" type="boolean"/>
        <cfargument name="fontSize" default="40" type="numeric"/>
        <cfargument name="fontFamily" default="Verdana,Arial,Courier New,Courier" type="string"/>


        <cfif arguments.debug>
            <cfdump var=#arguments# output="console">
        </cfif>

        <cfimage
                action="captcha"
                difficulty="#difficulty#"
                width="#width#"
                height="#height#"
                text="#text#"
                fontSize="#fontSize#"
                fonts="#fontFamily#"
                name="cfimage">

        <cfreturn cfimage/>

    </cffunction>


    <cffunction name="dump">
        <cfargument name="data">

        <cfsavecontent variable="dump">
            <cfdump var="#data#" expand="true"/>
        </cfsavecontent>

        <cfreturn dump>
    </cffunction>
</cfcomponent>