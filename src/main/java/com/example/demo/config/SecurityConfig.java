package com.example.demo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//@EnableWebSecurity
//@EnableMethodSecurity
//@Configuration
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().requestMatchers("/member/login");
//    }
//
//    @Bean
//    protected SecurityFilterChain webSecurityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
//                        .requestMatchers("/member/login", "/css/**", "/images/**", "/js/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//
//                .formLogin((formLogin) -> formLogin
//                        .loginPage("/member/login")
//                        .defaultSuccessUrl("/main")
//                        .permitAll()
//                );
//
//        http
//                .sessionManagement((sessionManagement) -> sessionManagement
//                        .invalidSessionUrl("/member/login")
//                )
//                .logout((logout) -> logout
//                        .invalidateHttpSession(true)
//                        .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
//                        .deleteCookies("JSESSIONID")
//                        .permitAll()
//                );
//
//
//        //CSRF 토큰
//        http
//                .csrf((csrf) -> csrf
//                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                );
//
//        return http.build();
//    }
//}
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
        requestCache.setMatchingRequestParameterName(null);

        http
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        .requestMatchers(
                                new AntPathRequestMatcher("/**")
                        )
                        .permitAll()
                )
                .csrf((csrf) -> csrf
                        .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"))
                )
                .requestCache(request -> request
                        .requestCache(requestCache)
                )
                .headers((headers) -> headers
                        .addHeaderWriter(new XFrameOptionsHeaderWriter(
                                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
                )
                .formLogin((formLogin) -> formLogin
                        .loginPage("/member/login") // 해당 url을 로그인 페이지로 사용
                        .defaultSuccessUrl("/main") // 로그인 성공하고 나면 해당 url로 이동
                )
                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                )
        ;
        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}