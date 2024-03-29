package fisa.dev.homebanker.global.util.pagination;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PaginationResMaker {

  public PaginationDTO makePaginationDto(Page<?> pages) {

    Long totalElements = pages.getTotalElements(); //전체 아이템 수
    Integer pageNumber = pages.getPageable().getPageNumber(); //현재 페이지 번호
    Integer totalPages = pages.getTotalPages(); //전체 페이지 번호

    return PaginationDTO.builder()
        .totalElements(totalElements)
        .totalPages(totalPages)
        .pageNumber(pageNumber)
        .build();
  }

}
