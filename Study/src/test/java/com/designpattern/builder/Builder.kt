package com.designpattern.builder

interface Builder {
    fun setName(name: String?): Builder
    fun setType(type: String?): Builder
    fun build(): Product
}