package com.dw.lms.config;

import com.dw.lms.exception.MyAccessDeniedHandler;
import com.dw.lms.exception.MyAuthenticationEntryPoint;
import com.dw.lms.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // 권한 관련 추가 => @PreAuthorize("hasAnyRole('ADMIN','USER','TEACHER')") 사용시는 꼭 필요
public class SecurityConfig {
    @Autowired
    private UserDetailService userDetailService;

// [login.html] form 부분
//          <form action="/login" method="POST">
//            <input type="hidden" th:name="${_csrf?.parameterName}" th:value="${_csrf?.token}" />
//            <div class="mb-3">
//              <label class="form-label text-white">User ID</label>
//              <input type="text" class="form-control" name="username">
//            </div>
//            <div class="mb-3">
//              <label class="form-label text-white">Password</label>
//              <input type="password" class="form-control" name="password">
//            </div>
//            <button type="submit" class="btn btn-primary">Submit</button>
//          </form>

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests(auth -> auth
                        .requestMatchers(
                                /* CORS 체크 후에 진행 */
                                new AntPathRequestMatcher("/products/**"), // REST API 용 주소(WAS 까지), 제품 관련 REST API 인증 추가 (24/05/27)
                                new AntPathRequestMatcher("/user/login"),  // REST API 용 주소(WAS 까지), Rest API 로그인, /user/** 이하 모든것, /user/* 첫번째 레벨 자식들
                                new AntPathRequestMatcher("/user/signup"), // REST API 용 주소(WAS 까지), 가입
                                new AntPathRequestMatcher("/login"),       // 일반주소, Static Page login 등 허용 주소를 기재함
                                new AntPathRequestMatcher("/lms/**"), // 페이지 요청 (위의 3개와 다름) (톰켓 까지)
                                // new AntPathRequestMatcher("/user/current"),
                                new AntPathRequestMatcher("/api/notices/**"),   // Guest 공지사항 확인 가능
                                new AntPathRequestMatcher("/api/lmsevents/**"), // Guest 이벤트 확인 가능
                                new AntPathRequestMatcher("/image/**"),    // 페이지 요청 (위의 3개와 다름) (톰켓 까지)
                                new AntPathRequestMatcher("/lecture/**"),  // 페이지 요청 (위의 3개와 다름) (톰켓 까지)
                                new AntPathRequestMatcher("/pdf/**"),  // 페이지 요청 (위의 3개와 다름) (톰켓 까지)
                                new AntPathRequestMatcher("/css/**"),      // 페이지 요청 (위의 3개와 다름) (톰켓 까지)
                                new AntPathRequestMatcher("/js/**")        // 페이지 요청 (위의 3개와 다름) (톰켓 까지)
                        ).permitAll() // 모두 허용
                        .anyRequest().authenticated()) // 어떠한 요청이든 인증받겠다.
                .formLogin(form->form.loginPage("/login").defaultSuccessUrl("/articles")) // (formLogin => 로그인 화면 창) 정적 로그인 화면이 존재하는 경우, 로그인 성공시 다음화면 => defaultSuccessUrl
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)) // 세션 인증시 접속 가능
                .csrf(AbstractHttpConfigurer::disable) // CSRF는 Cross Site Request Forgery(사이트 간 요청 위조) 다른 사이트에서 들어오는 요청 막음 => 안씀
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(new MyAuthenticationEntryPoint()) // 인증예외
                        .accessDeniedHandler(new MyAccessDeniedHandler())) // 인가예외(권한실패)
                .build();
    }

    @Bean
    // 생성자
    public AuthenticationManager authenticationManager(HttpSecurity http,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            UserDetailService userDetailService) throws Exception {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(); // DAO (Database Acess Object)
        authProvider.setUserDetailsService(userDetailService); // 사용할 userDetailService
        authProvider.setPasswordEncoder(bCryptPasswordEncoder); // 사용할 Password Encoder 정보
        return new ProviderManager(authProvider);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() { // BCrypt 는 블로피시 암호에 기반을 둔 [암호화 해시 함수]
        return new BCryptPasswordEncoder();
    }
    // 인코딩 => 디코딩이 가능한 암호화 기법
    // 인크립션 => 디코딩이 불가능한 암호화 기법 (해시 암호화)

}
