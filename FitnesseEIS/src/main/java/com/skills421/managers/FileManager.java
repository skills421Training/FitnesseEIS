package com.skills421.managers;

import java.io.File;
import java.io.IOException;

public class FileManager
{
	public static String replaceAllDotsWithPathSeparator(String packagename)
	{
		// if we are on a unix box, replace all .'s in the package name with /'s
		// if we are on a windows box replace all .'s in the package name with
		// \'s

		if (File.separator.equals("/"))
		{
			packagename = packagename.replaceAll("\\.", "/");
		}
		else
		{
			packagename = packagename.replaceAll("\\.", "\\\\");
		}

		return packagename;
	}

	public static String convertRulePackageAndFileToFullPathname(String packagename, String filename)
	{
		// turn packagename to file path
		StringBuilder fullpathnameSB = new StringBuilder();

		fullpathnameSB.append(FileManager.replaceAllDotsWithPathSeparator(packagename))
		.append(File.separator)
		.append(filename);

		return fullpathnameSB.toString();
	}

	public static String convertRulePackageAndFileToFullPathname(String packageAndFilename) throws IOException
	{
		// in the event that we get passed a filepath that actually exists
		// just return it

		if (new File(packageAndFilename).exists())
			return packageAndFilename;
		
		// remove the extension
		
		if(packageAndFilename.indexOf(".")==-1)
		{
			throw new IOException("No file extension provided for "+packageAndFilename);
		}
		
		int extPstn = packageAndFilename.lastIndexOf(".");
		String packagename = packageAndFilename.substring(0,extPstn);
		String ext = packageAndFilename.substring(extPstn);
		
		StringBuilder fullpathnameSB = new StringBuilder();

		fullpathnameSB.append(FileManager.replaceAllDotsWithPathSeparator(packagename))
		.append(ext);

		return fullpathnameSB.toString();
	}
}