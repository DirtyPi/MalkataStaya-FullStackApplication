package fontys.sem3.school.DTO.WS;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class Greeting {

    private String content;

    public String getContent() {
        return content;
    }
}
