package com.flying_8lack.random.main;

import com.mojang.serialization.Codec;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

import static com.flying_8lack.random.main.flying8lacksrandommod.MODID;

public class ModData {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(
            NeoForgeRegistries.ATTACHMENT_TYPES, MODID);

    public static final Supplier<AttachmentType<Integer>> SHOVELS = ATTACHMENT_TYPES.register(
            "", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).build()
    );
}
