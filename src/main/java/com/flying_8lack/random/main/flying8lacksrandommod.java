package com.flying_8lack.random.main;

import com.flying_8lack.random.items.potions.ModPotions;
import com.flying_8lack.random.loot.ModLoot;
import net.minecraft.network.chat.ChatType;
import com.flying_8lack.random.main.ModData;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.neoforge.event.ServerChatEvent;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import static com.flying_8lack.random.main.ModBlock.BLOCKS;
import static com.flying_8lack.random.main.ModCreativeTab.CREATIVE_MODE_TABS;
import static com.flying_8lack.random.main.ModItem.ITEMS;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(flying8lacksrandommod.MODID)
public class flying8lacksrandommod {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "flying8lacksrandommod";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public static Logger lg(){ return LOGGER; }

    public flying8lacksrandommod(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        ModData.ATTACHMENT_TYPES.register(modEventBus);
        ModLoot.GLOBAL_LOOT_MOD_SERIAL.register(modEventBus);

        ModMenu.MENU.register(modEventBus);

        ModPotions.POTION.register(modEventBus);

        ModEffect.ME.register(modEventBus);
        ModEntity.ET.register(modEventBus);

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
            event.accept(ModBlock.FIG_BLOCK);
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

    @SubscribeEvent // on the game event bus
    public void registerBrewingRecipes(RegisterBrewingRecipesEvent event) {
        // Gets the builder to add recipes to
        PotionBrewing.Builder builder = event.getBuilder();

                // Will add brewing recipes for all container potions (e.g. potion, splash potion, lingering potion)
                        builder.addMix(
                        // The initial potion to apply to
                        Potions.AWKWARD,
                        // The brewing ingredient. This is the item at the top of the brewing stand.
                        ModItem.FIG_FOOD.asItem(),
                        // The resulting potion
                        ModPotions.FIGIFICATION
                );
    }
}
