package com.cbl.backend.controller;

import com.cbl.backend.entity.CartInfo;
import com.cbl.backend.entity.User;
import com.cbl.backend.mapper.CartInfoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static java.sql.DriverManager.println;

@RestController
public class CartInfoController {
    @Resource
    CartInfoMapper cartInfoMapper;

    @GetMapping("/cartInfo/info")
    public List<CartInfo> list(HttpServletRequest request){

        User user = (User)request.getSession().getAttribute("user");
        if(cartInfoMapper.getCartInfoByUserId(user.getUserid()) != null){
            System.out.println(cartInfoMapper.getCartInfoByUserId(user.getUserid()));
            return cartInfoMapper.getCartInfoByUserId(user.getUserid());
        }
        else {
            println("该用户无购物车信息");
            return null;
        }
    }

    @PostMapping("/cartInfo/add")
    public ResponseEntity<Void> add(@RequestBody CartInfo cartInfo){
        try {
            cartInfoMapper.add(cartInfo);
            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/cartInfo/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id){
        try{
            cartInfoMapper.delete(id);
        }
        catch (Exception e){
            System.err.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
