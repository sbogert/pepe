package com.example.pepe.test;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;

@RunWith(Cucumber.class)

@CucumberOptions(features="features", glue="steps")
public class Runner {

}
