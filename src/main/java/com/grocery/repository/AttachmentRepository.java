package com.grocery.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.grocery.model.Attachment;

@Repository
public interface AttachmentRepository extends CrudRepository<Attachment, Long> {

}
