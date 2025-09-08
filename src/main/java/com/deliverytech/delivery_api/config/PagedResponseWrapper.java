package com.deliverytech.delivery_api.config;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;
import java.util.List;

@Schema(description = "Wrapper for paginated responses")
public class PagedResponseWrapper<T> {
    @Schema(description = "List of items on page")
    private List<T> content;

    @Schema(description = "Pagination info")
    private PageInfo page;

    @Schema(description = "Navigation links")
    private PageLinks links;

    // Default constructor
    public PagedResponseWrapper() {}

    // Constructor that accepts a Page object
    public PagedResponseWrapper(Page<T> page) {
        this.content = page.getContent();
        this.page = new PageInfo(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast());
        this.links = new PageLinks(page);
    }

    // Constructor with custom parameters
    public PagedResponseWrapper(List<T> content, PageInfo page, PageLinks links) {
        this.content = content;
        this.page = page;
        this.links = links;
    }

    @Schema(description = "Pagination info")
    public static class PageInfo {
        @Schema(description = "Num. of page (base 0)", example = "0")
        private int number;

        @Schema(description = "Page size", example = "10")
        private int size;

        @Schema(description = "Num. of elements", example = "50")
        private long totalElements;

        @Schema(description = "Num. of pages", example = "5")
        private int totalPages;

        @Schema(description = "This is the 1st page", example = "true")
        private boolean first;

        @Schema(description = "This is the last page", example = "false")
        private boolean last;

        public PageInfo(int number, int size, long totalElements, int totalPages, boolean first, boolean last) {
            this.number = number;
            this.size = size;
            this.totalElements = totalElements;
            this.totalPages = totalPages;
            this.first = first;
            this.last = last;
        }

        // Getters
        public int getNumber() { return number; }
        public int getSize() { return size; }
        public long getTotalElements() { return totalElements; }
        public int getTotalPages() { return totalPages; }
        public boolean isFirst() { return first; }
        public boolean isLast() { return last; }
        public boolean getFirst() { return first; }
        public boolean getlast() { return last;  }
    }

    @Schema(description = "Navigation links")
    public static class PageLinks {
        @Schema(description = "Link for 1st page")
        private String first;

        @Schema(description = "Link for last page")
        private String last;

        @Schema(description = "Link for next page")
        private String next;

        @Schema(description = "Link for previous page")
        private String prev;

        public PageLinks(Page<?> page) {
            String baseUrl = "/api/requests";
            this.first = baseUrl + "?page=0&size=" + page.getSize();
            this.last = baseUrl + "?page=" + (page.getTotalPages() - 1) + "&size=" + page.getSize();

            if (page.hasNext()) { this.next = baseUrl + "?page=" + (page.getNumber() + 1) + "&size=" + page.getSize(); }
            if (page.hasPrevious()) { this.prev = baseUrl + "?page=" + (page.getNumber() - 1) + "&size=" + page.getSize(); }
        }

        // Getters
        public String getFirst() { return first;  }
        public String getLast() { return last;  }
        public String getNext() { return next;  }
        public String getPrev() { return prev; }

        // Setters
        public void setFirst(String first) { this.first = first;  }
        public void setLast(String last) { this.last = last; }
        public void setNext(String next) { this.next = next; }
        public void setPrev(String prev) { this.prev = prev; }
    }

    // Getters
    public List<T> getContent() { return content; }
    public PageInfo getPage() { return page; }
    public PageLinks getLinks() { return links; }

    // Setters
    public void setContent(List<T> content) { this.content = content; }

    public void setPage(PageInfo page) { this.page = page; }

    public void setLinks(PageLinks links) { this.links = links; }
}