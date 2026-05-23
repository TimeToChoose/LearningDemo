package com.shineofeidos.demo.mapper

import com.shineofeidos.demo.model.VideoFeedRow
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

@Mapper
interface VideoFeedMapper {
    fun findFeedPage(
        @Param("tab") tab: String,
        @Param("cursor") cursor: Long?,
        @Param("limit") limit: Int,
    ): List<VideoFeedRow>
}
