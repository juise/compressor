package ru.juise.compressor;

/**
 * User: juise
 * Date: 04.06.11
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main {	
	public static void main(String args[]) {
		Logger logger = LoggerFactory.getLogger(Main.class);

		String FILENAME = "/Users/juise/cmp";
		if (args.length > 0) {
			FILENAME = args[0];
		}

		logger.info("Start");

		try {
			// Do prediction
			//Predictor predictor = new Predictor(FILENAME);
			//predictor.getExts();

			// Do compression
			new Command(FILENAME);
		} catch(Throwable e) {
			e.printStackTrace();
		}
		
		logger.info("Stop");
	}
}
