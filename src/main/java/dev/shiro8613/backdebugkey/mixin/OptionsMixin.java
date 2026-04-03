package dev.shiro8613.backdebugkey.mixin;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Options;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Arrays;

import static dev.shiro8613.backdebugkey.ModOptions.RENDER_DISTANCE;

@Mixin(Options.class)
public class OptionsMixin {


    @Shadow @Final public KeyMapping[] debugKeys;

    @Redirect(method = "<init>", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Options;debugKeys:[Lnet/minecraft/client/KeyMapping;", opcode = Opcodes.GETFIELD))
    private KeyMapping[] bk$redirectKeys(Options instance) {
        KeyMapping[] original = this.debugKeys;
        KeyMapping[] newKeys = Arrays.copyOf(original, original.length + 1);
        newKeys[original.length] = RENDER_DISTANCE;
        return newKeys;
    }
}
