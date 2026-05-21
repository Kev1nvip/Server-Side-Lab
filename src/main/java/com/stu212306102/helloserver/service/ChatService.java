package com.stu212306102.helloserver.service;

import com.stu212306102.helloserver.dto.ChatRequestDTO;
import com.stu212306102.helloserver.vo.ChatResponseVO;

public interface ChatService {
    ChatResponseVO chat(ChatRequestDTO requestDTO);
}