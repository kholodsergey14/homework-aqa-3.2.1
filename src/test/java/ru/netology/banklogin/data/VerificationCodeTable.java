package ru.netology.banklogin.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerificationCodeTable {
    private String id;
    private String user_id;
    private String code;
    private String created;
}