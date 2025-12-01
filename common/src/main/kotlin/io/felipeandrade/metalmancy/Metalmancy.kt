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
        
        io.felipeandrade.metalmancy.registry.ModItems.register()
        io.felipeandrade.metalmancy.registry.ModBlocks.register()
        io.felipeandrade.metalmancy.registry.ModBlockEntities.register()
        io.felipeandrade.metalmancy.registry.ModMenus.register()
        io.felipeandrade.metalmancy.registry.ModRecipes.register()
        io.felipeandrade.metalmancy.fluid.ModFluids.register()
        io.felipeandrade.metalmancy.network.ModNetwork.register()
    }

    fun asResource(path: String): ResourceLocation = ResourceLocation.fromNamespaceAndPath(MOD_ID, path)

    fun <T> resourceKey(path: String, key: ResourceKey<Registry<T>>) =
        ResourceKey.create(key, asResource(path))

    /**
     * Constructs a standardized unlocalized name (resource ID) by joining all non-blank
     * parts with an underscore, first converting any internal spaces within the parts to underscores.
     * * @param parts A variable number of strings (vararg) that form the name components.
     * @return The finalized name string (e.g., "deepslate_uranium_ore").
     */
    fun unlocalizedName(vararg parts: String): String = parts
        .filter { it.isNotBlank() }
        .joinToString(separator = "_") { it.replace(' ', '_') }
}
