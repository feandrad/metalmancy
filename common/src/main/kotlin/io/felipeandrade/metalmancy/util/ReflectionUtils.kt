package io.felipeandrade.metalmancy.util

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import java.lang.reflect.Method

object ReflectionUtils {
    private var registerMethod: Method? = null

    @Suppress("UNCHECKED_CAST")
    fun <T : BlockEntity> registerBlockEntityType(name: String, factory: BlockEntityType.BlockEntitySupplier<T>, vararg blocks: Block): BlockEntityType<T> {
        try {
            if (registerMethod == null) {
                // Find the private static register method: register(String, BlockEntitySupplier, Block...)
                val clazz = BlockEntityType::class.java
                // Note: The method might be mapped differently in production, but we are in dev env (Mojmap/Intermediary).
                // Based on previous debug output, it is named "register".
                // Signature: (String, BlockEntitySupplier, Block[])
                registerMethod = clazz.getDeclaredMethod("register", String::class.java, BlockEntityType.BlockEntitySupplier::class.java, Array<Block>::class.java)
                registerMethod?.isAccessible = true
            }
            return registerMethod?.invoke(null, name, factory, blocks) as BlockEntityType<T>
        } catch (e: Exception) {
            throw RuntimeException("Failed to register BlockEntityType via reflection", e)
        }
    }
}
