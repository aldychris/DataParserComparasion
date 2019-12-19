package com.github.aldychris.compare

import kotlin.reflect.full.memberProperties

/**
 *
 * This extension function only for convert single JsonObject,
 * Im not tested with nested json object or json array
*/

inline fun <reified T : Any> T.asMap() : Map<String, Any?> {
    if(T::class.isData) {
        val props = T::class.memberProperties.associateBy { it.name }
        return props.keys.associateWith { props[it]?.get(this) }
    } else {
        throw Exception("Not data class")
    }
}

fun String.toMap() : Map<String, Any?> {
    return try{
        return Regex("(\\w+)=(.*?(?=,\\s*\\w+=|\\s*\$))")
                .findAll(removeSurrounding("{","}"))
                .let {
                    it.associate {matchRes ->
                        (matchRes.groupValues[1] to matchRes.groupValues[2])
                    }
                }
    } catch (ex: IndexOutOfBoundsException){
        mapOf()
    }
}
