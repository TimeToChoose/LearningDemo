package com.shineofeidos.demo.service

import com.shineofeidos.demo.mapper.MessageMapper
import com.shineofeidos.demo.model.DemoMessage
import org.springframework.stereotype.Service

@Service
class MessageService(
    private val messageMapper: MessageMapper,
) {
    fun listRecent(limit: Int = 20): List<DemoMessage> =
        messageMapper.findRecent(limit.coerceIn(1, 100))

    fun save(message: String): DemoMessage {
        val entity = DemoMessage(message = message)
        messageMapper.insert(entity)
        return messageMapper.findById(entity.id!!)!!
    }
}
