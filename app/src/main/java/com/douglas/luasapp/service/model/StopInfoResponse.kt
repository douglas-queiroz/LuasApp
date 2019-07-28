package com.douglas.luasapp.service.model

import com.douglas.luasapp.service.helper.DateConverter
import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import java.util.*

@Xml
data class StopInfoResponse(

    @Attribute(converter = DateConverter::class)
    val created: Date?,

    @Attribute
    val stop: String,

    @Attribute
    val stopAbv: String?,

    @PropertyElement
    val message: String?,

    @Element(name = "direction")
    val direction: List<DirectionResponse>?
)