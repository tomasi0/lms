package com.dw.lms.service;

import com.dw.lms.dto.UserDto;
import com.dw.lms.model.Authority;
import com.dw.lms.model.User;
import com.dw.lms.repository.UserRepository;
import jakarta.transaction.Transactional;
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
                userDto.getConsentToRiYn(),
                userDto.getActYn(),
                userDto.getUpdatedAt()
        );

        return userRepository.save(user).getUserId(); // 없으면 Insert, 있으면 Update
    }
}
