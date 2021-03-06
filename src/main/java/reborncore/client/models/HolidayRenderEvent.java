package reborncore.client.models;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import reborncore.RebornCore;
import reborncore.common.RebornCoreConfig;
import reborncore.common.util.CalenderUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mark on 27/11/2016.
 */
public class HolidayRenderEvent {

	static ModelSantaHat santaHat = new ModelSantaHat();
	private static final ResourceLocation TEXTURE = new ResourceLocation("reborncore", "textures/models/santa_hat.png");
	static List<RenderPlayer> renderPlayerList = new ArrayList<>();

	@SubscribeEvent
	public static void holidayRender(RenderPlayerEvent.Pre event) {

		if (!CalenderUtils.christmas || !RebornCoreConfig.easterEggs) {
			return;
		}
		Render render = Minecraft.getMinecraft().getRenderManager().getEntityRenderObject(event.getEntityPlayer());
		if (render instanceof RenderPlayer) {
			RenderPlayer renderPlayer = (RenderPlayer) render;
			if (!renderPlayerList.contains(renderPlayer)) {
				renderPlayer.addLayer(new LayerRender());
				renderPlayerList.add(renderPlayer);
			}
		}

	}

	private static class LayerRender implements LayerRenderer<AbstractClientPlayer> {

		@Override
		public void doRenderLayer(AbstractClientPlayer abstractClientPlayer,
		                          float limbSwing,
		                          float limbSwingAmount,
		                          float partialTicks,
		                          float ageInTicks,
		                          float netHeadYaw,
		                          float headPitch,
		                          float scale) {
			float yaw = abstractClientPlayer.prevRotationYaw + (abstractClientPlayer.rotationYaw - abstractClientPlayer.prevRotationYaw) * partialTicks - (abstractClientPlayer.prevRenderYawOffset + (abstractClientPlayer.renderYawOffset - abstractClientPlayer.prevRenderYawOffset) * partialTicks);
			float pitch = abstractClientPlayer.prevRotationPitch + (abstractClientPlayer.rotationPitch - abstractClientPlayer.prevRotationPitch) * partialTicks;
			Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);
			GlStateManager.pushMatrix();
			GlStateManager.rotate(yaw, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(pitch, 1.0F, 0.0F, 0.0F);
			//GlStateManager.translate(-0.25F, -0.0F, 0.0F);
			if (abstractClientPlayer.isSneaking()) {
				GlStateManager.translate(0.0F, 0.26F, 0.0F);
			}

			float scale2 = 1.0F;
			GlStateManager.scale(scale2, scale2, scale2);
			santaHat.render(0.0625F);
			GlStateManager.popMatrix();

		}

		@Override
		public boolean shouldCombineTextures() {
			return true;
		}
	}

}
