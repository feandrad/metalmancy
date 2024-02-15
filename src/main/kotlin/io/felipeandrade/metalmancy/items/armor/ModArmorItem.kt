package io.felipeandrade.metalmancy.items.armor

import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.ArmorItem
import net.minecraft.item.ArmorMaterial

class ModArmorItem(
    material: ArmorMaterial,
    type: Type,
    settings: Settings = FabricItemSettings()
): ArmorItem(material, type, settings) {

}