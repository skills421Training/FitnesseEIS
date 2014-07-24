package com.skills421.model;

public class Person
{
	private String firstname;
	private String lastname;
	private int age;
	private double cmHeight;
	private double kgWeight;

	public Person()
	{

	}

	public Person(String firstname, String lastname, int age, double cmHeight, double kgWeight)
	{
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.age = age;
		this.cmHeight = cmHeight;
		this.kgWeight = kgWeight;
	}

	public String getFirstname()
	{
		return firstname;
	}

	public void setFirstname(String firstname)
	{
		this.firstname = firstname;
	}

	public String getLastname()
	{
		return lastname;
	}

	public void setLastname(String lastname)
	{
		this.lastname = lastname;
	}

	public int getAge()
	{
		return age;
	}

	public void setAge(int age)
	{
		this.age = age;
	}

	public double getCmHeight()
	{
		return cmHeight;
	}

	public void setCmHeight(double cmHeight)
	{
		this.cmHeight = cmHeight;
	}

	public double getKgWeight()
	{
		return kgWeight;
	}

	public void setKgWeight(double kgWeight)
	{
		this.kgWeight = kgWeight;
	}

	@Override
	public String toString()
	{
		return String.format("Person [firstname=%s, lastname=%s, age=%s, cmHeight=%s, kgWeight=%s]", firstname, lastname, age, cmHeight, kgWeight);
	}

}
