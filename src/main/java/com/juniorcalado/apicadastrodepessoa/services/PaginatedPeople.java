package com.juniorcalado.apicadastrodepessoa.services;

import com.juniorcalado.apicadastrodepessoa.domain.People;

import java.util.List;

public class PaginatedPeople {

    private List<People> data;
    private int current_page;
    private int last_page;
    private int per_page;
    private long total;

    public PaginatedPeople(List<People> data, int current_page, int last_page, int per_page, long total) {
        this.data = data;
        this.current_page = current_page;
        this.last_page = last_page;
        this.per_page = per_page;
        this.total = total;
    }

    public List<People> getData() {
        return data;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public int getLast_page() {
        return last_page;
    }

    public int getPer_page() {
        return per_page;
    }

    public long getTotal() {
        return total;
    }
}
