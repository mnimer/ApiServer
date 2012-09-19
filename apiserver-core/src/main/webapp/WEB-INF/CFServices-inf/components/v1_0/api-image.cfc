<cfcomponent>

    <cffunction name="imageInfo">
        <cfargument name="image"/>

        <cfimage
                action="info"
                source="#expandPath(arguments.image)#"
                structname="cfimage">

        <cfreturn cfimage/>
    </cffunction>


    <cffunction name="imageMetadata">
        <cfthrow detail="Not Implemented Yet"/>
    </cffunction>


    <cffunction name="imageConvert">
        <cfthrow detail="Not Implemented Yet"/>
    </cffunction>


    <cffunction name="rotateImage">
        <cfargument name="image"/>
        <cfargument name="angle" default="90"/>

        <cfimage
                action="rotate"
                angle="#arguments.angle#"
                source="#expandPath(arguments.image)#"
                name="cfimage">

        <cfreturn cfimage/>
    </cffunction>


    <cffunction name="resizeImage">
        <cfargument name="image"/>
        <cfargument name="interpolation" default="bicubic"/>
        <cfargument name="width" default="1024"/>
        <cfargument name="height" default="768"/>
        <cfargument name="scaleToFit" default="false"/>

        <CFIF arguments.scaleToFit>
            <cfthrow detail="Not Implemented Yet"/>
            <!--- todo: use ImageScaleFit method --->
        </cfif>

        <cfimage
                action="resize"
                width="#arguments.width#"
                height="#arguments.height#"
                source="#expandPath(arguments.image)#"
                interpolation="#arguments.interpolation#"
                name="cfimage">

        <cfreturn cfimage/>
    </cffunction>


    <cffunction name="addBorder">
        <cfargument name="image"/>
        <cfargument name="color" default="1024"/>
        <cfargument name="thickness" default="10"/>

        <cfimage
                action="border"
                color="#arguments.color#"
                thickness="#arguments.thickness#"
                source="#expandPath(arguments.image)#"
                name="cfimage">

        <cfreturn cfimage/>
    </cffunction>



    <cffunction name="addText">
        <cfargument name="image"/>
        <cfargument name="text"/>
        <cfargument name="color" />
        <cfargument name="fontSize" />
        <cfargument name="fontStyle" />
        <cfargument name="angle" required="false" />
        <cfargument name="x" />
        <cfargument name="y" />


        <CFIF arguments.angle gt 0>
            <cfthrow detail="Not Implemented Yet"/>
        </cfif>


        <cfset myImage = ImageNew(expandPath(arguments.image))>

        <cfset ImageSetDrawingColor(myImage, arguments.color)>
        <cfset attr=StructNew()>
        <cfset attr.size=arguments.fontSize>
        <cfset attr.style=arguments.fontStyle>
        <cfset ImageDrawText(myImage, text, arguments.x, arguments.y, attr)>


        <cfreturn myImage/>
    </cffunction>



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