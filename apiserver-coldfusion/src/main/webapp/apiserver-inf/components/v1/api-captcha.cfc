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