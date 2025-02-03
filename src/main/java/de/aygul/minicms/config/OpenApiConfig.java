package de.aygul.minicms.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(contact = @Contact(name = "Aygul", email = "aygul.fortelka@gmx.net"), description =
        "Mini-CMS API", title = "OpenAPI Specification for Mini-CMS"), servers = {@Server(description = "Local ENV", url
        = "http://localhost:8080/")})
public class OpenApiConfig {

}

