package dev.rodrick.acer

import me.shedaniel.autoconfig.AutoConfig
import me.shedaniel.autoconfig.ConfigData
import me.shedaniel.autoconfig.annotation.Config
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer

@Config(name = AcerMod.MOD_ID)
object AcerConfig : ConfigData {
    fun init() {
        AutoConfig.register(this.javaClass,::JanksonConfigSerializer)
    }
}