package com.odal.wooco.datamodels

data class CategoryDataModel(
    val univs: List<String>? = null, // 대학
    val course: List<String>? = null, // 진로/기타
    val interview: List<String>? = null, // 면접
    val personals: Map<String, List<String>>? = null, // 자소서
    val certification: List<String>? = null, // 자격증
    val transfer: Map<String, List<String>>? = null // 편입 (없으면 제거)
)
