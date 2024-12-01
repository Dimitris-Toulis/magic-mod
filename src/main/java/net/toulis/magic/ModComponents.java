package net.toulis.magic;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.List;

public class ModComponents {

    public static final ComponentType<List<String>> SPELLS_COMPONENT = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(MagicMod.MOD_ID, "spells"),
            ComponentType.<List<String>>builder().codec(Codec.list(Codec.STRING)).build()
    );

    protected static void init() {
        MagicMod.LOGGER.info("Registering {} components", MagicMod.MOD_ID);
    }
}