package ru.juise.compressor;

/**
 * User: juise
 * Date: 06.06.11
 */

import java.io.File;
import java.util.List;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Command {
	Logger logger = LoggerFactory.getLogger(Main.class);

	long size = 0;

	private Integer getType(String name) {
		if (name.endsWith("psd")) return 0;
		if (name.endsWith("bmp")) return 1;
		if (name.endsWith("jpg")) return 2;

		return -1;
	}
	
	public Command(String name, long size) {
		File root = new File(name);

		this.size = size;

		compression(root);
	}

	private boolean compress(File file) {
		Integer type = getType(file.getName());

		if (type > -1) {
			Compressor compressor = new Compressor(size);
			return compressor.convert(file, type);
		}

		return false;
	}

	private void compression(File root) {
		File[] files = root.listFiles();

		if (files != null) {
			List<File> filesList = Arrays.asList(files);

			for (File file: filesList) {
				if (file.isFile()) {
					if (compress(file)) {
						logger.info("Success dir {} file {}", file.getAbsolutePath(), file.getName());
					} else {
						logger.debug("FAIL dir {} file {}", file.getAbsolutePath(), file.getName());
					}
				} else {
					compression(file);
				}
			}
		}
	}
}
