package io.felipeandrade.metalmancy.fluid

import dev.architectury.registry.registries.DeferredRegister
import dev.architectury.registry.registries.RegistrySupplier
import io.felipeandrade.metalmancy.Metalmancy
import net.minecraft.core.registries.Registries
import net.minecraft.world.level.material.Fluid

object ModFluids {
    val FLUIDS: DeferredRegister<Fluid> = DeferredRegister.create(Metalmancy.MOD_ID, Registries.FLUID)

    // Placeholder for Essence Fluid. In a real implementation, this would need Flowing/Still/Block/Bucket/Type setup.
    // For now, I'll register a dummy fluid to allow compilation.
    // TODO: Implement full Fluid registration (Flowing, Still, Block, Bucket, Attributes)
    val STILL_ESSENCE: RegistrySupplier<Fluid> = FLUIDS.register("still_essence") { StillEssence() }
    val FLOWING_ESSENCE: RegistrySupplier<Fluid> = FLUIDS.register("flowing_essence") { FlowingEssence() }

    fun registerAll() {
        FLUIDS.register()
    }
}
