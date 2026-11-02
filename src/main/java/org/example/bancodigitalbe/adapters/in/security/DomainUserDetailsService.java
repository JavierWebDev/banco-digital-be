package org.example.bancodigitalbe.adapters.in.security;

import org.example.bancodigitalbe.domain.port.out.LoadUserPort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DomainUserDetailsService implements UserDetailsService {

    private final LoadUserPort loadUserPort;

    public DomainUserDetailsService(LoadUserPort loadUserPort) {
        this.loadUserPort = loadUserPort;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loadUserPort.loadByUsername(username)
                .map(user -> org.springframework.security.core.userdetails.User
                        .withUsername(user.getUser())
                        .password(user.getPassword())
                        .authorities("ROLE_CLIENT")
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("[!] username {" + username + "} not found"));
    }
}
