package edu.wsu.bean_582_2024.ApartmentFinder.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Objects;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Lazy
public class TableController {
  @Autowired
  @RequestMapping("/admin/tables")
  public String tables(DataSource dataSource) {
    String createTableSql;
    try {
      createTableSql = Files.readString(
          Paths.get(
              Objects.requireNonNull(
                      getClass()
                          .getResource("schema.sql")
                  )
                  .toURI()
          ),
          StandardCharsets.UTF_8);
    } catch (IOException | URISyntaxException e) {
      throw new RuntimeException(e);
    }

    try {
      dataSource.getConnection().createStatement().execute(createTableSql);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return "OK.";
  }
}
