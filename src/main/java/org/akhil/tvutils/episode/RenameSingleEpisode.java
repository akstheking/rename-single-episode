package org.akhil.tvutils.episode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class RenameSingleEpisode {
	
	private static final Logger LOGGER = Logger.getLogger(RenameSingleEpisode.class);

	public static Map<String, String> keywords = new HashMap<>();
	static {
		keywords.put("hdtv-lol", "HDTV.X264-LOL");
		keywords.put("hdtv.x264-lol", "HDTV.X264-LOL");
	}

	public static void main(String... args) {
		System.out.println(Arrays.toString(args));
		LOGGER.info(Arrays.toString(args));
		String curDir = System.getProperty("user.dir").replaceAll("\\\\", "/");
		LOGGER.info(curDir);
		if(args.length == 0) {
			System.out.print("No Input");	
			return;
		}
		//String episodeName = "arrow.321.hdtv-lol.mp4";
		
		Path inputPath = Paths.get(args[0]);
		String episodeName = inputPath.getFileName().toString();
		StringBuilder sb = new StringBuilder();
		String parts[] = episodeName.split("\\.");
		LOGGER.debug(Arrays.toString(parts));
		for (int i = 0; i < parts.length-1; i++) {
			sb.append(capitalize(parts[i])).append(".");
		}
		sb.append(parts[parts.length - 1]);
		LOGGER.info(sb.toString());
		Path outputPath = Paths.get(inputPath.getParent().toString() + "/" + sb.toString());
		LOGGER.info("Input File : " + inputPath);
		LOGGER.info("Output File : " + outputPath);
		
		try {
			Files.move(inputPath, outputPath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			LOGGER.error("Error in renaing", e);
		}
			
	}

	public static String capitalize(String s) {
		if (keywords.containsKey(s)) {
			return keywords.get(s);
		}
		if(s.matches("\\d+")) {
			String season = s.substring(0, 1);
			String episode = s.substring(1);
			return "S" + convertToInt(season) + "E" + episode;
			
		}
		if (s.length() > 0) {
			return s.substring(0, 1).toUpperCase() + s.substring(1);
		}
		return s;
	}
	
	public static String convertToInt(String s) {
		int num = Integer.parseInt(s);
		if(num < 9) {
			return 0 + s;
		} 
		return s;
	}

}
