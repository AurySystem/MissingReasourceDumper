package gay.aurum.missingnodumper.mixin;

import gay.aurum.missingnodumper.GenericResource;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static gay.aurum.missingnodumper.MissingResourceDumper.Assets;


@Mixin(SpriteAtlasTexture.class)
public abstract class SpriteAtlasMixin {

	@Shadow
	protected abstract Identifier getTexturePath(Identifier id);

	@Inject(method = "loadSprite", at = @At("TAIL"))
	private void missingnodumper$CheckMissingTexture(ResourceManager container, Sprite.Info info, int atlasWidth, int atlasHeight, int maxLevel, int x, int y, CallbackInfoReturnable<Sprite> cir){
		Assets.put(this.getTexturePath(info.getId()), GenericResource.MISSINGNO);
	}
}
