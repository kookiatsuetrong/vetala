/**
 * Collections of common tools.
 */

import java.io.FileReader;

/*
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import javax.imageio.ImageIO;
*/

public class Tool {
	
	/*
	*	Creates a photo of the given string.
	*/
	public static String createPhotoCode(final String text) {
		String name = "photo-code/" + text + ".txt";
		String buffer = "";
		try {
			FileReader fr = new FileReader(name);
			while (true) {
				int k = fr.read();
				if (k == -1) break;
				buffer += (char)k;
			}
		} catch (Exception e) { 
			System.out.println("File not found " + name);
		}
		return buffer;
		/*
		final int height = 16;
		final int width = 42;
		BufferedImage image = new BufferedImage(width, height,
									BufferedImage.TYPE_INT_ARGB);

		Graphics2D g = image.createGraphics();
		g.setFont(new Font( Font.MONOSPACED, Font.PLAIN, height) );
		g.setColor(new Color(64, 64, 64));
		g.drawString(text, 0, height - 2);
		g.dispose();

		try {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			ImageIO.write(image, "PNG", output);
			byte[] b = output.toByteArray();
			return Base64.getEncoder().encodeToString(b);
		} catch (Exception e) { }
		return "";
		*/
	}
	
	public static String randomPhotoCode() {
		int r = (int)(Math.random() * 10000);
		String s = "" + r;
		while (s.length() < 4) { s = "0" + s; }
		return s;
	}
	
	public static String randomActivationCode() {
		int r = (int)(Math.random() * 1000000);
		String s = "" + r;
		while (s.length() < 6) { s = "0" + s; }
		return s;
	}
}
