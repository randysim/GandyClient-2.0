package GandyClient.core.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;

import com.google.gson.JsonObject;

import GandyClient.core.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

/* MODIFIED FROM OPTIFINE cape.utils */
public class CapeUtils
{	
    public static void downloadCape(final AbstractClientPlayer player, int capeColor)
    {
    	String username = player.getName();
        boolean isColor = capeColor != 0;

        if (username != null && !username.isEmpty())
        {
        	String ofCapeUrl = null;
        	
        	// cheap fix hopefully
        	// fixing the sun cert thingy problem
        	/* 
        	  dev note: HttpsURLConnection
        	  */
        	JsonObject capesJson = Client.INSTANCE.getDataManager().getCapesData();
        	
        	/* SET CAPE URL */
        	if (capesJson.has(username)) {
        		ofCapeUrl = capesJson.get(username).toString();
        		ofCapeUrl = ofCapeUrl.substring(1, ofCapeUrl.length()-1); // getting rid of the weird quotes
        		System.out.println("Getting cape from " + ofCapeUrl);
        	} else {
        		return;
        	}

            // String mptHash = FilenameUtils.getBaseName(ofCapeUrl);
            ResourceLocation rlTemp = null;
            if (!isColor) {
            	rlTemp = new ResourceLocation("capeof/" + username);
            } else {
            	rlTemp = new ResourceLocation(Integer.toString(capeColor) + "_colorcapeof/" + username);
            }
            
            final ResourceLocation rl = rlTemp; 
            
            TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
            ITextureObject tex = textureManager.getTexture(rl);

            if (
            	(isColor && Client.INSTANCE.getCapeManager().getColoredCape(username, capeColor) != null) ||
            	(!isColor && Client.INSTANCE.getCapeManager().getCape(username) != null)
            )
            {
            	// checks if texture is already stored to prevent loading it again
                return;
            }

            IImageBuffer iib = new IImageBuffer()
            {
                ImageBufferDownload ibd = new ImageBufferDownload();
                public BufferedImage parseUserSkin(BufferedImage var1)
                {
                    BufferedImage cutOut = parseCape(var1);
                    
                    if (isColor) {
                    	return colorCape(cutOut, capeColor);
                    } else {
                    	return cutOut;
                    }
                }
                public void skinAvailable()
                {		
                		if (isColor) {
                			Client.INSTANCE.getCapeManager().addColoredCape(username, capeColor, rl);
                		} else {
                			Client.INSTANCE.getCapeManager().addCape(username, rl);
                		}
                }

            };
            ThreadDownloadImageData textureCape = new ThreadDownloadImageData((File)null, ofCapeUrl, (ResourceLocation)null, iib);
            
    		if (isColor) {
    			Client.INSTANCE.getCapeManager().addColoredCape(username, capeColor, rl);
    		} else {
    			Client.INSTANCE.getCapeManager().addCape(username, rl);
    		}
    		
            textureManager.loadTexture(rl, textureCape);
        }
    }

    public static BufferedImage parseCape(BufferedImage img)
    {
        int imageWidth = 64;
        int imageHeight = 32;
        int srcWidth = img.getWidth();

        for (int srcHeight = img.getHeight(); imageWidth < srcWidth || imageHeight < srcHeight; imageHeight *= 2)
        {
            imageWidth *= 2;
        }

        BufferedImage imgNew = new BufferedImage(imageWidth, imageHeight, 2);
        Graphics g = imgNew.getGraphics();
        g.drawImage(img, 0, 0, (ImageObserver)null);
        g.dispose();
        return imgNew;
    }
    
    public static BufferedImage colorCape (BufferedImage img, int newColor) {
    	// only works for capes with a white background as of now
    	
    	int h = img.getHeight();
    	int w = img.getWidth();
    	
    	for (int hI = 0; hI < h; ++hI) {
    		for (int wI = 0; wI < w; ++wI) {
    			
    			if (img.getRGB(wI, hI) == -1) {
    				Color originalColor = new Color(newColor);
    				double colorScale = 0.25;
    				Color lightColor = new Color(
    						Math.min((int) ((double) originalColor.getRed() + (colorScale * 255D)), 255), 
    						Math.min((int) ((double) originalColor.getGreen() + (colorScale * 255D)), 255), 
    						Math.min((int) ((double) originalColor.getBlue() + (colorScale * 255D)), 255)
    					);
    				img.setRGB(wI, hI, lightColor.getRGB());
    			}
    		}
    	}
    	
    	BufferedImage imgNew = new BufferedImage(w, h, 2);
        Graphics g = imgNew.getGraphics();
        g.drawImage(img, 0, 0, (ImageObserver)null);
        g.dispose();
        return imgNew;
    }
}
