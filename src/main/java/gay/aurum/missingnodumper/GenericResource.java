package gay.aurum.missingnodumper;

import com.google.gson.JsonObject;
import net.minecraft.client.texture.MissingSprite;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.util.Identifier;

import java.awt.image.BufferedImage;

public class GenericResource {
	private static BufferedImage convertNativeToBuffer(NativeImage img){
		BufferedImage out;
		if(img != null) {
			out = new BufferedImage(img.getWidth(), img.getHeight(), 1);
			for (int x = 0; x < out.getWidth(); ++x) {
				for (int y = 0; y < out.getHeight(); ++y) { //why are we supporting the possibility that someone might change the missing texture
					out.setRGB(x, y, img.getPixelColor(x, y));
				}
			}
		} else {
			out = new BufferedImage(16, 16, 1);
			for (int x = 0; x < 16; ++x) {
				for (int y = 0; y < 16; ++y) {
					out.setRGB(x, y, x^y<<2);
				}
			}
		}
		return out;
	}
	private static BufferedImage bufferedMissing(){
		BufferedImage out = new BufferedImage(16, 16, 1);
		for (int x = 0; x < 16; ++x) {
			for (int y = 0; y < 16; ++y) {
				if (y < 8 ^ x < 8) {
					out.setRGB(x, y, 0);
				} else {
					out.setRGB(x, y, 0xf800f8);
				}
			}
		}
		return out;

	}
	public static BufferedImage missingtex = bufferedMissing()/*convertNativeToBuffer(MissingSprite.getMissingSpriteTexture().getImage())*/;

	public String data;
	public Boolean isTexture;

	GenericResource(String data, Boolean isTexture){
		this.data = data;
		this.isTexture = isTexture;
	}

	public static GenericResource MISSINGNO = new GenericResource("", true);

	public static GenericResource buildJson(JsonObject obj){
		return new GenericResource(obj.toString(),false);
	}

	// a temporary storage for default data evidently it just needs to hold strings or missingno

}
