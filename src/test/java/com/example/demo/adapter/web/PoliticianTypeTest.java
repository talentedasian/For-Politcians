package com.example.demo.adapter.web;

import com.example.demo.domain.politicians.Politicians;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PoliticianTypeTest {

    @Test
    public void shouldMapToPresidentialType() throws Exception{
        assertThat(Politicians.Type.valueOf("PRESIDENTIAL"))
                .isEqualTo(Politicians.Type.PRESIDENTIAL);
    }

}
