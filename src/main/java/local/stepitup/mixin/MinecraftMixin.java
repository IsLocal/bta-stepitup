package local.stepitup.mixin;

import local.stepitup.Stepitup;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Minecraft.class, remap = false)
public class MinecraftMixin {

	@Inject(method = "startGame", at = @At("TAIL"))
	private void stepitup$startGame(CallbackInfo ci) {
		Stepitup.INSTANCE.start((Minecraft) (Object) this);
	}

	@Inject(method = "shutdownMinecraftApplet", at = @At("HEAD"))
	private void stepitup$shutdownMinecraftApplet(CallbackInfo ci) {
		Stepitup.INSTANCE.stop();
	}
}
