package link.infra.motiono.config;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class ConfigScreen extends Screen {
	protected Screen parent;

	protected ConfigScreen(Screen parent) {
		// Fun fact: you can't see this screen without fapi, as mod menu depends on it
		// So we can both not depend on fapi and have proper translations!
		// TODO: is it still no fapi dep?
		super(new TranslatableText("config.motiono.title"));
		this.parent = parent;
	}

	private final Text enabledText = new TranslatableText("config.motiono.enabled");
	private final Text disabledText = new TranslatableText("config.motiono.disabled");

	@Override
	protected void init() {
		addButton(new AbstractButtonWidget((width / 2) - 40, 40, 80, 20, ConfigHandler.getInstance().enabled ? enabledText : disabledText) {
			@Override
			public void onClick(double mouseX, double mouseY) {
				ConfigHandler.getInstance().enabled = !ConfigHandler.getInstance().enabled;
				setMessage(ConfigHandler.getInstance().enabled ? enabledText : disabledText);
			}
		});

		addButton(new AbstractButtonWidget((width / 2) - 40, 80, 80, 20, new TranslatableText("gui.done")) {
			@Override
			public void onClick(double mouseX, double mouseY) {
				assert client != null;
				client.openScreen(parent);
			}
		});
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		this.renderBackground(matrices);
		this.drawCenteredString(matrices, textRenderer, title.asString(), width / 2, 20, 0x00FFFFFF);
		super.render(matrices, mouseX, mouseY, delta);
	}

	@Override
	public void removed() {
		ConfigHandler.getInstance().save();
	}

	@Override
	public void onClose() {
		assert client != null;
		client.openScreen(parent);
	}
}
