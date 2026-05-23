package com.shineofeidos.demo.mapper

import com.shineofeidos.demo.model.Item
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

@Mapper
interface ItemMapper {
    fun findAll(): List<Item>

    fun findById(@Param("id") id: Long): Item?

    fun insert(item: Item): Int

    fun update(item: Item): Int

    fun deleteById(@Param("id") id: Long): Int
}
