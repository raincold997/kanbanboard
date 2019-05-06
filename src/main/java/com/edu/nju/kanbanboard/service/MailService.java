package com.edu.nju.kanbanboard.service;

public interface MailService {
    public boolean sendInviteMail(Long ownerId,String targetEmail);
}
