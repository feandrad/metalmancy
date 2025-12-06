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

    fun <M : net.minecraft.world.inventory.AbstractContainerMenu, U : net.minecraft.client.gui.screens.Screen> registerScreenFactory(
        type: net.minecraft.world.inventory.MenuType<out M>,
        factory: (M, net.minecraft.world.entity.player.Inventory, net.minecraft.network.chat.Component) -> U
    ) {
        try {
            val menuScreensClass = net.minecraft.client.gui.screens.MenuScreens::class.java
            // Find the ScreenConstructor interface (it's an inner interface)
            val screenConstructorClass = menuScreensClass.declaredClasses.firstOrNull { it.simpleName == "ScreenConstructor" }
                ?: throw ClassNotFoundException("Could not find MenuScreens.ScreenConstructor inner class")

            val registerMethod = menuScreensClass.getDeclaredMethod(
                "register",
                net.minecraft.world.inventory.MenuType::class.java,
                screenConstructorClass
            )
            registerMethod.isAccessible = true

            // Create a proxy for the ScreenConstructor interface
            val proxy = java.lang.reflect.Proxy.newProxyInstance(
                screenConstructorClass.classLoader,
                arrayOf(screenConstructorClass)
            ) { _, _, args ->
                // invoke(menu, inventory, title)
                @Suppress("UNCHECKED_CAST")
                factory(args[0] as M, args[1] as net.minecraft.world.entity.player.Inventory, args[2] as net.minecraft.network.chat.Component)
            }

            registerMethod.invoke(null, type, proxy)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
