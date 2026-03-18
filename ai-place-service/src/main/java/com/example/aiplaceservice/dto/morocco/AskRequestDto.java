package com.example.aiplaceservice.dto.morocco;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AskRequestDto {

    private String question;
    private Integer top_k; // nombre de résultats à retourner
}
