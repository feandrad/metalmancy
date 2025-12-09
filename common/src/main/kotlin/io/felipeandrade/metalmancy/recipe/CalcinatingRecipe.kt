package io.felipeandrade.metalmancy.recipe

// Add specific imports if star import doesn't cover them or they are in different packages
// RecipeBookCategory is in net.minecraft.world.item.crafting
// PlacementInfo is in net.minecraft.world.item.crafting
// But let's explicit import to be safe if they are recently moved
import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import io.felipeandrade.metalmancy.blocks.ModBlocks
import net.minecraft.core.HolderLookup
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.*
import net.minecraft.world.level.Level

class CalcinatingRecipe(
    val ingredient: Ingredient,
    val output: ItemStack,
    val essence: Long
) : Recipe<RecipeInput> {

    override fun matches(input: RecipeInput, level: Level): Boolean {
        return ingredient.test(input.getItem(0))
    }

    override fun assemble(input: RecipeInput, registries: HolderLookup.Provider): ItemStack {
        return output.copy()
    }

    fun canCraftInDimensions(width: Int, height: Int): Boolean = true
    fun getResultItem(registries: HolderLookup.Provider): ItemStack = output
    fun getToastSymbol(): ItemStack = ItemStack(ModBlocks.CALCINATOR)
    
    override fun isSpecial(): Boolean = true
    
    override fun recipeBookCategory(): RecipeBookCategory? = null
    
    override fun placementInfo(): PlacementInfo? = null

    override fun getSerializer(): RecipeSerializer<CalcinatingRecipe> = Serializer.INSTANCE

    override fun getType(): RecipeType<CalcinatingRecipe> = Type.INSTANCE

    class Type : RecipeType<CalcinatingRecipe> {
        companion object {
            val INSTANCE = Type()
            const val ID = "calcinating"
        }
        override fun toString(): String = ID
    }

    class Serializer : RecipeSerializer<CalcinatingRecipe> {
        companion object {
            val INSTANCE = Serializer()
            val ID = "calcinating"

            val CODEC: MapCodec<CalcinatingRecipe> = RecordCodecBuilder.mapCodec { instance ->
                instance.group(
                    Ingredient.CODEC.fieldOf("ingredient").forGetter { it.ingredient },
                    ItemStack.CODEC.fieldOf("output").forGetter { it.output },
                    Codec.LONG.fieldOf("essence").forGetter { it.essence }
                ).apply(instance, ::CalcinatingRecipe)
            }

            val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, CalcinatingRecipe> = StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC, CalcinatingRecipe::ingredient,
                ItemStack.STREAM_CODEC, CalcinatingRecipe::output,
                StreamCodec.of({ buf, v -> buf.writeLong(v) }, { buf -> buf.readLong() }), CalcinatingRecipe::essence,
                ::CalcinatingRecipe
            )
        }

        override fun codec(): MapCodec<CalcinatingRecipe> = CODEC

        override fun streamCodec(): StreamCodec<RegistryFriendlyByteBuf, CalcinatingRecipe> = STREAM_CODEC
    }
}
