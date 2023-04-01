package com.user.management.model.bundle;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BundleErrorMessage {

    @JsonProperty("arabic_message")
    private String arabicMessage;

    @JsonProperty("english_message")
    private String englishMessage;
}
