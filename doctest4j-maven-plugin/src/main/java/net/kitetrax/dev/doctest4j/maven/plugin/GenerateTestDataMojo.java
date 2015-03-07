package net.kitetrax.dev.doctest4j.maven.plugin;

import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Mojo(name = "generateTestData", defaultPhase = LifecyclePhase.GENERATE_TEST_RESOURCES,
    requiresProject = true)
public class GenerateTestDataMojo extends AbstractMojo {

  private static final String JAVA_FILES = "**\\/*.java";

  private static final String EMPTYSTRING = "";

  @Parameter(defaultValue = "${project}", required = true, readonly = true)
  private MavenProject project;

  @Parameter(defaultValue = "${project.compileSourceRoots}", required = true, readonly = true)
  private List<String> compileSourceRoots;

  @Parameter(property = "encoding", defaultValue = "${project.build.sourceEncoding}")
  private String encoding;

  @Parameter(defaultValue = "${project.build.directory}/doctest4j/", required = true,
      readonly = true)
  private File generatedResourcesDirectory;

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    generatedResourcesDirectory.delete();
    Resource generatedResources = new Resource();
    generatedResources.setDirectory(generatedResourcesDirectory.getAbsolutePath().substring(
        project.getBasedir().getAbsolutePath().length()));
    project.addTestResource(generatedResources);

    List<File> javaFiles;
    try {
      javaFiles = getJavaFiles();
    } catch (IOException e) {
      throw new MojoExecutionException("IOException: " + e.getMessage(), e);
    }

    if (javaFiles.isEmpty()) {
      getLog().info("No files ending with .java found in sources directories.");
    } else {
      Collection<JavaClass> javaClasses;
      try {
        javaClasses = getJavaClasses(javaFiles);
      } catch (IOException e) {
        throw new MojoExecutionException("IOException: " + e.getMessage(), e);
      }
      getLog().info("Found " + javaClasses.size() + " java classes in sources directories.");

      for (JavaClass javaClass : javaClasses) {
        String aggregatedComments = aggregateComments(javaClass);
        if (!EMPTYSTRING.equals(aggregatedComments)) {
          try {
            generateDataFile(javaClass, aggregatedComments);
          } catch (IOException e) {
            throw new MojoExecutionException("IOException: " + e.getMessage(), e);
          }
        }
      }
    }

  }

  private List<File> getJavaFiles() throws IOException {
    List<File> javaFiles = new ArrayList<>();
    for (String sourceRoot : compileSourceRoots) {
      File directory = new File(sourceRoot);
      if (directory.isDirectory()) {
        javaFiles.addAll(FileUtils.getFiles(directory, JAVA_FILES, null, true));
      } else {
        getLog().debug("Source folder " + directory + " does not exists and has been ignored.");
      }
    }
    return javaFiles;
  }

  private Collection<JavaClass> getJavaClasses(List<File> javaFiles) throws IOException {
    JavaProjectBuilder builder = new JavaProjectBuilder();
    builder.setEncoding(encoding);

    for (File file : javaFiles) {
      builder.addSource(file);
    }

    return builder.getClasses();
  }

  private String aggregateComments(JavaClass javaClass) {
    StringBuilder sb = new StringBuilder();
    sb.append(javaClass.getComment());
    sb.append('\n');
    for (JavaMethod javaMethod : javaClass.getMethods()) {
      sb.append(javaMethod.getComment());
      sb.append('\n');
    }
    sb.deleteCharAt(sb.length() - 1);
    return sb.toString();
  }

  private void generateDataFile(JavaClass javaClass, String aggregateComments) throws IOException {
    getLog().debug("Generating test data for class " + javaClass.getFullyQualifiedName());
    File file =
        new File(generatedResourcesDirectory, javaClass.getFullyQualifiedName().replace('.', '/')
            + ".dt4j");
    getLog().debug("Write file with test data into " + file.getAbsolutePath());
    file.getParentFile().mkdirs();
    FileUtils.fileWrite(file, encoding, aggregateComments);
  }

}
