package com.catdog.comerce.dto.response;

import java.time.LocalDateTime;

public record CustomeErrorResponse(LocalDateTime datetime, String message, String path) {

}
