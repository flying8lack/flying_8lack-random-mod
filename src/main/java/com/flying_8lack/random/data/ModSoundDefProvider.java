package com.flying_8lack.random.data;

import com.flying_8lack.random.main.ModSound;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

import static com.flying_8lack.random.main.flying8lacksrandommod.MODID;

public class ModSoundDefProvider extends SoundDefinitionsProvider {
    public ModSoundDefProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, MODID, helper);
    }

    @Override
    public void registerSounds() {

        add(ModSound.WET_SOUND.value(), SoundDefinition.definition()
                .with(sound(MODID+":wet_sound")));

    }
}
