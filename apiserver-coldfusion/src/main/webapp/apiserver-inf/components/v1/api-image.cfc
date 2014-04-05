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

<cfcomponent output="false">

    <cffunction name="imageInfo" access="remote" returntype="any" returnformat="plain">
        <cfargument name="image" />

        <cfimage
                action="info"
                source="#arguments.image#"
                structname="imageInfo">

        <cfset results = structNew()>
        <cfset results['width'] = imageInfo.width>
        <cfset results['height'] = imageInfo.height>

        <cfreturn toBase64(results)/>
    </cffunction>


    <cffunction name="imageMetadata" access="remote" returntype="any" returnformat="plain">
        <cfargument name="image"/>
        <cfimage
                action="read"
                source="#arguments.image#"
                name="cfimage">

        <cfreturn toBase64(cfimage)/>
    </cffunction>


    <cffunction name="imageConvert" access="remote" returnformat="json" returntype="Binary">
        <cfthrow detail="Not Implemented Yet"/>
    </cffunction>


    <cffunction name="rotateImage" access="remote" returnformat="json">
        <cfargument name="image"/>
        <cfargument name="angle" default="90"/>

        <cfimage
                action="rotate"
                angle="#arguments.angle#"
                source="#arguments.image#"
                name="cfimage">

        <cfreturn toBase64( cfimage )/>
    </cffunction>


    <cffunction name="resizeImage" access="remote" returntype="any" returnformat="plain" >
        <cfargument name="image"/>
        <cfargument name="width" default="1024"/>
        <cfargument name="height" default="768"/>
        <cfargument name="interpolation" default="bicubic"/>
        <cfargument name="scaleToFit" default="false"/>

        <cfimage
                action="resize"
                width="#arguments.width#"
                height="#arguments.height#"
                source="#arguments.image#"
                interpolation="#arguments.interpolation#"
                name="cfimage">

        <cfreturn toBase64( cfimage ) />
    </cffunction>


    <cffunction name="addBorder" access="remote" returntype="any" returnformat="plain">
        <cfargument name="image"/>
        <cfargument name="color" default="1024"/>
        <cfargument name="thickness" default="10"/>

        <cfimage
                action="border"
                color="#arguments.color#"
                thickness="#arguments.thickness#"
                source="#arguments.image#"
                name="cfimage">

        <cfreturn toBase64(cfimage)/>
    </cffunction>


    <cffunction name="addText" access="remote" returntype="any" returnformat="plain">
        <cfargument name="image"/>
        <cfargument name="text"/>
        <cfargument name="color"/>
        <cfargument name="fontSize"/>
        <cfargument name="fontStyle"/>
        <cfargument name="angle" required="false"/>
        <cfargument name="x"/>
        <cfargument name="y"/>

        <CFIF arguments.angle gt 0>
            <cfthrow detail="Not Implemented Yet"/>
        </cfif>

        <cfset myImage = ImageNew(expandPath(arguments.image))>

        <cfset ImageSetDrawingColor(myImage, arguments.color)>
        <cfset attr = StructNew()>
        <cfset attr.size = arguments.fontSize>
        <cfset attr.style = arguments.fontStyle>
        <cfset ImageDrawText(myImage, text, arguments.x, arguments.y, attr)>

        <cfreturn toBase64(myImage)/>
    </cffunction>


    <cffunction name="generateCaptcha" access="remote" returntype="any" returnformat="plain">
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

        <cfreturn toBase64(cfimage)/>
    </cffunction>


    <cffunction name="dump" access="remote" returnformat="json" returntype="String">
        <cfargument name="data">

        <cfsavecontent variable="dump">
            <cfdump var="#data#" expand="true"/>
        </cfsavecontent>

        <cfreturn dump>
    </cffunction>
</cfcomponent>