package com.shineofeidos.demo.mapper

import com.shineofeidos.demo.model.DemoMessage
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

@Mapper
interface MessageMapper {
    fun findById(@Param("id") id: Long): DemoMessage?

    fun findRecent(@Param("limit") limit: Int): List<DemoMessage>

    fun insert(message: DemoMessage): Int
}
