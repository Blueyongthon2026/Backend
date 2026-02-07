package com.cau.artchive.dto;

import com.cau.artchive.entity.Category;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PostRequestDto {
    private String title, content, workName, location;
    private Category category;
    private Double rating;
    private LocalDate viewingDate;
}
