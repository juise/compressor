package ru.juise.compressor;

/**
 * User: juise
 * Date: 11.06.11
 */

import java.io.File;
import java.io.IOException;

import psd.Psd;
import javax.imageio.ImageIO;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public class Converter {
	public static BufferedImage convert(File file, Integer type) {
		BufferedImage inputImage;
		BufferedImage outputImage;

		switch (type) {
			case 0: // PSD
				try {
					Psd psd = new Psd(file);

					inputImage = psd.getImage();
					outputImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

					Graphics2D g = outputImage.createGraphics();
					g.drawImage(inputImage, 0, 0, inputImage.getWidth(), inputImage.getHeight(), 0, 0, inputImage.getWidth(), inputImage.getHeight(), null);
					g.dispose();

					return outputImage;
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 1: // BMP
			case 2: // JPG
				try {
					inputImage = ImageIO.read(file);

					return inputImage;
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			default:
				break;
		}

		return null;
	}
}
