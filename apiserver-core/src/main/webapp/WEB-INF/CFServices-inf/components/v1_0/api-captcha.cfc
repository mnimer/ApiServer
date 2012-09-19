<cfcomponent>

    <cffunction name="generateCaptcha">
        <cfargument name="difficulty"/>
        <cfargument name="width"/>
        <cfargument name="height"/>
        <cfargument name="text"/>
        <cfargument name="fontSize" default="40"/>
        <cfargument name="fontFamily" default="Verdana,Arial,Courier New,Courier"/>

        <cfimage
                action="captcha"
                difficulty="#arguments.difficulty#"
                width="#arguments.width#"
                height="#arguments.height#"
                text="#arguments.text#"
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