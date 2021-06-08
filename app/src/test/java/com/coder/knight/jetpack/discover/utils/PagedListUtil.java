package com.coder.knight.jetpack.discover.utils;

import androidx.paging.PagedList;

import org.mockito.stubbing.Answer;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PagedListUtil {
    public static <T> PagedList mockPagedList(List<T> list) {
        PagedList pagedList = mock(PagedList.class);
        Answer<T> answer = invocation -> {
            Integer index = (Integer) invocation.getArguments()[0];
            return list.get(index);
        };
        
        when(pagedList.get(anyInt())).then(answer);
        when(pagedList.size()).thenReturn(list.size());

        return pagedList;
    }
}
