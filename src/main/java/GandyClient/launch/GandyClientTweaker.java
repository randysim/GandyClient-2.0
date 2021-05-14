package GandyClient.launch;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;

public class GandyClientTweaker implements ITweaker {
	
	private ArrayList<String> gameArgs = new ArrayList<>();

	@Override
	public void acceptOptions(List<String> args, File gameFolder, File assetsFolder, String profile) {
		// TODO Auto-generated method stub
		this.gameArgs.addAll(args);
		addGameArg("gameDir", gameFolder);
		addGameArg("assetsDir", assetsFolder);
		addGameArg("version", profile);
	}

	@Override
	public String[] getLaunchArguments() {
		// TODO Auto-generated method stub
		return (String[]) gameArgs.toArray();
	}

	@Override
	public String getLaunchTarget() {
		// TODO Auto-generated method stub
		return "net.minecraft.client.main.Main";
	}

	@Override
	public void injectIntoClassLoader(LaunchClassLoader classLoader) {
		// TODO Auto-generated method stub
		MixinBootstrap.init();
		MixinEnvironment environment = MixinEnvironment.getDefaultEnvironment();
        Mixins.addConfiguration("mixins.gandyclient.json");
        
        if (environment.getObfuscationContext() == null) 
        	environment.setObfuscationContext("notch");
        
        environment.setSide(MixinEnvironment.Side.CLIENT);
	}
	
	 private void addGameArg(String argType, Object argValue) {
	        gameArgs.add("--" + argType);
	        if (argValue instanceof String) {
	        	gameArgs.add((String) argValue);
	        } else if (argValue instanceof File) {
	        	gameArgs.add(((File) argValue).getAbsolutePath());
	        } else {
	        	gameArgs.add(".");
	        }
	        
	 }

}
