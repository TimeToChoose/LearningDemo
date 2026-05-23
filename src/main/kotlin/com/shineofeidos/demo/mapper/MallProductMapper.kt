package com.shineofeidos.demo.mapper

import com.shineofeidos.demo.model.MallCategoryDto
import com.shineofeidos.demo.model.MallProduct
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

@Mapper
interface MallProductMapper {
    fun countByCategory(@Param("category") category: String?): Long

    fun findPage(
        @Param("category") category: String?,
        @Param("offset") offset: Int,
        @Param("limit") limit: Int,
    ): List<MallProduct>

    fun findById(@Param("id") id: Long): MallProduct?

    fun countCategories(): List<MallCategoryDto>
}
