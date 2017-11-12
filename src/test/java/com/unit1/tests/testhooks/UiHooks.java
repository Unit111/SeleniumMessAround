package com.unit1.tests.testhooks;

import com.google.inject.Inject;
import com.unit1.core.Context;
import com.unit1.core.WikipediaTasks;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.runtime.java.guice.ScenarioScoped;

import java.io.IOException;

/**
 * Created by Unit 1 on 07-Jul-17.
 */
@ScenarioScoped
public class UiHooks {

    private WikipediaTasks wikipediaTasks;
    private Context context;

    @Inject
    public UiHooks(final WikipediaTasks wikipediaTasks,
                   final Context context) {
        this.wikipediaTasks = wikipediaTasks;
        this.context = context;
    }


    @Before(value = "@Wiki")
    public void onWikiHomePage() {
        wikipediaTasks.openWikipediaHomePage();
    }

    @Before
    public void before(Scenario scenario) {
        context.setScenario(scenario);
    }

    @After(value = "@Wiki")
    public void closeBrowser() throws IOException {
        wikipediaTasks.closeBrowser();
        Runtime.getRuntime().exec("taskkill /F /IM geckodriver.exe /T");
    }
}
