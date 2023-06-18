package com.example.hannapp.ui.viewmodel

interface ReferenceValidationStrategy {
    fun validate(value: String, reference: Reference)
}

class InvalidReferencesList(private val references: MutableList<Reference>) : ReferenceValidationStrategy {
    override fun validate(value: String, reference: Reference) {
        if (value.toFloatOrNull() == null) references.add(reference)
    }
}
