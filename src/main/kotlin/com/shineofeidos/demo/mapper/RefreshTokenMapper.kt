package com.shineofeidos.demo.mapper

import com.shineofeidos.demo.model.RefreshTokenRecord
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

@Mapper
interface RefreshTokenMapper {
    fun insert(record: RefreshTokenRecord): Int

    fun findValidByHash(@Param("tokenHash") tokenHash: String): RefreshTokenRecord?

    fun revokeByHash(@Param("tokenHash") tokenHash: String): Int

    fun revokeAllByUserId(@Param("userId") userId: Long): Int
}
