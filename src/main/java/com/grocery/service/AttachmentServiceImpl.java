package com.grocery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.grocery.model.Attachment;
import com.grocery.repository.AttachmentRepository;

@Service
public class AttachmentServiceImpl implements AttachmentService {

	@Autowired
	private AttachmentRepository attachmentRepository;

	@Override
	public Attachment saveAttachment(MultipartFile file) throws Exception {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			if (fileName.contains("..")) {
				throw new Exception("Filename contains invalid path sequence " + fileName);
			}

			Attachment attachment = new Attachment(fileName, file.getContentType(), file.getBytes());

			return attachmentRepository.save(attachment);

		} catch (Exception e) {
			throw new Exception("Could not save file: " + fileName);
		}
	}

	@Override
	public Attachment getAttachment(Long fileId) throws Exception {
		return attachmentRepository.findById(fileId)
				.orElseThrow(() -> new Exception("File not found with Id: " + fileId));
	}

}
