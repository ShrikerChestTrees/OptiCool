package com.opticool.f3.mixin;

import com.opticool.f3.F3;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(DebugScreenOverlay.class)
public abstract class DebugScreenMixin {
    @Inject(method = "getGameInformation", at = @At("RETURN"), cancellable = true)
    private void opticoolf3$replaceVersionLine(CallbackInfoReturnable<List<String>> cir) {
        List<String> original = cir.getReturnValue();
        if (original == null || original.isEmpty()) return;

        List<String> replacement = new ArrayList<>(original);
        replacement.set(0, F3.get());
        cir.setReturnValue(replacement);
    }
}
