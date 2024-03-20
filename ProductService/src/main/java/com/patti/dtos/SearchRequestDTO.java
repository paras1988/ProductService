package com.patti.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRequestDTO {
    private String title;
    private int itemsPerPage;
    private int pageNumber;

}
