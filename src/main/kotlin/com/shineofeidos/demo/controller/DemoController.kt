package com.shineofeidos.demo.controller

import com.shineofeidos.demo.model.ApiResponse
import com.shineofeidos.demo.model.DemoMessage
import com.shineofeidos.demo.model.EchoRequest
import com.shineofeidos.demo.model.Item
import com.shineofeidos.demo.model.ItemCreateRequest
import com.shineofeidos.demo.model.ItemUpdateRequest
import com.shineofeidos.demo.service.ItemService
import com.shineofeidos.demo.service.MessageService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
@RequestMapping("/api")
class DemoController(
    private val itemService: ItemService,
    private val messageService: MessageService,
) {

    @GetMapping("/health")
    fun health(): ApiResponse<Map<String, Any>> =
        ApiResponse.ok(
            mapOf(
                "status" to "UP",
                "timestamp" to Instant.now().toString(),
            ),
        )

    @GetMapping("/hello")
    fun hello(@RequestParam(defaultValue = "Android") name: String): ApiResponse<String> =
        ApiResponse.ok("Hello, $name!")

    @GetMapping("/items")
    fun listItems(): ApiResponse<List<Item>> =
        ApiResponse.ok(itemService.list())

    @GetMapping("/items/{id}")
    fun getItem(@PathVariable id: Long): ApiResponse<Item> {
        val item = itemService.getById(id)
            ?: return ApiResponse.fail(404, "Item not found: $id")
        return ApiResponse.ok(item)
    }

    @PostMapping("/items")
    fun createItem(@Valid @RequestBody request: ItemCreateRequest): ApiResponse<Item> =
        ApiResponse.ok(itemService.create(request))

    @PutMapping("/items/{id}")
    fun updateItem(
        @PathVariable id: Long,
        @Valid @RequestBody request: ItemUpdateRequest,
    ): ApiResponse<Item> {
        val item = itemService.update(id, request)
            ?: return ApiResponse.fail(404, "Item not found: $id")
        return ApiResponse.ok(item)
    }

    @DeleteMapping("/items/{id}")
    fun deleteItem(@PathVariable id: Long): ApiResponse<Map<String, Long>> {
        if (!itemService.delete(id)) {
            return ApiResponse.fail(404, "Item not found: $id")
        }
        return ApiResponse.ok(mapOf("id" to id))
    }

    @PostMapping("/echo")
    fun echo(@Valid @RequestBody request: EchoRequest): ApiResponse<DemoMessage> =
        ApiResponse.ok(messageService.save(request.message))

    @GetMapping("/messages")
    fun listMessages(@RequestParam(defaultValue = "20") limit: Int): ApiResponse<List<DemoMessage>> =
        ApiResponse.ok(messageService.listRecent(limit))
}
