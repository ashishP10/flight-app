package com.example.flightapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
public class Delay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer code;
    private String reason;
    private Integer time;

    private Delay(Long id,Integer code,String reason,Integer time){
        this.id=id;
        this.code=code;
        this.reason=reason;
        this.time=time;
    }

    public Delay() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public Integer getCode() {
        return code;
    }

    public String getReason() {
        return reason;
    }

    public Integer getTime() {
        return time;
    }

    public static class DelayBuilder{
        private Long id;
        private Integer code;
        private String reason;
        private Integer time;

        public DelayBuilder(){
            id=0L;
            code= 0;
            reason ="WWW";
            time=0;
        }
        public DelayBuilder setCode(Integer code) {
            this.code = code;
            return this;
        }

        public DelayBuilder setReason(String reason) {
            this.reason = reason;
            return this;
        }

        public DelayBuilder setTime(Integer time) {
            this.time = time;
            return this;
        }
        public Delay build(){
            return new Delay(id,code,reason,time);
        }

    }

}
