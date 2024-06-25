package com.dw.lms.service;

import com.dw.lms.dto.SessionDto;
import com.dw.lms.dto.UserDto;
import com.dw.lms.exception.ResourceNotFoundException;
import com.dw.lms.model.Authority;
import com.dw.lms.model.User;
import com.dw.lms.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public String saveUser(UserDto userDto) { // userDto 형태로 변경
        Optional <User> userOptional = userRepository.findByUserId(userDto.getUserId()); // 기본적으로 Optional 로 가져옴
        if (userOptional.isPresent()) {
            return "이미 등록된 ID 입니다."; // 나중에 Status 실패로 보내서 클라이언트에서 처리 예정
        }

        /* 권한 관련 추가, 회원 가입시 무조건 user 권한, 나중에 Admin 이 권한을 바꿈 */
        Authority authority = new Authority();
        authority.setAuthorityName("ROLE_USER");

        User user = new User(userDto.getUserId(),
                userDto.getUserName(),
                userDto.getUserEmail(),
                bCryptPasswordEncoder.encode(userDto.getPassword()), // 암호화 하여 적용
                authority,
                LocalDateTime.now(),
                userDto.getUserNameEng(),
                userDto.getGender(),
                userDto.getBirthDate(),
                userDto.getHpTel(),
                userDto.getZip_code(),
                userDto.getAddress1Name(),
                userDto.getAddress2Name(),
                userDto.getEducation(),
                userDto.getFinalSchool(),
                userDto.getCfOfEmp(),
                userDto.getReceiveEmailYn(),
                userDto.getReceiveSmsYn(),
                userDto.getReceiveAdsPrPromoYn(),
                userDto.getActYn(),
                userDto.getUpdatedAt()
        );

        return userRepository.save(user).getUserId(); // 없으면 Insert, 있으면 Update
    }

//    public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException {
//        Optional<User> user = userRepository.findByUserId(username);
//        if (user.isEmpty()) {
//            throw new IllegalArgumentException(username);
//        }
//        return user.get();
//    }

    public User getUserByUserId(String userId) {
        // 유저아이디로 유저객체 찾기
        Optional<User> userOptional = userRepository.findByUserId(userId);
        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("User", "ID", userId);
        }

        System.out.println("UserId: " + userOptional.get().getUserId());
        System.out.println("UserId: " + userOptional.get().getUserNameKor());

        return userOptional.get();
    }

    public User SetUserData(User user) {
        Optional<User> userOptional = userRepository.findByUserId(user.getUserId());
        if(userOptional.isPresent()) {
            User temp = userOptional.get();
            temp.setUserNameEng(user.getUserNameEng());
            temp.setEmail(user.getEmail());
            temp.setBirthDate(user.getBirthDate());
            temp.setHpTel(user.getHpTel());
            temp.setEducation(user.getEducation());
            temp.setFinalSchool(user.getFinalSchool());
            temp.setZip_code(user.getZip_code());
            temp.setAddress1Name(user.getAddress1Name());
            temp.setAddress2Name(user.getAddress2Name());
            temp.setCfOfEmp(user.getCfOfEmp());
            temp.setReceiveEmailYn(user.getReceiveEmailYn());
            temp.setReceiveSmsYn(user.getReceiveSmsYn());
            temp.setReceiveAdsPrPromoYn(user.getReceiveAdsPrPromoYn());
            userRepository.save(temp);
            return temp;
        }else {
            throw new ResourceNotFoundException("user", "ID", user.getUserId());
        }
    }

}
