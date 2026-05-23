package com.shineofeidos.demo.model

data class VideoFeedPageResult(
    val items: List<VideoFeedItemDto>,
    val nextCursor: Long?,
    val hasMore: Boolean,
)
