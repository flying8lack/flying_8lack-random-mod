package com.flying_8lack.random.main;

import com.flying_8lack.random.blockentity.HElevatorBlockEntity;
import com.flying_8lack.random.blockentity.PotionMixerBlockEntity;
import com.flying_8lack.random.blockentity.SillyMinerBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.flying_8lack.random.main.flying8lacksrandommod.MODID;

public class ModBlockEntity {
    public static final DeferredRegister<BlockEntityType<?>> BE = DeferredRegister.create(
            Registries.BLOCK_ENTITY_TYPE,MODID);

    public static final Supplier<BlockEntityType<HElevatorBlockEntity>> H_ELEVATOR_BE = BE.register(
            "h_elevator_be", () -> BlockEntityType.Builder.of(
                    HElevatorBlockEntity::new,
                    ModBlock.H_ELEVATOR.get()
            ).build(null)
    );

    public static final Supplier<BlockEntityType<SillyMinerBlockEntity>> SILLY_MINER_BE = BE.register(
            "silly_miner_be", () -> BlockEntityType.Builder.of(
                    SillyMinerBlockEntity::new,
                    ModBlock.SILLY_MINER.get()
            ).build(null)
    );

    public static final Supplier<BlockEntityType<PotionMixerBlockEntity>> POTION_MIXER_BE = BE.register(
            "potion_mixer_be", () -> BlockEntityType.Builder.of(
                    PotionMixerBlockEntity::new,
                    ModBlock.POTION_MIXER_BLOCK.get()
            ).build(null)
    );
}
