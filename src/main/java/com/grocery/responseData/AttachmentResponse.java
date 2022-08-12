package com.grocery.responseData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentResponse {
	
	private String fileName;
	private String downloadURL;
	private String fileType;
	private Long fileSize;
}
