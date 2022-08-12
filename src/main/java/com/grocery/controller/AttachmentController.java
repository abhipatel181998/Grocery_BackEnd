package com.grocery.controller;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.grocery.model.Attachment;
import com.grocery.responseData.AttachmentResponse;
import com.grocery.service.AttachmentService;

@RestController
@RequestMapping("/api")
public class AttachmentController {
	@Autowired
	AttachmentService attachmentService;

	@PostMapping("/upload")
	public AttachmentResponse uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
		Attachment attachment = null;

		attachment = attachmentService.saveAttachment(file);

		String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/download/")
				.path(attachment.getId().toString()).toUriString();

		return new AttachmentResponse(attachment.getFileName(), downloadURL, file.getContentType(), file.getSize());
	}

	@GetMapping("/download/{fileId}")
	public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) throws Exception {
		Attachment attachment = attachmentService.getAttachment(fileId);

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(attachment.getFileType()))
				.header(CONTENT_DISPOSITION, "attachment; filename=\"" + attachment.getFileName() + "\"")
				.body(new ByteArrayResource(attachment.getData()));
	}
}
