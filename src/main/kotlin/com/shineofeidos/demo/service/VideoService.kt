package com.shineofeidos.demo.service

import com.shineofeidos.demo.exception.BusinessException
import com.shineofeidos.demo.mapper.VideoFeedMapper
import com.shineofeidos.demo.model.VideoAuthorDto
import com.shineofeidos.demo.model.VideoFeedItemDto
import com.shineofeidos.demo.model.VideoFeedPageResult
import org.springframework.stereotype.Service

@Service
class VideoService(
    private val videoFeedMapper: VideoFeedMapper,
    private val userService: UserService,
) {
    private val allowedTabs = setOf("recommend", "follow", "nearby")

    fun getFeed(tab: String, cursor: Long?, limit: Int): VideoFeedPageResult {
        if (tab !in allowedTabs) {
            throw BusinessException(400, "Invalid tab: $tab. Allowed: recommend, follow, nearby")
        }
        val safeLimit = limit.coerceIn(1, 20)
        val fetchSize = safeLimit + 1
        val rows = videoFeedMapper.findFeedPage(tab, cursor, fetchSize)
        val hasMore = rows.size > safeLimit
        val pageRows = if (hasMore) rows.dropLast(1) else rows
        val items = pageRows.map { row ->
            val author = userService.findById(row.authorId)
            VideoFeedItemDto(
                id = row.id!!,
                tab = row.tab,
                title = row.title,
                coverUrl = row.coverUrl,
                videoUrl = row.videoUrl,
                author = VideoAuthorDto(
                    id = row.authorId,
                    nickname = author?.nickname ?: "Unknown",
                    avatar = author?.avatar ?: "",
                ),
                likeCount = row.likeCount,
            )
        }
        val nextCursor = if (hasMore && pageRows.isNotEmpty()) pageRows.last().id else null
        return VideoFeedPageResult(
            items = items,
            nextCursor = nextCursor,
            hasMore = hasMore,
        )
    }
}
