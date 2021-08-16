package com.hoaxify.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hoaxify.models.User;
import com.hoaxify.models.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	public User save(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	public static void main(String[] args) {
		String[] ss = "http://download.siliconexpert.com/pdfs2/2021/3/30/9/33/56/405362/qorvo_/manual/21-0071.pdf|http://download.siliconexpert.com/pdfs2/2020/11/24/8/42/24/705385/qorvo_/manual/20-0186.pdf"
				.split("\\|");
		System.out.println(ss[0]);
	}
}
