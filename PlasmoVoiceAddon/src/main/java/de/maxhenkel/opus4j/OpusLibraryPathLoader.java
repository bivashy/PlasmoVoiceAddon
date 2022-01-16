package de.maxhenkel.opus4j;

import com.sun.jna.Platform;

public class OpusLibraryPathLoader {
	private OpusLibraryPathLoader() {
	}

	public static String getPath() {
		String platform = getPlatform();
		String path = String.format("/natives/%s/libopus.%s", platform, getExtension(platform));
		return path;
	}

	public static String getPlatform() {
		return Platform.RESOURCE_PREFIX;
	}

	public static String getExtension(String platform) {
		switch (platform) {
		case "darwin":
			return "dylib";
		case "win32-x86":
		case "win32-x86-64":
			return "dll";
		case "linux-arm":
		case "linux-aarch64":
		case "linux-x86":
		case "linux-x86-64":
		default:
			return "so";
		}
	}

}