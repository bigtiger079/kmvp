package com.bigtiger.kmvp.utils

import android.support.annotation.Nullable

object Preconditions {
    fun checkArgument(expression: Boolean) {
        if (!expression) {
            throw IllegalArgumentException()
        }
    }

    fun checkArgument(expression: Boolean, @Nullable errorMessage: Any) {
        if (!expression) {
            throw IllegalArgumentException(errorMessage.toString())
        }
    }

    fun checkArgument(expression: Boolean, @Nullable errorMessageTemplate: String, @Nullable vararg errorMessageArgs: Any) {
        if (!expression) {
            throw IllegalArgumentException(format(errorMessageTemplate, *errorMessageArgs))
        }
    }

    fun checkState(expression: Boolean) {
        if (!expression) {
            throw IllegalStateException()
        }
    }

    fun checkState(expression: Boolean, @Nullable errorMessage: Any) {
        if (!expression) {
            throw IllegalStateException(errorMessage.toString())
        }
    }

    fun checkState(expression: Boolean, @Nullable errorMessageTemplate: String, @Nullable vararg errorMessageArgs: Any) {
        if (!expression) {
            throw IllegalStateException(format(errorMessageTemplate, *errorMessageArgs))
        }
    }

    fun <T> checkNotNull(reference: T?): T {
        return if (reference == null) {
            throw NullPointerException()
        } else {
            reference
        }
    }

    fun <T> checkNotNull(reference: T?, @Nullable errorMessage: Any): T {
        return reference ?: throw NullPointerException(errorMessage.toString())
    }

    fun <T> checkNotNull(reference: T?, @Nullable errorMessageTemplate: String, @Nullable vararg errorMessageArgs: Any): T {
        return reference
                ?: throw NullPointerException(format(errorMessageTemplate, *errorMessageArgs))
    }

    fun checkElementIndex(index: Int, size: Int): Int {
        return checkElementIndex(index, size, "index")
    }

    fun checkElementIndex(index: Int, size: Int, @Nullable desc: String): Int {
        return if (index >= 0 && index < size) {
            index
        } else {
            throw IndexOutOfBoundsException(badElementIndex(index, size, desc))
        }
    }

    private fun badElementIndex(index: Int, size: Int, desc: String): String {
        return if (index < 0) {
            format("%s (%s) must not be negative", *arrayOf(desc, Integer.valueOf(index)))
        } else if (size < 0) {
            throw IllegalArgumentException(StringBuilder(26).append("negative size: ").append(size).toString())
        } else {
            format("%s (%s) must be less than size (%s)", *arrayOf(desc, Integer.valueOf(index), Integer.valueOf(size)))
        }
    }

    fun checkPositionIndex(index: Int, size: Int): Int {
        return checkPositionIndex(index, size, "index")
    }

    fun checkPositionIndex(index: Int, size: Int, @Nullable desc: String): Int {
        return if (index >= 0 && index <= size) {
            index
        } else {
            throw IndexOutOfBoundsException(badPositionIndex(index, size, desc))
        }
    }

    private fun badPositionIndex(index: Int, size: Int, desc: String): String {
        return if (index < 0) {
            format("%s (%s) must not be negative", *arrayOf(desc, Integer.valueOf(index)))
        } else if (size < 0) {
            throw IllegalArgumentException(StringBuilder(26).append("negative size: ").append(size).toString())
        } else {
            format("%s (%s) must not be greater than size (%s)", *arrayOf(desc, Integer.valueOf(index), Integer.valueOf(size)))
        }
    }

    fun checkPositionIndexes(start: Int, end: Int, size: Int) {
        if (start < 0 || end < start || end > size) {
            throw IndexOutOfBoundsException(badPositionIndexes(start, end, size))
        }
    }

    private fun badPositionIndexes(start: Int, end: Int, size: Int): String {
        return if (start in 0..size) if (end in 0..size) format("end index (%s) must not be less than start index (%s)", *arrayOf<Any>(Integer.valueOf(end), Integer.valueOf(start))) else badPositionIndex(end, size, "end index") else badPositionIndex(start, size, "start index")
    }

    fun format(template: String, @Nullable vararg args: Any): String {
        var temp:String= template
        val builder = StringBuilder(template.length + 16 * args.size)
        var templateStart = 0

        var i = 0
        var placeholderStart = 0
        while (i < args.size) {
            placeholderStart = temp.indexOf("%s", templateStart)
            if (placeholderStart == -1) {
                break
            }

            builder.append(temp.substring(templateStart, placeholderStart))
            builder.append(args[i++])
            templateStart = placeholderStart + 2
        }
        builder.append(temp.substring(templateStart))
        if (i < args.size) {
            builder.append(" [")
            builder.append(args[i++])

            while (i < args.size) {
                builder.append(", ")
                builder.append(args[i++])
            }

            builder.append(']')
        }

        return builder.toString()
    }
}