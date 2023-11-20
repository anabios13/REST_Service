package by.anabios13.util;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.testcontainers.junit.jupiter.Container;

public class PostgreSQLContainer {
    public static final String INIT_SQL = "sql/schema.sql";
    private static int containerPort = 5432;
    private static int localPort = 5432;

    @Container
    public static org.testcontainers.containers.PostgreSQLContainer<?> container = new org.testcontainers.containers.PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("aston_rest_service_db")
            .withUsername(PropertiesUtil.getProperties("dataSource.user"))
            .withPassword(PropertiesUtil.getProperties("dataSource.password"))
            .withEnv("POSTGRES_PASSWORD", PropertiesUtil.getProperties("dataSource.password"))
            .withExposedPorts(containerPort)
            .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
                    new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(localPort), new ExposedPort(containerPort)))
            ))
            .withInitScript(INIT_SQL);
}
