package caios.android.fanbox.core.logs.category

import kotlinx.serialization.json.JsonObject

interface LogCategory {
    val properties: JsonObject
}
