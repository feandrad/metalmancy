package io.felipeandrade.metalmancy.recipe

import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import io.felipeandrade.metalmancy.blocks.ModBlocks
import io.felipeandrade.metalmancy.blocks.entity.CalcinatorBlockEntity.Companion.INPUT_SLOT
import io.felipeandrade.metalmancy.items.ModItems
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.recipe.*
import net.minecraft.registry.DynamicRegistryManager
import net.minecraft.util.Identifier
import net.minecraft.util.collection.DefaultedList
import net.minecraft.world.World


class CalcinatingRecipe(
    val ingredient: Ingredient,
    var output: ItemStack,
    val essence: Long
) : Recipe<SimpleInventory> {

    // 1 Essence Bottle = 27000 droplets = 60 xp
    // 1 Bucket = 81000 droplet = 3 * 60 xp
    // 1 Random Item = ? droplets = 5 xp

    override fun matches(inventory: SimpleInventory, world: World): Boolean =
        ingredient.test(inventory.getStack(INPUT_SLOT))

    override fun craft(inventory: SimpleInventory, registryManager: DynamicRegistryManager): ItemStack = output

    override fun fits(width: Int, height: Int): Boolean = true

    override fun getResult(registryManager: DynamicRegistryManager?): ItemStack = output

    override fun getIngredients(): DefaultedList<Ingredient> = DefaultedList.ofSize<Ingredient>(1)
        .apply { add(ingredient) }

    override fun createIcon(): ItemStack = ItemStack(ModBlocks.CALCINATOR)

    override fun getSerializer(): RecipeSerializer<*> = Serializer()

    override fun getType(): RecipeType<*> = TYPE

    companion object {
        const val RECIPE_ID = "calcinating"

        val TYPE = object : RecipeType<CalcinatingRecipe> {
            val ID = RECIPE_ID
        }

        fun defaultRecipe(itemStack: ItemStack): RecipeEntry<CalcinatingRecipe> {
            val ingredient = Ingredient.ofStacks(itemStack)
            return RecipeEntry(
                Identifier(RECIPE_ID),
                CalcinatingRecipe(ingredient, ItemStack(ModItems.ESSENCE_DUST), 5f.toDroplets())
            )
        }
    }

    class Serializer : RecipeSerializer<CalcinatingRecipe> {

        val CODEC: Codec<CalcinatingRecipe> = RecordCodecBuilder.create { builder ->
            builder.group(
                (Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("ingredient") as MapCodec).forGetter{it.ingredient},
                ItemStack.RECIPE_RESULT_CODEC.fieldOf("output").orElse(ModItems.ESSENCE_DUST.defaultStack)
                    .forGetter { it.output },
                (Codec.FLOAT.fieldOf("experience") as MapCodec).orElse(5.0f).forGetter { it.essence.toXp() },
            ).apply(builder) { ingredients, output, experience ->
                CalcinatingRecipe(ingredients, output, experience.toDroplets())
            }
        }

//        this.codec = RecordCodecBuilder.create(instance -> instance.group(
//        Codecs.createStrictOptionalFieldCodec(Codec.STRING, "group", "").forGetter(recipe -> recipe.group),
//        ((MapCodec)CookingRecipeCategory.CODEC.fieldOf("category")).orElse(CookingRecipeCategory.MISC).forGetter(recipe -> recipe.category),
//        ((MapCodec)Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("ingredient")).forGetter(recipe -> recipe.ingredient),
//        ((MapCodec)Registries.ITEM.getCodec().xmap(ItemStack::new, ItemStack::getItem).fieldOf("result")).forGetter(recipe -> recipe.result),
//        ((MapCodec)Codec.FLOAT.fieldOf("experience")).orElse(Float.valueOf(0.0f)).forGetter(recipe -> Float.valueOf(recipe.experience)),
//        ((MapCodec)Codec.INT.fieldOf("cookingtime")).orElse(cookingTime).forGetter(recipe -> recipe.cookingTime)
//        ).apply((Applicative<AbstractCookingRecipe, ?>)instance, recipeFactory::create));

        override fun codec(): Codec<CalcinatingRecipe> = CODEC

        override fun read(buf: PacketByteBuf): CalcinatingRecipe {
            val inputs = DefaultedList.ofSize(buf.readInt(), Ingredient.EMPTY)
            for (i in inputs.indices) {
                inputs[i] = Ingredient.fromPacket(buf)
            }
            val output = buf.readItemStack()
            val essence = buf.readLong()
            return CalcinatingRecipe(inputs[0], output, essence)
        }

        override fun write(buf: PacketByteBuf, recipe: CalcinatingRecipe) {
            buf.writeInt(1)
            for (ingredient in recipe.getIngredients()) {
                ingredient.write(buf)
            }
            buf.writeItemStack(recipe.getResult(null))
        }
    }
}

private fun Long.toXp(): Float = (this / 450).toFloat()
private fun Float.toDroplets(): Long = (this * 450).toLong()
