package com.dw.lms.service;

import com.dw.lms.dto.UserDto;
import com.dw.lms.exception.ResourceNotFoundException;
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
        }else {
            userDto.setActYn("Y"); // 회원가입시 ActYn Y로 설정 2024.06.11 명준호
            userDto.setUpdatedAt(LocalDateTime.now()); // 세이브 하기 전, UpdateAt을 현재시간으로 등록 2024.06.11 명준호
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

//    회원탈퇴
//    public String deleteUser(UserDto userDto) {
//        Optional<User> userOptional = userRepository.findByUserId(userDto.getUserId());
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//            userDto.setActYn("N");
//            userRepository.save(user);
//            return "회원 탈퇴가 완료되었습니다.";
//        } else {
//            return "해당 ID로 등록된 사용자가 없습니다.";
//        }
//    }

    public User getUserByUserId(String userId) {
        // 유저아이디로 유저객체 찾기
        Optional<User> userOptional = userRepository.findByUserId(userId);
        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("User", "ID", userId);
        }

//        System.out.println("UserId     : " + userOptional.get().getUserId());
//        System.out.println("UserName   : " + userOptional.get().getUserNameKor());
//        System.out.println("UserNameEng: " + userOptional.get().getUserNameEng());

        return userOptional.get();
    }



}
