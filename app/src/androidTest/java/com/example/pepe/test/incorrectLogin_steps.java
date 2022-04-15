package com.example.pepe.test;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class incorrectLogin_steps {
    @When("^I enter invalid username <email>$")
    public void iEnterInvalidUsernameEmail() {
    }

    @And("^I enter valid password <password>$")
    public void iEnterValidPasswordPassword() {
    }

    @Then("^I get incorrect login notification$")
    public void iGetIncorrectLoginNotification() {
    }
}
