package com.flying_8lack.random.main;

import com.flying_8lack.random.data.ModData;
import com.flying_8lack.random.loot.ModLoot;
import com.flying_8lack.random.util.BuildUtil;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.event.ServerChatEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Consumer;

import static com.flying_8lack.random.main.ModBlock.BLOCKS;
import static com.flying_8lack.random.main.ModBlock.H_ELEVATOR;
import static com.flying_8lack.random.main.ModItem.ITEMS;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(flying8lacksrandommod.MODID)
public class flying8lacksrandommod {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "flying8lacksrandommod";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public static Logger lg(){ return LOGGER; }
    // Create a Deferred Register to hold Blocks which will all be registered under the "flying8lacksrandommod" namespace

    // Create a Deferred Register to hold Items which will all be registered under the "flying8lacksrandommod" namespace

    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "flying8lacksrandommod" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // Creates a new Block with the id "flying8lacksrandommod:example_block", combining the namespace and path


    // Creates a creative tab with the id "flying8lacksrandommod:example_tab" for the example item, that is placed after the combat tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.flying8lacksrandommod")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ModBlock.H_ELEVATOR_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(H_ELEVATOR.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
                output.accept(ModItem.POST_PROTECTION_UPGRADE_ITEM);
            }).build());

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public flying8lacksrandommod(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        ModData.ATTACHMENT_TYPES.register(modEventBus);
        ModLoot.GLOBAL_LOOT_MOD_SERIAL.register(modEventBus);

        ModMenu.MENU.register(modEventBus);

        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);

        ModBlockEntity.BE.register(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (flying8lacksrandommod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.LOG_DIRT_BLOCK.getAsBoolean()) {
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
        }

        LOGGER.info("{}{}", Config.MAGIC_NUMBER_INTRODUCTION.get(), Config.MAGIC_NUMBER.getAsInt());

        Config.ITEM_STRINGS.get().forEach((item) -> LOGGER.info("ITEM >> {}", item));
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {

        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onChatEvent(ServerChatEvent event) {
        // Do something when the server starts
        if (event.getRawText().contains("joke")){
            event.getPlayer().getInventory().add(new ItemStack(Items.DIRT));
        }

    }







    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {

        if(Config.GIVE_SHOVEL_AT_START.isFalse()) return;

        LOGGER.info("Gave {} a shovel!", event.getEntity().getName());
        int number = event.getEntity().getData(ModData.SHOVELS);
        Player player = event.getEntity();
        PlayerChatMessage msg = PlayerChatMessage.unsigned(player.getUUID(), "I am stupid!");
        player.createCommandSourceStack().sendChatMessage(OutgoingChatMessage.create(msg),
                false, ChatType.bind(ChatType.CHAT, player));
        switch (number){
            case 0:
                event.getEntity().getInventory().add(new ItemStack(Items.DIAMOND_SHOVEL));
                player.setData(ModData.SHOVELS, player.getData(ModData.SHOVELS) + 1);
                break;
            case 1:
                event.getEntity().getInventory().add(new ItemStack(Items.IRON_SHOVEL));
                player.setData(ModData.SHOVELS, player.getData(ModData.SHOVELS) + 1);
                break;
            case 2:
                event.getEntity().getInventory().add(new ItemStack(Items.STONE_SHOVEL));
                player.setData(ModData.SHOVELS, player.getData(ModData.SHOVELS) + 1);
                break;
            case 3:
                event.getEntity().getInventory().add(new ItemStack(Items.WOODEN_SHOVEL));
                player.setData(ModData.SHOVELS, player.getData(ModData.SHOVELS) + 1);
                break;
            default:
                event.getEntity().addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN,
                        600, 2));

        }


    }
}
