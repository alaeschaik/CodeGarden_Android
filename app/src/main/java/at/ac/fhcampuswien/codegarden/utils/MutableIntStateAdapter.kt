package at.ac.fhcampuswien.codegarden.utils

import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.io.IOException

class MutableIntStateAdapter : TypeAdapter<MutableIntState>() {
    @Throws(IOException::class)
    override fun write(out: JsonWriter, value: MutableIntState) {
        out.value(value.intValue)
    }

    @Throws(IOException::class)
    override fun read(`in`: JsonReader): MutableIntState {
        return mutableIntStateOf(`in`.nextInt())
    }
}