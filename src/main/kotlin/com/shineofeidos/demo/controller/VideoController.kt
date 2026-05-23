package com.shineofeidos.demo.controller

import com.shineofeidos.demo.auth.PublicApi
import com.shineofeidos.demo.model.ApiResponse
import com.shineofeidos.demo.model.VideoFeedPageResult
import com.shineofeidos.demo.service.VideoService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Video", description = "短视频流（公开接口）")
@RestController
@RequestMapping("/api/videos")
@PublicApi
class VideoController(
    private val videoService: VideoService,
) {
    @Operation(summary = "短视频 Feed", description = "tab: recommend | follow | nearby，cursor 为上一页最后一条 id")
    @GetMapping("/feed")
    fun feed(
        @RequestParam tab: String,
        @RequestParam(required = false) cursor: Long?,
        @RequestParam(defaultValue = "10") limit: Int,
    ): ApiResponse<VideoFeedPageResult> =
        ApiResponse.ok(videoService.getFeed(tab, cursor, limit))
}
