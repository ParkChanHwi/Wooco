package com.odal.wooco.datamodels

class CategoryDataModel (
    val univs: List<String>? = null, // 대학
    val courses: List<String>? = null, // 진로/기타
    val interviews: List<String>? = null, // 면접
    val personals: Map<String, List<String>>? = null, // 자소서
    val certifications: List<String>? = null, // 자격증
    val transfer: Map<String, List<String>>? = null // 편입
)
