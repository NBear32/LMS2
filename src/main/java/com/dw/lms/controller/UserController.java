package com.dw.lms.controller;

import com.dw.lms.dto.AuthorityUpdateDto;
import com.dw.lms.dto.SessionDto;
import com.dw.lms.dto.UserDto;
import com.dw.lms.model.Category;
import com.dw.lms.model.User;
import com.dw.lms.repository.UserRepository;
import com.dw.lms.service.UserDetailService;
import com.dw.lms.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // Rest API 사용
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    private UserDetailService userDetailService;
    private AuthenticationManager authenticationManager;
    private HttpServletRequest httpServletRequest;
    @Autowired
    UserRepository userRepository;

    public UserController(UserService userService, UserDetailService userDetailService, AuthenticationManager authenticationManager, HttpServletRequest httpServletRequest) {
        this.userService = userService;
        this.userDetailService = userDetailService;
        this.authenticationManager = authenticationManager;
        this.httpServletRequest = httpServletRequest;
    }

    @PostMapping("signup") // "/user/signup"
    public ResponseEntity<String> signUp(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.saveUser(userDto), // 저장 Service 로 보냄
                HttpStatus.CREATED); // CREATED => 201 리턴
    }
//    회원 탈퇴
//    @PostMapping("withdraw")
//    public ResponseEntity<String> deleteUser(@RequestBody UserDto userDto){
//        return new ResponseEntity<>(userService.deleteUser(userDto),HttpStatus.CREATED);
//    }


    @PostMapping("login") // "/user/login" => @PostMapping("login") 에서 "/"를 붙이면 안됨
    public ResponseEntity<String> login(@RequestBody UserDto userDto, HttpServletRequest request) {

        // actYn이 N일때 로그인 불가 2024.06.11 명준호
        Optional<User> userOptional = userRepository.findByUserId(userDto.getUserId());
        if (userOptional.isPresent() && userOptional.get().getActYn().equals("N")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 거부되었습니다. 관리자에게 문의하세요.");
        }

        Authentication authentication = authenticationManager.authenticate( // 보안 관련 코드이므로 바로 리턴
                new UsernamePasswordAuthenticationToken(userDto.getUserId(), userDto.getPassword()) // 토큰을 만듦 (스프링 시큐리티 인증방식)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication); // 보안컨텍스트홀더 인증 저장 (컨텍스트에 인증정보 저장)

        // current 테스트를 위해 추가
        // 세션 생성
        HttpSession session = request.getSession(true); // true: 세션이 없으면 새로 생성 (톰켓이 생성[Http 로 시작되는 모든것])
        // 세션에 인증 객체 저장
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

        return ResponseEntity.ok("Success"); // ok => 200 리턴
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // request: 클라이언트에서 오는 요청, response: 서버에서 오는 응답 결과
        HttpSession session = request.getSession(false); // 세션이 없으면 null, 세션이 있으면 받아옴
        if (session != null) { // 세션이 있으면
            session.invalidate(); // invalidate 함수는 세션을 없애고 세션에 속해있는 값들을 모두 없앤다.
        }
        return "You have been logged out.";
    }

    @GetMapping("current") // 현재 세션의 주인의 정보를 알고 싶을때 사용 // "/user/current"
    public SessionDto getCurrentUser() { // 리턴값 String 에서 SessionDto 로 변경
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("User is not authenticated");
        }

        // 유저네임과 권한 Dto 에 적용
        SessionDto sessionDto = new SessionDto();
        sessionDto.setUserId(authentication.getName());

        Optional<User> userOptional = userRepository.findByUserId(authentication.getName());
        if (userOptional.isPresent()){
            sessionDto.setUserName(userOptional.get().getUserNameKor());
        }

        sessionDto.setAuthority(authentication.getAuthorities());

        //return authentication.getName(); // 없는 경우 무명(anonymous), 있는 경우 유저네임
        return sessionDto; // 없는 경우 무명(anonymous), 있는 경우 유저네임 과 함께 권한도 같이 보냄
    }

    @GetMapping("/id/{userId}")
    public User getUserByUserId(@PathVariable String userId) {
        return userService.getUserByUserId(userId);
    }

//    @GetMapping("/id/{userId}")
//    public UserDto getUserSQLByUserId(@PathVariable String userId) {
//        return userService.getUserSQLByUserId(userId);
//    }

    @GetMapping("/id/name/{userName}")
    public User getUserByUserName(@PathVariable String userName) {
        return userService.getUserByUserNameKor(userName);
    }

//    @GetMapping("/id/{userId}")
//    public UserDto getUserSQLByUserId(@PathVariable String userId) {
//        return userService.getUserSQLByUserId(userId);
//    }

    @GetMapping("admin/getAllUsers")
    @PreAuthorize("hasAnyRole('ADMIN')") // ADMIN 이외에는 사용 못하게
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(),
                HttpStatus.OK);
    }

    @PutMapping("/userset")
    public User SetUserData(@RequestBody User user) {
        return userService.SetUserData(user);
    }

    @PutMapping("/updateAuthority")
    @PreAuthorize("hasAnyRole('ADMIN')") // ADMIN 이외에는 사용 못하게
    public ResponseEntity<?> updateAuthority(@RequestBody AuthorityUpdateDto request) {
        try {
            System.out.println("Controller getUserId: " + request.getUserId());
            System.out.println("Controller getAuthorityName: " + request.getAuthorityName());

            // 권한 업데이트 서비스 호출
            userService.updateAuthority(request);

            // 성공적으로 업데이트한 경우
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            // 실패한 경우
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update authority: " + e.getMessage());
        }
    }



}
