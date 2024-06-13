package com.example.thebotanyapp.domain.usecase.validator

class FieldsValidatorUseCase {
    fun validateFields(vararg fields: String): Boolean {
        return fields.all { it.isNotEmpty() }
    }
}