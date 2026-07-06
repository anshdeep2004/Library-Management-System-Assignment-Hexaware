package com.library.dao;

import com.library.model.Author;

public interface AuthorDao {
    Author getAuthoById(long authorId);
}
