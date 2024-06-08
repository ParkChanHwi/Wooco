package com.odal.wooco.datamodels

data class Consult(
    var mentiName: String? = null,
    var messages: List<Message> = emptyList(),
    var senderID: String? = null,
    var lastMessage: String? = null
)
