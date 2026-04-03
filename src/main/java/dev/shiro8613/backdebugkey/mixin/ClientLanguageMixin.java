package dev.shiro8613.backdebugkey.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.ClientLanguage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;

import java.util.HashMap;
import java.util.Map;

@Mixin(ClientLanguage.class)
public class ClientLanguageMixin {

    @Unique
    private static final String DEBUG_RENDER_KEY = "debug.cycle_renderdistance.message";

    @ModifyVariable(method = "<init>", at = @At("HEAD"), argsOnly = true)
    private static Map<String, String> bk$init(Map<String, String> original) {
        Map<String, String> map = new HashMap<>(original);
        if (Minecraft.getInstance().options.languageCode.equals("ja_jp")) {
            map.put(DEBUG_RENDER_KEY, "描画距離: %s");
        } else {
            map.put(DEBUG_RENDER_KEY, "Render Distance: %s");
        }

        return Map.copyOf(map);
    }

}
