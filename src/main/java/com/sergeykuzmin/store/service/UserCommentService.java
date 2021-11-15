package com.sergeykuzmin.store.service;

import com.sergeykuzmin.store.models.UserComment;
import com.sergeykuzmin.store.repository.UserCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCommentService {

    @Autowired
    UserCommentRepository repository;

    public void saveComment(UserComment userComment){

           repository.save(userComment);
    }


}
