package com.shineofeidos.demo.model

data class VideoFeedItemDto(
    val id: Long,
    val tab: String,
    val title: String,
    val coverUrl: String,
    val videoUrl: String,
    val author: VideoAuthorDto,
    val likeCount: Int,
)
