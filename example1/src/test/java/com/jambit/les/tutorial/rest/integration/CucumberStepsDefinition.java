package com.jambit.les.tutorial.rest.integration;

import com.jambit.les.tutorial.rest.Application;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ContextConfiguration(classes = Application.class, loader = SpringApplicationContextLoader.class)
@WebIntegrationTest
@RunWith(SpringJUnit4ClassRunner.class)
public @interface CucumberStepsDefinition {
}
