package ru.juise.compressor;

/**
 * User: juise
 * Date: 06.06.11
 */

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;


public class Compressor {
	long size = 0;

	public Compressor(long size) {
		this.size = size;
	}

	public boolean accept(File file) {
		long fileSize = file.length(); 

		if (fileSize > size) {
			return true;
		}

		return false;
	}

	public boolean compress(File file) {
		if (accept(file)) {
			try {
				BufferedImage inputImage = ImageIO.read(file);

				int nw = inputImage.getWidth() - (int)(inputImage.getWidth() * 0.5);
				int nh = inputImage.getHeight() - (int)(inputImage.getHeight() * 0.5);

				BufferedImage outputImage = new BufferedImage(nw, nh, inputImage.getType());

				Graphics2D g = outputImage.createGraphics();

				g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
				g.drawImage(inputImage, 0, 0, nw, nh, 0, 0, inputImage.getWidth(), inputImage.getHeight(), null);
				g.dispose();

				ImageIO.write(outputImage, "jpg", file);

				compress(file);

				return true;
			} catch(Exception e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	public boolean convert(File file, Integer type) {
		try {
			String name = file.getAbsolutePath();
			name = name.substring(0, name.length() - 3).concat("jpg");

			BufferedImage inputImage = Converter.convert(file, type);
			BufferedImage outputImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), inputImage.getType());

			Graphics2D g = outputImage.createGraphics();

			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.drawImage(inputImage, 0, 0, inputImage.getWidth(), inputImage.getHeight(), 0, 0, inputImage.getWidth(), inputImage.getHeight(), null);
			g.dispose();

			file.delete();
			file = new File(name);

			ImageIO.write(outputImage, "jpg", file);

			compress(file);

			return true;
		} catch(Exception e) {
			e.printStackTrace();
		}

		return false;
	}
}
