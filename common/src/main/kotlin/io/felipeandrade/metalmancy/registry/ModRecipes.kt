package io.felipeandrade.metalmancy.registry

import dev.architectury.registry.registries.DeferredRegister
import dev.architectury.registry.registries.RegistrySupplier
import io.felipeandrade.metalmancy.Metalmancy
import io.felipeandrade.metalmancy.recipe.CalcinatingRecipe
import net.minecraft.core.registries.Registries
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.RecipeType

object ModRecipes {
    val RECIPE_SERIALIZERS: DeferredRegister<RecipeSerializer<*>> =
        DeferredRegister.create(Metalmancy.MOD_ID, Registries.RECIPE_SERIALIZER)
    val RECIPE_TYPES: DeferredRegister<RecipeType<*>> =
        DeferredRegister.create(Metalmancy.MOD_ID, Registries.RECIPE_TYPE)

    val CALCINATING_SERIALIZER: RegistrySupplier<RecipeSerializer<*>> =
        RECIPE_SERIALIZERS.register(CalcinatingRecipe.Serializer.ID) { CalcinatingRecipe.Serializer.INSTANCE }

    val CALCINATING_TYPE: RegistrySupplier<RecipeType<*>> =
        RECIPE_TYPES.register(CalcinatingRecipe.Type.ID) { CalcinatingRecipe.Type.INSTANCE }

    fun register() {
        RECIPE_SERIALIZERS.register()
        RECIPE_TYPES.register()
    }
}
