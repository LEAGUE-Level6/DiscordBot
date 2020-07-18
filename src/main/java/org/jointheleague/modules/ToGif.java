package org.jointheleague.modules;


import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import org.javacord.api.entity.message.MessageAttachment;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

public class ToGif extends CustomMessageCreateListener {

	/*
	 * ToGif by Kevin
	 * 
	 * !AddGifImg adds a frame to the final gif !ToGif compiles the gif and sends it
	 * back, the default delay time between each frame is 250 milliseconds this can
	 * be changed by calling the command with !ToGif (delay in millis)
	 */

	private static final String COMMAND_AddGifImg = "!AddGifImg";
	private static final String COMMAND_ToGif = "!ToGif";

	public static ArrayList<URL> URLS = new ArrayList<URL>();

	public ToGif(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND_AddGifImg, "!AddGifImg adds a frame to the final gif !ToGif compiles the gif and sends it back, the default delay time between each frame is 250 milliseconds this can be changed by calling the command with !ToGif (delay in millis)     Note: to use this, press upload image, and set the comment to the command");

	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().contains(COMMAND_AddGifImg)) {
			try {
				List<MessageAttachment> images = event.getMessageAttachments();
				for (int i = 0; i < images.size(); i++) {
					URLS.add(new URL(images.get(i).getUrl().toString()));
				}
			} catch (Exception e) {
				// TODO: handle exception
				//event.getChannel().sendMessage("Error: " + e);

			}
		} else if (event.getMessageContent().contains(COMMAND_ToGif)) {
			try {
				(new File("/tmp/example.gif")).delete();
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {

				String[] splitCommand = event.getMessageContent().split(" ");

				int delay = 250;
				if (splitCommand.length > 1) {
					delay = Integer.parseInt(splitCommand[1]);
				}
				//System.out.println(delay);

				BufferedImage first = (ImageIO.read(URLS.get(0)));
				ImageOutputStream output = new FileImageOutputStream(new File("/tmp/example.gif"));

				GifSequenceWriter writer = new GifSequenceWriter(output, first.getType(), delay, true);
				writer.writeToSequence(first);

				for (int i = 0; i < URLS.size(); i++) {
					BufferedImage next = ImageIO.read(URLS.get(i));
					writer.writeToSequence(next);
				}

				writer.close();
				output.close();
				URLS.clear();

				// event.getChannel().sendMessage("Consider adding images with " +
				// COMMAND_AddGifImg + " first Error: " + e);
				// //System.out.println(new File("/tmp/example.gif").length()/1074790400‬);
				// (1048576 * 1024)
				Thread t = new Thread(() -> {
					if (new File("/tmp/example.gif").length() > 1000000 * 8) {
						event.getChannel().sendMessage("File is too big to send, file totals to a size of "
								+ ((float) new File("/tmp/example.gif").length() / 1000000) + "MB");

					} else {
						// 1048576
						event.getChannel()
								.sendMessage("File Size: " + ((float) new File("/tmp/example.gif").length() / 1000000)
										+ "MB                                     Status:Uploading");
						event.getChannel().sendMessage(new File("/tmp/example.gif"));
					}
				});
				t.start();
			} catch (NullPointerException e) {
				event.getChannel()
				.sendMessage("I dont think that was a compatible file you uploaded          Error: " + e);

			} catch (Exception e) {

				// TODO: handle exception
				event.getChannel()
						.sendMessage("Consider adding images with " + COMMAND_AddGifImg + " first         Error: " + e);
			}
		}
	}

}


/*
 * Code under here from: https://memorynotfound.com/generate-gif-image-java-delay-infinite-loop-example/
 * */


class GifSequenceWriter {

    protected ImageWriter writer;
    protected ImageWriteParam params;
    protected IIOMetadata metadata;

    public GifSequenceWriter(ImageOutputStream out, int imageType, int delay, boolean loop) throws IOException {
        writer = ImageIO.getImageWritersBySuffix("gif").next();
        params = writer.getDefaultWriteParam();

        ImageTypeSpecifier imageTypeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(imageType);
        metadata = writer.getDefaultImageMetadata(imageTypeSpecifier, params);

        configureRootMetadata(delay, loop);

        writer.setOutput(out);
        writer.prepareWriteSequence(null);
    }

    private void configureRootMetadata(int delay, boolean loop) throws IIOInvalidTreeException {
        String metaFormatName = metadata.getNativeMetadataFormatName();
        IIOMetadataNode root = (IIOMetadataNode) metadata.getAsTree(metaFormatName);

        IIOMetadataNode graphicsControlExtensionNode = getNode(root, "GraphicControlExtension");
        graphicsControlExtensionNode.setAttribute("disposalMethod", "none");
        graphicsControlExtensionNode.setAttribute("userInputFlag", "FALSE");
        graphicsControlExtensionNode.setAttribute("transparentColorFlag", "FALSE");
        graphicsControlExtensionNode.setAttribute("delayTime", Integer.toString(delay / 10));
        graphicsControlExtensionNode.setAttribute("transparentColorIndex", "0");

        IIOMetadataNode commentsNode = getNode(root, "CommentExtensions");
        commentsNode.setAttribute("CommentExtension", "Created by: Kevin's Bot!");

        IIOMetadataNode appExtensionsNode = getNode(root, "ApplicationExtensions");
        IIOMetadataNode child = new IIOMetadataNode("ApplicationExtension");
        child.setAttribute("applicationID", "NETSCAPE");
        child.setAttribute("authenticationCode", "2.0");

        int loopContinuously = loop ? 0 : 1;
        child.setUserObject(new byte[]{ 0x1, (byte) (loopContinuously & 0xFF), (byte) ((loopContinuously >> 8) & 0xFF)});
        appExtensionsNode.appendChild(child);
        metadata.setFromTree(metaFormatName, root);
    }

    private static IIOMetadataNode getNode(IIOMetadataNode rootNode, String nodeName){
        int nNodes = rootNode.getLength();
        for (int i = 0; i < nNodes; i++){
            if (rootNode.item(i).getNodeName().equalsIgnoreCase(nodeName)){
                return (IIOMetadataNode) rootNode.item(i);
            }
        }
        IIOMetadataNode node = new IIOMetadataNode(nodeName);
        rootNode.appendChild(node);
        return(node);
    }

    public void writeToSequence(RenderedImage img) throws IOException {
        writer.writeToSequence(new IIOImage(img, null, metadata), params);
    }

    public void close() throws IOException {
        writer.endWriteSequence();
    }

}