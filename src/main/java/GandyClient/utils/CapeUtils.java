package GandyClient.utils;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;

import org.apache.commons.io.FilenameUtils;

import com.google.gson.JsonObject;

import GandyClient.Client;
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
    public static void downloadCape(final AbstractClientPlayer player)
    {
        String username = player.getName();

        if (username != null && !username.isEmpty())
        {
        	String ofCapeUrl = null;
        	
        	// cheap fix hopefully
        	// fixing the sun cert thingy problem
        	/* 
        	  dev note: HttpsURLConnection
        	  */
        	JsonObject capesJson = Client.getInstance().getDataManager().getCapesData();
        	
        	/* SET CAPE URL */
        	if (capesJson.has(username)) {
        		ofCapeUrl = capesJson.get(username).toString();
        		ofCapeUrl = ofCapeUrl.substring(1, ofCapeUrl.length()-1); // getting rid of the weird quotes
        		System.out.println("Getting cape from " + ofCapeUrl);
        	} else {
        		return;
        	}

            String mptHash = FilenameUtils.getBaseName(ofCapeUrl);
            final ResourceLocation rl = new ResourceLocation("capeof/" + mptHash);
            TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
            ITextureObject tex = textureManager.getTexture(rl);

            if (tex != null && tex instanceof ThreadDownloadImageData)
            {
            	// checks if texture is already stored to prevent loading it again
            	Client.getInstance().getCapeManager().addCape(username, rl);
                return;
            }

            IImageBuffer iib = new IImageBuffer()
            {
                ImageBufferDownload ibd = new ImageBufferDownload();
                public BufferedImage parseUserSkin(BufferedImage var1)
                {
                    return CapeUtils.parseCape(var1);
                }
                public void skinAvailable()
                {
                	Client.getInstance().getCapeManager().addCape(username, rl);
                }

            };
            ThreadDownloadImageData textureCape = new ThreadDownloadImageData((File)null, ofCapeUrl, (ResourceLocation)null, iib);
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
}
