package com.example.project_kpi_27_09_24.security;


import com.example.project_kpi_27_09_24.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService  implements UserDetailsService {

    @Autowired
    private UserRepository   userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return   userRepository.findByUserName(username).orElseThrow(()-> new UsernameNotFoundException(username+ " Topilmadi..!!"));
    }

    public   UserDetails  loadUserById(Long  id)  throws   UsernameNotFoundException {
        return    userRepository.findById(id).orElseThrow(()-> new UsernameNotFoundException(id+" Topilmadi..!!>"));
    }


    public  boolean  checkUser(Long  id) {
        System.out.println(userRepository.existsById(id));
        return   userRepository.existsById(id);
    }

    public  boolean   checkUserByUsername( String   userName){

        return  userRepository.existsByUserName(userName);}


    public boolean isAuthenticate(){
        return   (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
    }

}
