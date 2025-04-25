package pedrk72.quarkusSocial.rest.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

//@Getter
//@Setter
@Data //Annotations that set for us the getters, setters and constructors methods.
public class CreateUserRequest {

    @NotBlank(message = "Name is required") //To verify if the String is not empty or null
    private String name;

    @NotNull(message = "Age is required") //To verify if the String is not null
    private Integer age;

    // With lombok we don't need anymore to instantiate the gets and setters methods
//    public String getName() {
//        return name;
//    }
//
//    public Integer getAge() {
//        return age;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setAge(Integer age) {
//        this.age = age;
//    }
}
