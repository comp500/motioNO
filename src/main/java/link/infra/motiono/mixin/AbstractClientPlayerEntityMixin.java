package link.infra.motiono.mixin;

import com.mojang.authlib.GameProfile;
import link.infra.motiono.config.ConfigHandler;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstractClientPlayerEntityMixin extends PlayerEntity {
	public AbstractClientPlayerEntityMixin(World world, GameProfile profile) {
		super(world, profile);
	}

	@Inject(method = "getSpeed()F", at = @At("HEAD"), cancellable = true)
	public void onGetSpeed(CallbackInfoReturnable<Float> cir) {
		if (ConfigHandler.getInstance().enabled) {
			cir.setReturnValue(1.0f);
		}
	}
}
