package com.cr.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //密码编码：passwordEncoder
    //在Spring Security 5.0+ 中新增了很多的加密方法
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        auth.inMemoryAuthentication().passwordEncoder(passwordEncoder)
                .withUser("root").password(passwordEncoder.encode("123456")).roles("vip1", "vip2", "vip3")
                .and()
                .withUser("yangying").password(passwordEncoder.encode("123456")).roles("vip2")
                .and()
                .withUser("langying").password(passwordEncoder.encode("123456")).roles("vip3");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //首页所有人可以访问，但是功能页只有对应有权限的人可以访问
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/level1/**").hasRole("vip1")
                .antMatchers("/level2/**").hasRole("vip2")
                .antMatchers("/level3/**").hasRole("vip3");

        //没有权限默认跳转至登录页面
        http.formLogin();
        //CSRF在SpringSecurity中默认是启动的，那么你的退出请求必须改为POST请求。
        // 这确保了注销需要CSRF令牌和一个恶意的用户不能强制注销用户
        //所以在SpringSecurity中需要重新配置登出
        http.csrf().disable();//关闭csrf功能
        //注销，开启了注销功能
        http.logout().logoutSuccessUrl("/");

    }
}
