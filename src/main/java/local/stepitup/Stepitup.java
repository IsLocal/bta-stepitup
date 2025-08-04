package local.stepitup;

import local.stepitup.util.OptionStore;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.options.components.BooleanOptionComponent;
import net.minecraft.client.gui.options.components.KeyBindingComponent;
import net.minecraft.client.gui.options.data.OptionsPage;
import net.minecraft.client.gui.options.data.OptionsPages;
import net.minecraft.client.input.InputDevice;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.Option;
import net.minecraft.client.option.OptionBoolean;
import net.minecraft.core.block.Blocks;
import org.lwjgl.input.Keyboard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public class Stepitup implements ModInitializer {
	public static Stepitup INSTANCE;
    public static final String MOD_ID = "stepitup";
	public final Path optionFilePath = FabricLoader.getInstance().getConfigDir().resolve("stepitup_config.properties");

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public OptionBoolean stepitupEnabled;
	public OptionBoolean stepitupSneakingEnabled;
	public KeyBinding stepitupKey = new KeyBinding("key.enable_stepitup")
		.setDefault(InputDevice.keyboard, Keyboard.KEY_B);

	public boolean stepitupKeyPressed = false;
	public Option<?>[] options;
	public KeyBinding[] keys = {stepitupKey};
	@Override
    public void onInitialize() {
		Stepitup.INSTANCE = this;
		LOGGER.info("Stepitup initialized.");
    }



	public void start(Minecraft client) {
		try {
			stepitupEnabled = new OptionBoolean(client.gameSettings, "stepitup_enabled", true);
			stepitupSneakingEnabled = new OptionBoolean(client.gameSettings, "stepitup_sneaking_enabled", true);


			OptionsPages.register(new OptionsPage("gui.options.page.stepitup.title", Blocks.STAIRS_BRICK_CLAY.getDefaultStack()))
				.withComponent(new KeyBindingComponent(stepitupKey))
				.withComponent(new BooleanOptionComponent(stepitupEnabled))
				.withComponent(new BooleanOptionComponent(stepitupSneakingEnabled));
			options = new Option<?>[] {stepitupEnabled, stepitupSneakingEnabled};

			OptionStore.loadOptions(optionFilePath, options, keys);
			LOGGER.info("Stepitup loaded.");
		} catch (Exception ex) {
			LOGGER.error("Failed to load options.");
			ex.printStackTrace();
		}
	}

	public void stop() {
		OptionStore.saveOptions(optionFilePath, options, new KeyBinding[]{});
	}



}
