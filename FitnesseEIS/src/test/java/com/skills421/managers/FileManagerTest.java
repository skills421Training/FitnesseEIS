package com.skills421.managers;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class FileManagerTest
{

	@Test
	public void testReplaceAllDotsWithPathSeparator()
	{
		String packagename = "com.skills421.rules";

		String path = FileManager.replaceAllDotsWithPathSeparator(packagename);

		assertEquals("com/skills421/rules", path);
	}

	@Test
	public void testConvertPackageNameToDrlFilePathOneArg()
	{
		String packagename = "com.skills421.rules.test1.drl";

		try
		{
			String drlFilePath = FileManager.convertRulePackageAndFileToFullPathname(packagename);

			assertEquals("com/skills421/rules/test1.drl", drlFilePath);
		}
		catch (IOException e)
		{
			fail(e.getMessage());
		}
	}

	@Test
	public void testConvertPackageNameToDrlFilePathTwoArgs()
	{
		String packagename = "com.skills421.rules";
		String filename = "test1.drl";

		String drlFilePath = FileManager.convertRulePackageAndFileToFullPathname(packagename, filename);

		assertEquals("com/skills421/rules/test1.drl", drlFilePath);
	}

}
