package local.stepitup.mixin;


import local.stepitup.Stepitup;
import net.minecraft.client.Minecraft;
import net.minecraft.core.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Entity.class, remap = false)
public abstract class EntityMixin  {

	@Shadow
	public float footSize;

	@Shadow
	public abstract boolean isSneaking();


	@Shadow
	public int id;

	@Inject(method = "move", at = @At("HEAD"))
	private void stepitup$move(double x, double y, double z, CallbackInfo ci) {
		if (id != Minecraft.getMinecraft().thePlayer.id) {
			return;
		}
		if (Minecraft.getMinecraft().currentScreen == null) {
			if (Stepitup.INSTANCE.stepitupKey.isPressed()) {
				if (!Stepitup.INSTANCE.stepitupKeyPressed) {
					Stepitup.INSTANCE.stepitupKeyPressed = true;
					Stepitup.INSTANCE.stepitupEnabled.set(!Stepitup.INSTANCE.stepitupEnabled.value);
				}
			} else {
				Stepitup.INSTANCE.stepitupKeyPressed = false;
			}
		}
		if ((isSneaking() && !Stepitup.INSTANCE.stepitupSneakingEnabled.value) || Stepitup.INSTANCE.stepitupEnabled.value == false) {
			footSize = 0.5F;
		} else {
			footSize = 1.0F;
		}
	}
}
