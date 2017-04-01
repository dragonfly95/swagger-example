package com.example;

import java.io.File;
import java.io.IOException;

// 이부분 추가하셔야 합니다.
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.github.robwin.swagger2markup.Swagger2MarkupConverter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import springfox.documentation.staticdocs.Swagger2MarkupResultHandler;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={DemoApplication.class, SwaggerConfig.class})
@WebAppConfiguration
public class Swagger2MarkupTest {

    private static final String API_URI = "/v2/api-docs";
    String projectDir = "";

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setup() throws IOException {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    public void convertSwaggerToAsciiDoc() throws Exception {
        Swagger2MarkupResultHandler.Builder builder = Swagger2MarkupResultHandler
            .outputDirectory(outputDirForFormat("asciidoc"));

        mockMvc.perform(get(API_URI)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(Swagger2MarkupResultHandler
                    .outputDirectory("src/main/asciidoc/generated").build())
            .andExpect(status().isOk());


//      Swagger2MarkupConverter.from("http://localhost:8080/v2/api-docs").build()
//              .intoFolder("src/docs/asciidoc/generated");

    }

    private String outputDirForFormat(String format) throws IOException {
      projectDir = Swagger2MarkupTest.class.getResource("").getPath();
      System.out.println(projectDir);

        return new File(projectDir, "main/" + format + "/generated").getAbsolutePath();
    }
}