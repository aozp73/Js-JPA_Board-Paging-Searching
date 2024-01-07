package com.example.demo.module.user;


import com.example.demo.exception.statuscode.CustomException;
import com.example.demo.module.user.dto.Join_InDTO;
import com.example.demo.module.user.enums.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public void save(Join_InDTO joinInDTO) {
        log.debug("aa = {}", UserRole.COMMON);
        // 장난 치는거 체크
        if(userRepository.findByEmail(joinInDTO.getEmail()).isPresent()) {
            throw new CustomException("해당 이메일로 가입할 수 없습니다.");
        }

        try {
            userRepository.save(joinInDTO.toEntity(passwordEncoder));
        } catch (Exception exception) {
            throw new CustomException("회원가입에 실패하였습니다.");
        }
    }

    @Transactional(readOnly = true)
    public Boolean emailCheck(String email) {

        return userRepository.findByEmail(email).isEmpty();
    }
}
