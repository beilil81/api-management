package com.humanup.matrix.ui.apimanagement.proxy;

import com.humanup.matrix.ui.apimanagement.dto.LoginDTO;
import com.humanup.matrix.ui.apimanagement.dto.PersonDTO;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "zuul-server")
public interface AuthenticationProxy {
    @PostMapping(value="/token/generate-token")
    String authentication(@RequestBody LoginDTO login);
}
