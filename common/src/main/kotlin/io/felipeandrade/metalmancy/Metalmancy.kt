package io.felipeandrade.metalmancy

import io.felipeandrade.metalmancy.platform.PlatformHelper
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Metalmancy {
    const val MOD_ID = "metalmancy"
    const val MOD_NAME: String = "Metalmancy"
    val LOG: Logger = LoggerFactory.getLogger(MOD_NAME)

    lateinit var helper: PlatformHelper
        private set

    @JvmStatic
    fun init(helper: PlatformHelper) {
        this.helper = helper
    }

    fun asResource(path: String): ResourceLocation = ResourceLocation.fromNamespaceAndPath(MOD_ID, path)

    fun <T> resourceKey(path: String, key: ResourceKey<Registry<T>>) =
        ResourceKey.create(key, asResource(path))
}
