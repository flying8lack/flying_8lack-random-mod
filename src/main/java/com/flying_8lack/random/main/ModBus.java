package com.flying_8lack.random.main;

import com.flying_8lack.random.data.*;
import com.flying_8lack.random.entity.FigEntity;
import com.flying_8lack.random.entity.goals.FireProjectileGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Giant;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.event.village.WandererTradesEvent;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static com.flying_8lack.random.main.flying8lacksrandommod.MODID;
import static com.flying_8lack.random.world.World.BUILDER;

@EventBusSubscriber(modid = MODID)
public class ModBus {

    @SubscribeEvent
    public static void onCap(RegisterCapabilitiesEvent event) {
        // Do something when the server starts
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                ModBlockEntity.H_ELEVATOR_BE.get(),
                (be, d) -> be.getUpgrade()
        );

        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                ModBlockEntity.SILLY_MINER_BE.get(),
                (be, d) -> be.getInv()
        );

    }

    @SubscribeEvent
    private static void breakBlockEvent(BlockEvent.BreakEvent event) {
        if(event.getLevel().isClientSide()) return;

        if(event.getState().is(Tags.Blocks.GLASS_BLOCKS)){
            Level level = event.getPlayer().level();
            if(level.getRandom().nextFloat() < 0.1f && level.canSeeSky(event.getPos())){
                Block.popResource(level, event.getPos(), ModItem.SILLY_GLASS_SHARD.toStack());
            }
        }

    }

    @SubscribeEvent
    private static void commonSetup(RegisterSpawnPlacementsEvent event) {
        event.register(
                ModEntity.FIG_ENTITY.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (f, level, spawnType, pos, rng) -> {
                    BlockPos p = pos.below();
                    return level.getBlockState(p).isValidSpawn(level, p, f);
                },
                RegisterSpawnPlacementsEvent.Operation.OR

        );
    }

    @SubscribeEvent
    public static void mobCreator (EntityAttributeCreationEvent e){
        e.put(ModEntity.FIG_ENTITY.get(),
                LivingEntity.createLivingAttributes().add(Attributes.MAX_HEALTH, 12.0f)
                        .add(Attributes.FOLLOW_RANGE, 36.0f)
                        .add(Attributes.MOVEMENT_SPEED, 0.19)
                        .add(Attributes.ATTACK_DAMAGE, 5)
                        .build());


    }

    @SubscribeEvent
    public static void modifyAttributes(EntityAttributeModificationEvent event) {
        // Check if the Giant has the attribute first, then add/modify it
        // Giants by default have health/attack but you can boost them
        event.add(EntityType.GIANT, Attributes.MAX_HEALTH, 200.0);
        event.add(EntityType.GIANT, Attributes.ATTACK_DAMAGE, 12.0);
        event.add(EntityType.GIANT, Attributes.MOVEMENT_SPEED, 0.27);
        event.add(EntityType.GIANT, Attributes.FOLLOW_RANGE, 48.0f);

    }


    @SubscribeEvent
    public static void modMobs (EntityJoinLevelEvent e){
        if(e.getEntity() instanceof AbstractGolem g){
            g.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(g, FigEntity.class, true, false));
        }
        if(e.getEntity() instanceof Villager v){
            v.goalSelector.addGoal(0, new AvoidEntityGoal<>(v, FigEntity.class, 12, 1.2, 1.1));
        }
        if(e.getEntity() instanceof Giant g){
            g.goalSelector.addGoal(0, new FireProjectileGoal(g,mob -> {
                LivingEntity target = mob.getTarget();

                Vec3 loc = target.position().subtract(mob.position().add(0,mob.getBbHeight(),0)).normalize();
                LargeFireball b = new LargeFireball(mob.level(), mob, loc, 4);


                b.moveTo(mob.position().add(loc.x, loc.y+mob.getBbHeight(),loc.z));
                b.setOwner(mob);

                return b;
            }));
            g.goalSelector.addGoal(0, new FloatGoal(g));
            g.goalSelector.addGoal(1, new MeleeAttackGoal(g, 1.2f, true));
            g.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(g, 1.0f));
            g.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(g, Player.class, false));
            g.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(g, AbstractVillager.class, false));
            g.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(g, IronGolem.class, true));

        }
    }

    @SubscribeEvent
    public static void villagerTrade (VillagerTradesEvent event){
        if(event.getType() == VillagerProfession.FARMER){
            event.getTrades().get(2).add( (entity, r) -> new MerchantOffer(
                    new ItemCost(Items.DIAMOND, r.nextInt(3,5)),
                    ModItem.FIG_SEED.toStack(),
                    1,
                    15,
                    0.07f

            ));
        }

    }

    @SubscribeEvent
    public static void villagerTrade (WandererTradesEvent event){
        event.getGenericTrades().add((entity, r) -> new MerchantOffer(
                new ItemCost(Items.DIAMOND, 2),
                ModItem.FIG_SEED.toStack(),
                3,
                15,
                0.2f

        ));

        event.getRareTrades().add((entity, r) -> new MerchantOffer(
                new ItemCost(Items.DIAMOND, 1),
                ModItem.FIG_SEED.toStack(),
                3,
                15,
                0.2f

        ));


    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public static void datagenEvent (GatherDataEvent event){
        // Do something when the server starts
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeServer(),
                new ModBiomeTagModifer(output,lookupProvider,existingFileHelper));

        generator.addProvider(event.includeServer(),
                new ModRecipeProvider(output, lookupProvider));


        generator.addProvider(event.includeClient(),
                new ModBlockModelProvider(output, existingFileHelper));

        generator.addProvider(event.includeClient(),
                new ModBlockStateProvider(output, existingFileHelper));

        generator.addProvider(event.includeServer(),
                new MyLootProvider(output,
                        Set.of(),
                        List.of(
                                new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new,
                                        LootContextParamSets.BLOCK),
                                new LootTableProvider.SubProviderEntry(ModEntityLootTableProvider::new,
                                        LootContextParamSets.ENTITY)
                        ),
                        lookupProvider)
        );



        generator.addProvider(event.includeServer(),
                new DatapackBuiltinEntriesProvider(output,lookupProvider, BUILDER, Set.of(MODID)));

        generator.addProvider(event.includeClient(),
                new ModItemModelProvider(output, existingFileHelper));

        generator.addProvider(event.includeClient(),
                new ModSoundDefProvider(output, existingFileHelper));


    }
}
