package com.spring.management.sittings;

import com.spring.management.sittings.helpers.TokenConfiguration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "user-management")
public class Configurations {

    private TokenConfiguration token;
}
