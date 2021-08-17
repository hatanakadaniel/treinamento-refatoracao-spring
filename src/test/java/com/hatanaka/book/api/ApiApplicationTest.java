package com.hatanaka.book.api;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "json:build/reports/cucumber/cucumber-api.report.json"}, features = "classpath:features")
public class ApiApplicationTest {
}
