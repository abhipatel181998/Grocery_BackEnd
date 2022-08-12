package com.grocery.service;

import org.springframework.web.multipart.MultipartFile;

import com.grocery.model.Attachment;

public interface AttachmentService {

	Attachment saveAttachment(MultipartFile file) throws Exception;

	Attachment getAttachment(Long fileId) throws Exception;

}
