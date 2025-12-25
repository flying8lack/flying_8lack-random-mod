package com.flying_8lack.random.main;

import com.flying_8lack.random.blockentity.HElevatorBlockEntity;
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
}
