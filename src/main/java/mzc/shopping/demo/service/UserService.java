package mzc.shopping.demo.service;
import mzc.shopping.demo.dto.AdminSignUpRequest;
import lombok.RequiredArgsConstructor;
import mzc.shopping.demo.dto.*;
import mzc.shopping.demo.entity.User;
import mzc.shopping.demo.repository.UserRepository;
import mzc.shopping.demo.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public UserResponse signUp(SignUpRequest request) {
        // 이메일 중복 체크
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .phone(request.getPhone())
                .build();

        User savedUser = userRepository.save(user);
        return UserResponse.from(savedUser);
    }

    public TokenResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다");
        }

        String token = jwtTokenProvider.createToken(user.getEmail(), user.getRole().name());
        return TokenResponse.of(token, jwtTokenProvider.getExpiration());
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다"));
        return UserResponse.from(user);
    }

    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다"));
        return UserResponse.from(user);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserResponse::from)
                .collect(Collectors.toList());
    }
        // 사용자 삭제
        @Transactional
        public void deleteUser(Long id) {
            if (!userRepository.existsById(id)) {
                throw new IllegalArgumentException("사용자를 찾을 수 없습니다");
            }
            userRepository.deleteById(id);
        }


    // 사용자 정보 수정
    @Transactional
    public UserResponse updateUser(Long id, SignUpRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다"));

        if (request.getName() != null) {
            user.setName(request.getName());
        }
        if (request.getPhone() != null) {
            user.setPhone(request.getPhone());
        }

        return UserResponse.from(user);
    }

    // 관리자 회원가입
    @Transactional
    public UserResponse adminSignUp(AdminSignUpRequest request) {
        // 관리자 코드 검증
        if (!"ADMIN2024".equals(request.getAdminCode())) {
            throw new IllegalArgumentException("관리자 인증 코드가 올바르지 않습니다");
        }

        // 이메일 중복 체크
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .phone(request.getPhone())
                .role(User.Role.ADMIN)
                .build();

        User savedUser = userRepository.save(user);
        return UserResponse.from(savedUser);
    }

}