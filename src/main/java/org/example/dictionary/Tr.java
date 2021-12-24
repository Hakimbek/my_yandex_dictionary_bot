package org.example.dictionary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Tr {
    private String text;

    private String pos;

    private List<Syn> syn;

    private List<Mean> mean;

    private List<Ex> ex;
}
