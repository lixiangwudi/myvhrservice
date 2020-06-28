package org.javaboy.vhr.config;
/**
 * @Author lixiang
 * @Date 2020/5/19 21:05
 * @Version 1.0
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import org.javaboy.vhr.model.Hr;
import org.javaboy.vhr.model.RespBean;
import org.javaboy.vhr.service.HrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author     ：lx
 * @date       ：Created in 2020/5/19 21:05
 * @description：
 * @modified By：
 * @version: 1.0
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    HrService hrService;
    @Autowired
    MyFilter myFilter;
    @Autowired
    MyDesicionManage myDesicionManage;
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(hrService);
//        auth.inMemoryAuthentication().passwordEncoder(passwordEncoder()).withUser("admin")
//                .password(passwordEncoder().encode("123")).roles("admin");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/login");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setAccessDecisionManager(myDesicionManage);//注入用户登录权限
                        object.setSecurityMetadataSource(myFilter);//注入用户所请求的地址所需要的权限
                        return object;
                    }
                })
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/doLogin")
                .loginPage("/login")
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication auth) throws IOException, ServletException {
                            resp.setContentType("application/json;charset=utf-8");
                        PrintWriter writer = resp.getWriter();
                        Hr principal = (Hr) auth.getPrincipal();
                        principal.setPassword(null);
                        RespBean ok = RespBean.ok("200", principal);
                        String s = new ObjectMapper().writeValueAsString(ok);
                        writer.write(s);
                        writer.flush();
                        writer.close();
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                        response.setContentType("application/json;charset=utf-8");
                        PrintWriter out = response.getWriter();
                        RespBean respBean = RespBean.error(exception.getMessage());
                        if (exception instanceof LockedException) {
                            respBean.setMsg("账户被锁定，请联系管理员!");
                        } else if (exception instanceof CredentialsExpiredException) {
                            respBean.setMsg("密码过期，请联系管理员!");
                        } else if (exception instanceof AccountExpiredException) {
                            respBean.setMsg("账户过期，请联系管理员!");
                        } else if (exception instanceof DisabledException) {
                            respBean.setMsg("账户被禁用，请联系管理员!");
                        } else if (exception instanceof BadCredentialsException) {
                            respBean.setMsg("用户名或者密码输入错误，请重新输入!");
                        }
                        out.write(new ObjectMapper().writeValueAsString(respBean));
                        out.flush();
                        out.close();
                    }
                })
                .permitAll()
                .and()
                .logout()
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication auth) throws IOException, ServletException {
                        resp.setContentType("application/json;charset=utf-8");
                        PrintWriter writer = resp.getWriter();
                        writer.write(new ObjectMapper().writeValueAsString(RespBean.ok("注销登录成功")));
                        writer.flush();
                        writer.close();
                    }
                })
                .permitAll()
                .and()
                .csrf().disable().exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest req, HttpServletResponse resp, AuthenticationException e) throws IOException, ServletException {
                resp.setContentType("application/json;charset=utf-8");
                PrintWriter writer = resp.getWriter();
                resp.setStatus(401);
                RespBean ok = RespBean.error("500", "访问失败");
                if(e instanceof InsufficientAuthenticationException){
                    ok.setMsg("尚未登录，请登录");
                }
                String s = new ObjectMapper().writeValueAsString(ok);
                writer.write(s);
                writer.flush();
                writer.close();
            }
        });


    }
}
