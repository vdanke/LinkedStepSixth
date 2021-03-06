package org.step.linked.step.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.HashSet;
import java.util.Set;

/*
        val some = "new string"; неизменяемая
        var some = "some"; изменяемая
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"id", "username"})
@EqualsAndHashCode(of = {"id"})
@Builder
@Document(value = "user")
public class User {

    @MongoId
    private String id;
    @Indexed(unique = true)
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;
    private Set<Authorities> authoritiesList = new HashSet<>();
    private Set<String> providers = new HashSet<>();
    private String gender;
    private String fullName;
    private String locale;
    private String imageUrl;
}
