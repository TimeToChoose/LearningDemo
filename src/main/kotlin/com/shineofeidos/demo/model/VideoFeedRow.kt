package com.shineofeidos.demo.model

import java.time.LocalDateTime

data class VideoFeedRow(
    val id: Long? = null,
    val tab: String,
    val title: String,
    val coverUrl: String,
    val videoUrl: String,
    val authorId: Long,
    val likeCount: Int,
    val createdAt: LocalDateTime? = null,
)
