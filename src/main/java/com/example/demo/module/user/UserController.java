package com.example.demo.module.user;

import com.example.demo.module.user.dto.Join_InDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 회원가입
     */
    // 페이지
    @GetMapping("/joinForm")
    public String joinForm(@ModelAttribute("joinInDTO") Join_InDTO joinInDTO) {
        log.debug("GET - 회원가입 페이지");
        return "pages/user/joinForm";
    }

    // 회원가입
    @PostMapping("/join")
    public String join(@ModelAttribute("joinInDTO") @Valid Join_InDTO joinInDTO, BindingResult bindingResult) {
        log.debug("POST - 회원가입");
        // 유효성 체크 실패 시, 필드값 그대로 담아서 페이지 응답
        if (bindingResult.hasErrors()){
            return "pages/user/joinForm";
        }

        userService.save(joinInDTO);
        return "redirect:/loginForm";
    }

    // 이메일 체크
    @ResponseBody
    @GetMapping("/emailCheck")
    public ResponseEntity<?> emailCheck(@RequestParam String email) {
        log.debug("POST - 이메일 체크");
        boolean isValid = userService.emailCheck(email);
        return new ResponseEntity<>(isValid, HttpStatus.OK);
    }


    /**
     * 로그인
     */
    // 페이지
    @GetMapping("/loginForm")
    public String loginForm() {
        log.debug("GET - 로그인 페이지");
        return "pages/user/loginForm";
    }
}
