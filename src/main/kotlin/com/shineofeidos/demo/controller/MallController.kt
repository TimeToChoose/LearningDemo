package com.shineofeidos.demo.controller

import com.shineofeidos.demo.auth.PublicApi
import com.shineofeidos.demo.model.ApiResponse
import com.shineofeidos.demo.model.MallCategoryDto
import com.shineofeidos.demo.model.MallProduct
import com.shineofeidos.demo.model.ProductPageResult
import com.shineofeidos.demo.service.MallService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Mall", description = "商城（公开接口）")
@RestController
@RequestMapping("/api/mall")
@PublicApi
class MallController(
    private val mallService: MallService,
) {
    @Operation(summary = "商品分类列表")
    @GetMapping("/categories")
    fun categories(): ApiResponse<List<MallCategoryDto>> =
        ApiResponse.ok(mallService.listCategories())

    @Operation(summary = "商品分页列表")
    @GetMapping("/products")
    fun products(
        @RequestParam(required = false) category: String?,
        @RequestParam(defaultValue = "1") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
    ): ApiResponse<ProductPageResult> =
        ApiResponse.ok(mallService.listProducts(category, page, size))

    @Operation(summary = "商品详情")
    @GetMapping("/products/{id}")
    fun product(@PathVariable id: Long): ApiResponse<MallProduct> =
        ApiResponse.ok(mallService.getProduct(id))
}
