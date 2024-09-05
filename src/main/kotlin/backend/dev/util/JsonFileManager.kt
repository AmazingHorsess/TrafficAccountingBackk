package backend.dev.util

import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.io.path.writeText

object JsonFileManager: JsonFileManagerContract {
    override fun readJsonFromFile(fileName: String): String {
        return Path(fileName).readText(Charsets.UTF_8)
    }

    override fun writeJsonToFile(fileName: String, jsonData: String) {
        Path(fileName).writeText(jsonData, Charsets.UTF_8)
    }


}

interface JsonFileManagerContract {
    fun readJsonFromFile(fileName:String):String
    fun writeJsonToFile(fileName: String, jsonData: String)
}