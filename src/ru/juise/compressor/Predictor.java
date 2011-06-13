package ru.juise.compressor;

/**
 * User: juise
 * Date: 04.06.11
 */

import java.io.*;

import java.util.Set;
import java.util.List;
import java.util.HashSet;
import java.util.Arrays;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Predictor {
	String BAD_FILE = "Directory {} does not exist or it's file";
	String AMOUNT_SIZE = "Amount size ~{} Gb of {} files";

	File root = null;

	Long size = 0L;
	Long count = 0L;

	Set<String> extSet = new HashSet<String>();

	Logger logger = LoggerFactory.getLogger(Predictor.class);


	public Predictor(String name) {
		root = new File(name);

		if (root.exists() && root.isDirectory()) {
			doPredict(root);

			logger.info(AMOUNT_SIZE, size / 1024 / 1024 / 1024, count);
		} else {
			logger.debug(BAD_FILE, name);
		}
	}

	private void doPredict(File root) {
		File[] files = root.listFiles();

		if (files != null) {
			List<File> filesList = Arrays.asList(files);

			for (File file: filesList) {
				if (file.isFile()) {
					count++;
					size += file.length();

					addExts(file);
				} else {
					doPredict(file);
				}
			}
		}
	}

	private void addExts(File file) {
		int extIdx = 0;
		String fileName = null;

		fileName = file.getName();
		extIdx = fileName.lastIndexOf(".");

		if (extIdx > 0) {
			extSet.add(fileName.substring(extIdx));
		}
	}

	public void getExts() {
		Iterator<String> extIter = extSet.iterator();

		while (extIter.hasNext()) {
			logger.info(extIter.next());
		}
	}
}