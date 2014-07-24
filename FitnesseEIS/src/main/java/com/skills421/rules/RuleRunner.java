package com.skills421.rules;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.decisiontable.InputType;
import org.drools.decisiontable.SpreadsheetCompiler;
import org.drools.definition.KnowledgePackage;
import org.drools.io.Resource;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;

import com.skills421.managers.FileManager;

public class RuleRunner
{
	private static Logger logger = LogManager.getLogger(RuleRunner.class);

	private KnowledgeBase kbase;
	private KnowledgeBuilder kbuilder;

	public RuleRunner()
	{
		kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
	}

	public List<String> buildKnowledgeBase(String[] decisionTables, String[] drlFiles)
	{
		List<String> buildErrors = new ArrayList<String>();

		if (decisionTables != null)
		{
			logger.info("*** Adding Decision Tables ***");

			for (int i = 0; i < decisionTables.length; i++)
			{
				String decisionTable = decisionTables[i];
				Resource xlsResource = ResourceFactory.newClassPathResource(decisionTable, RuleRunner.class);

				kbuilder.add(xlsResource, ResourceType.DTABLE);
			}
		}

		if (drlFiles != null)
		{
			logger.info("*** Adding DRL Files ***");

			for (int i = 0; i < drlFiles.length; i++)
			{
				try
				{
					String drlFile = FileManager.convertRulePackageAndFileToFullPathname(drlFiles[i]);
					if (drlFile.startsWith(File.separator) || drlFile.toUpperCase().startsWith("C:"))
					{
						kbuilder.add(ResourceFactory.newFileResource(new File(drlFile)), ResourceType.DRL);
					}
					else
					{
						kbuilder.add(ResourceFactory.newClassPathResource(drlFile, RuleRunner.class), ResourceType.DRL);
					}
				}
				catch (IOException e)
				{
					buildErrors.add(e.getMessage());
				}

			}
		}

		logger.info("*** Checking for Errors ***");
		if (kbuilder.hasErrors())
		{
			logger.info("*** !!! Build Errors !!! ***");
			for (KnowledgeBuilderError error : kbuilder.getErrors())
			{
				logger.info(error);
				buildErrors.add(error.toString());
			}
		}

		logger.info("*** Loading KnowledgePackages ***");
		Collection<KnowledgePackage> pkgs = kbuilder.getKnowledgePackages();
		kbase.addKnowledgePackages(pkgs);

		return buildErrors;
	}

	public List<String> buildKnowledgeBase(List<String> decisionTables, List<String> rules)
	{
		String[] decisionTablesArr = null;
		String[] rulesArr = null;

		if (decisionTables != null)
			decisionTablesArr = decisionTables.toArray(new String[decisionTables.size()]);
		if (rules != null)
			rulesArr = rules.toArray(new String[rules.size()]);

		return this.buildKnowledgeBase(decisionTablesArr, rulesArr);
	}

	public static String getDRLFromDecisionTable(String decisionTable)
	{
		String drlContent = null;

		try
		{
			SpreadsheetCompiler sc = new SpreadsheetCompiler();

			Resource xlsResource = ResourceFactory.newClassPathResource(decisionTable, RuleRunner.class);
			drlContent = sc.compile(xlsResource.getInputStream(), InputType.XLS);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return drlContent;
	}
	
	public StatefulKnowledgeSession newKnowledgeSession()
	{
		return kbase.newStatefulKnowledgeSession();
	}

	public static void logDrlContent(String decisionTable) throws IOException
	{
		String drlContent = RuleRunner.getDRLFromDecisionTable(decisionTable);
		logger.info("DRLContent: " + drlContent);
	}
}