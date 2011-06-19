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

		logger.info("Start");

		if (args.length == 0) {
			View view = new View();
		} else {

			String FILENAME = "";
			if (args.length > 1) {
				FILENAME = args[0];
			}

			long SIZE = 1024 * 800;
			if (args.length > 2) {
				SIZE = Integer.valueOf(args[1]);
			}

			try {
				new Command(FILENAME, SIZE);
			} catch(Throwable e) {
				e.printStackTrace();
			}

			logger.info("Stop");

		}
		
	}
}
