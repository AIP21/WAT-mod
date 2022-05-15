package com.anip24.playertracker;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@me.shedaniel.autoconfig.annotation.Config(name = "playertracker")
public class Config implements ConfigData {

    public boolean enabled = true;

    @Comment("How frequent a position tracking log is done. If set to 4, a log is done every 4 ticks. If set to 1, it is done every tick")
    @ConfigEntry.BoundedDiscrete(min = 100, max = 2400)
    public int frequency = 100;
    public boolean debugLogging = false;

    @Override
    public void validatePostLoad() throws ValidationException {
        if (frequency < 100) frequency = 100;
        if (frequency > 2400) frequency = 2400;
    }
}
//
//@Environment(EnvType.CLIENT)
//class ModMenuIntegration implements ModMenuApi {
//
//    @Override
//    public ConfigScreenFactory<?> getModConfigScreenFactory() {
//        return parent -> AutoConfig.getConfigScreen(ModConfig.class, parent).get();
//    }
//}