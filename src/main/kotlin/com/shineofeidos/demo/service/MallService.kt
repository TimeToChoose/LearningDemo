package com.shineofeidos.demo.service

import com.shineofeidos.demo.exception.BusinessException
import com.shineofeidos.demo.mapper.MallProductMapper
import com.shineofeidos.demo.model.MallCategoryDto
import com.shineofeidos.demo.model.MallProduct
import com.shineofeidos.demo.model.ProductPageResult
import org.springframework.stereotype.Service
import kotlin.math.ceil

@Service
class MallService(
    private val mallProductMapper: MallProductMapper,
) {
    fun listCategories(): List<MallCategoryDto> = mallProductMapper.countCategories()

    fun listProducts(category: String?, page: Int, size: Int): ProductPageResult {
        val safePage = page.coerceAtLeast(1)
        val safeSize = size.coerceIn(1, 50)
        val offset = (safePage - 1) * safeSize
        val total = mallProductMapper.countByCategory(category?.ifBlank { null })
        val items = mallProductMapper.findPage(category?.ifBlank { null }, offset, safeSize)
        val totalPages = if (total == 0L) 0 else ceil(total.toDouble() / safeSize).toInt()
        return ProductPageResult(
            items = items,
            page = safePage,
            size = safeSize,
            total = total,
            totalPages = totalPages,
        )
    }

    fun getProduct(id: Long): MallProduct {
        return mallProductMapper.findById(id)
            ?: throw BusinessException(404, "Product not found: $id")
    }
}
