package com.douglas.luasapp.service.model

import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml

@Xml
data class DirectionResponse(

    @Attribute
    val name: String?,

    @Element(name = "tram")
    val tram: List<TramResponse>?
)
