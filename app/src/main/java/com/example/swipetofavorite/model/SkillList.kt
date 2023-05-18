package com.example.swipetofavorite.model

data class SkillList(
    val isNextPage: Boolean?=null,
    val message: Any?=null,
    var skills: ArrayList<Skill>,
    val success: Boolean?=null,
    val topicHeader: TopicHeader?=null,
    val code: Int?=null,
    val errors: List<Error>?=null
)