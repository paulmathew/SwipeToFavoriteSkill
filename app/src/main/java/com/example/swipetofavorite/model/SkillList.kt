package com.example.swipetofavorite.model

data class SkillList(
    val isNextPage: Boolean?=null,
    val message: Any?=null,
    val skills: List<Skill>?=null,
    val success: Boolean?=null,
    val topicHeader: TopicHeader?=null
)