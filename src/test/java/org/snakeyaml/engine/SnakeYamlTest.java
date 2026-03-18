package org.snakeyaml.engine;

import org.snakeyaml.engine.v2.api.*;
import org.snakeyaml.engine.v2.api.lowlevel.Compose;
import org.snakeyaml.engine.v2.nodes.MappingNode;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class SnakeYamlTest {
  public static void main(String[] args) throws IOException {

    InputStream resource =
        SnakeYamlTest
          .class
          .getClassLoader()
          .getResourceAsStream("input.yml");

    LoadSettings settings = LoadSettings.builder().setParseComments(true).build();
    Compose compose = new Compose(settings);
    MappingNode mappingNode = (MappingNode) compose.composeInputStream(resource).get();

    DumpSettings dumpSettings = DumpSettings.builder().setIndicatorIndent(2)
        .setIndentWithIndicator(true).setDumpComments(true).build();
    Dump dump = new Dump(dumpSettings);

    Path outputPath = Path.of("output.yml");
    OutputStream outputStream = Files.newOutputStream(outputPath);
    YamlOutputStreamWriter yamlOutputStreamWriter =
        new YamlOutputStreamWriter(outputStream, StandardCharsets.UTF_8);

    dump.dumpNode(mappingNode, yamlOutputStreamWriter);
  }
}
