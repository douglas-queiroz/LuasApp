package com.douglas.luasapp.service.model

import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Xml

@Xml
data class TramResponse (

    @Attribute
    val dueMins: String?,

    @Attribute
    val destination: String?
)
