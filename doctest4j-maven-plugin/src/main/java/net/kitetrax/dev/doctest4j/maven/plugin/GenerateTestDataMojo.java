package net.kitetrax.dev.doctest4j.maven.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "generateTestData", defaultPhase = LifecyclePhase.GENERATE_TEST_RESOURCES,
    requiresProject = true)
public class GenerateTestDataMojo extends AbstractMojo {

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    getLog().info("this is where the test data will be generated");
  }

}
