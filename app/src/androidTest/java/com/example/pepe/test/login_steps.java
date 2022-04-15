package com.example.pepe.test;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class login_steps {
    @Given("^I am on the user login page$")
    public void iAmOnTheUserLoginPage() {
        //onView(withId(R.id.Name));
    }

    @When("^I enter valid username (\\\\S+)$")
    public void iEnterValidEmailEmail(final String username) {
        //onView(withId(R.id.Name)).perform(typeText(username));
    }

    @And("^I enter valid password (\\\\S+)$")
    public void iEnterValidPasswordPassword(final String password) {
        //onView(withId(R.id.Password)).perform(typeText(password));
    }

    @And("^I click sign in button$")
    public void iClickSignInButton() {
        //onView(withId(R.id.Name))
    }

    @Then("^I am directed to maps page$")
    public void iAmDirectedToMapsPage() {
    }
}
