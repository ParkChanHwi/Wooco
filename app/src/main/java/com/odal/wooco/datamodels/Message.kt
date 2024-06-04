package com.odal.wooco.datamodels

data class Message(
    var message: String?,
    var sendId: String?
){
    constructor():this("","")
}
