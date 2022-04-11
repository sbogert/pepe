package com.example.pepe.test;

import cucumber.api.CucumberOptions;

import cucumber.api.CucumberOptions
@CucumberOptions(
        features = "features",
        glue = "com.sniper.bdd.cucumber.steps",
        tags = "@e2e", "@smoke"
)

@SuppressWarnings("unused")
class CucumberTestCase{

}
