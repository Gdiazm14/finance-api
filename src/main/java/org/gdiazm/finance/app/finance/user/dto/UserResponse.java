package org.gdiazm.finance.app.finance.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
public class UserResponse {
private UUID id;
private String name;
private String email;
private OffsetDateTime createdAt;
}
