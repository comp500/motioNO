package link.infra.motiono.config;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.TranslatableText;

public class ConfigScreen extends Screen {
	protected Screen parent;

	protected ConfigScreen(Screen parent) {
		// No i18n because no fAPI dep here!!!!!!!
		super(new TranslatableText("config.motiono.title"));

		this.parent = parent;
	}

	private final String enabledText = I18n.translate("config.motiono.enabled");
	private final String disabledText = I18n.translate("config.motiono.disabled");

	@Override
	protected void init() {
		addButton(new AbstractButtonWidget((this.width / 2) - 40, 40, 80, 20, ConfigHandler.getInstance().enabled ? enabledText : disabledText) {
			@Override
			public void onClick(double mouseX, double mouseY) {
				ConfigHandler.getInstance().enabled = !ConfigHandler.getInstance().enabled;
				setMessage(ConfigHandler.getInstance().enabled ? enabledText : disabledText);
			}
		});

		addButton(new AbstractButtonWidget((this.width / 2) - 40, 80, 80, 20, "Done") {
			@Override
			public void onClick(double mouseX, double mouseY) {
				assert client != null;
				client.openScreen(parent);
			}
		});
	}

	public void render(int mouseX, int mouseY, float delta) {
		this.renderBackground();
		this.drawCenteredString(textRenderer, this.title.asFormattedString(), this.width / 2, 20, 0x00FFFFFF);
		super.render(mouseX, mouseY, delta);
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
