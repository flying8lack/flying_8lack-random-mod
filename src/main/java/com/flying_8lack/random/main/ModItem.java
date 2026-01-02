package com.flying_8lack.random.main;

import com.flying_8lack.random.items.PostProtectionUpgradeItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.flying_8lack.random.main.flying8lacksrandommod.MODID;

public class ModItem {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);


    public static final DeferredItem<PostProtectionUpgradeItem> POST_PROTECTION_UPGRADE_ITEM = ITEMS.register(
            "post_protection_upgrade_item", PostProtectionUpgradeItem::new
    );
}
