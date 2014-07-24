package com.skills421.rules;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.drools.runtime.StatefulKnowledgeSession;
import org.junit.Before;
import org.junit.Test;

import com.skills421.model.Person;

public class RuleRunnerTest
{
	private RuleRunner ruleRunner;
	
	@Before
	public void setupRunner()
	{
		ruleRunner = new RuleRunner();
	}
	
	@Test
	public void testBuildKnowledgeBase()
	{
		String[] decisionTables = null;
		String[] drlFiles = new String[] { "com.skills421.rules.test1.drl" };
		
		List<String> errors = ruleRunner.buildKnowledgeBase(decisionTables, drlFiles);
		
		assertTrue(errors.size()==0);
	}

	@Test
	public void testRunRules()
	{
		String[] decisionTables = null;
		String[] drlFiles = new String[] { "com.skills421.rules.test1.drl" };
		
		List<String> errors = ruleRunner.buildKnowledgeBase(decisionTables, drlFiles);
		
		assertTrue(errors.size()==0);
		
		StatefulKnowledgeSession ksession = ruleRunner.newKnowledgeSession();
		
		Person p1 = new Person("Jon","Doe",18,1.8,80);
		Person p2 = new Person("Jane","Doe",22,1.9,70);
		
		List<String> outputList = new ArrayList<String>();
		
		ksession.setGlobal("OUTPUTLIST", outputList);
		
		ksession.insert(p1);
		ksession.insert(p2);
		
		ksession.fireAllRules();
		
		assertEquals(1, outputList.size());
				
		assertEquals("Jane Doe", outputList.get(0));
	}
}
