package com.shineofeidos.demo.service

import com.shineofeidos.demo.mapper.ItemMapper
import com.shineofeidos.demo.model.Item
import com.shineofeidos.demo.model.ItemCreateRequest
import com.shineofeidos.demo.model.ItemUpdateRequest
import org.springframework.stereotype.Service

@Service
class ItemService(
    private val itemMapper: ItemMapper,
) {
    fun list(): List<Item> = itemMapper.findAll()

    fun getById(id: Long): Item? = itemMapper.findById(id)

    fun create(request: ItemCreateRequest): Item {
        val item = Item(name = request.name, category = request.category)
        itemMapper.insert(item)
        return itemMapper.findById(item.id!!)!!
    }

    fun update(id: Long, request: ItemUpdateRequest): Item? {
        val existing = itemMapper.findById(id) ?: return null
        val updated = existing.copy(name = request.name, category = request.category)
        itemMapper.update(updated)
        return itemMapper.findById(id)
    }

    fun delete(id: Long): Boolean = itemMapper.deleteById(id) > 0
}
