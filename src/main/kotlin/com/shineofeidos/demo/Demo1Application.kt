package com.shineofeidos.demo

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@MapperScan("com.shineofeidos.demo.mapper")
class Demo1Application

fun main(args: Array<String>) {
    runApplication<Demo1Application>(*args)
}
