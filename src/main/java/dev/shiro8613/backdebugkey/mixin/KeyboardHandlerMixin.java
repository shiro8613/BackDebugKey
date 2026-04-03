package dev.shiro8613.backdebugkey.mixin;

import dev.shiro8613.backdebugkey.ModOptions;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin {

    @Shadow @Final private Minecraft minecraft;

    @Shadow protected abstract void debugFeedbackTranslated(String string, Object... objects);

    @Inject(method = "handleDebugKeys", at = @At("RETURN"), cancellable = true)
    private void bk$handleDebugKeys(KeyEvent keyEvent, CallbackInfoReturnable<Boolean> cir) {
        if (ModOptions.RENDER_DISTANCE.matches(keyEvent)) {
            Options options = this.minecraft.options;
            OptionInstance<Integer> instance = options.renderDistance();
            OptionInstance.IntRange range = (OptionInstance.IntRange) instance.values();
            instance.set(Mth.clamp(instance.get() + (keyEvent.hasShiftDown() ? -1 : 1), range.minInclusive(), range.maxInclusive()));
            debugFeedbackTranslated("debug.cycle_renderdistance.message", String.valueOf(this.minecraft.options.renderDistance().get()));
            cir.setReturnValue(true);
        }

    }


}
